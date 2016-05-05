package es.javiergarciaescobedo.itemsdbjavawebservlet;

import es.javiergarciaescobedo.itemsdbjavawebservlet.model.Categories;
import es.javiergarciaescobedo.itemsdbjavawebservlet.model.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@WebServlet(name = "RequestCategories", urlPatterns = {"/RequestCategories"})
public class RequestCategories extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RequestCategories.class.getName());
    
    public static final byte ACTION_GET = 0;    // SELECT
    public static final byte ACTION_POST = 1;   // INSERT
    public static final byte ACTION_PUT = 2;    // UPDATE
    public static final byte ACTION_DELETE = 3; // DELETE
    
    private byte action;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            LOG.setLevel(Level.ALL);
            LOG.fine("Request received");
            EntityManager entityManager = Persistence.createEntityManagerFactory("SampleItemsDBJavaWebPU").createEntityManager(); 
            JAXBContext jaxbContext = JAXBContext.newInstance(Categories.class);
            
            Categories categories = new Categories();
            if(action != ACTION_GET) {
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                categories = (Categories) jaxbUnmarshaller.unmarshal(request.getInputStream());
            }

            entityManager.getTransaction().begin();
            switch (action) {
                case ACTION_GET:
                    LOG.fine("Action GET requested");
                    Query query = entityManager.createNamedQuery("Category.findAll");
                    categories.setCategoriesList(query.getResultList());
                    break;
                case ACTION_POST:
                    LOG.fine("Action POST requested");
                    for(Category category :  categories.getCategoriesList()) {
                        LOG.fine("Inserting Category[ id=" + category.getId()
                                + "; name=" + category.getName() + " ]");
                        entityManager.persist(category);
                    }   break;
                case ACTION_PUT:
                    LOG.fine("Action PUT requested");
                    for(Category category :  categories.getCategoriesList()) {
                        LOG.fine("Updating Category[ id=" + category.getId()
                                + "; name=" + category.getName() + " ]");
                        entityManager.merge(category);
                    }   break;
                case ACTION_DELETE:
                    LOG.fine("Action DELETE requested");
                    for(Category category :  categories.getCategoriesList()) {
                        LOG.fine("Removing Category[ id=" + category.getId()
                                + "; name=" + category.getName() + " ]");
                        category = entityManager.find(Category.class, category.getId());
                        entityManager.remove(category);
                    }   break;
                default:
                    break;
            }
            entityManager.getTransaction().commit();
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(categories, out);
            
            LOG.fine("Response generated: ");
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(categories, sw);
            LOG.fine(sw.toString());
        } catch (JAXBException ex) {
            Logger.getLogger(RequestCategories.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestCategories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("doGet");
        action = ACTION_GET;
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("doPost");
        action = ACTION_POST;
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        LOG.info("doDelete");
        action = ACTION_DELETE;
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        LOG.info("doPut");
        action = ACTION_PUT;
        processRequest(request, response);
    }
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
