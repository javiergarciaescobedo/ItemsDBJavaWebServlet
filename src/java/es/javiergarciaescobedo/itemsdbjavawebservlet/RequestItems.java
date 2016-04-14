package es.javiergarciaescobedo.itemsdbjavawebservlet;

import es.javiergarciaescobedo.itemsdbjavawebservlet.model.Item;
import es.javiergarciaescobedo.itemsdbjavawebservlet.model.Items;
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

@WebServlet(name = "RequestItems", urlPatterns = {"/RequestItems"})
public class RequestItems extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RequestItems.class.getName());

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
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Items items = (Items) jaxbUnmarshaller.unmarshal(request.getInputStream());

            String strAction = request.getParameter("action");
            LOG.fine("Action requested: " + strAction);
            entityManager.getTransaction().begin();
            switch (strAction) {
                case "select":
                    Query query = entityManager.createNamedQuery("Item.findAll");
                    items.setItemsList(query.getResultList());
                    break;
                case "insert":
                    for(Item item :  items.getItemsList()) {
                        LOG.fine("Inserting Item[ id=" + item.getId()
                                + "; astring=" + item.getAstring()
                                + "; anumber=" + item.getAnumber()
                                + "; adate=" + item.getAdate() + " ]");
                        entityManager.persist(item);
                    }   break;
                case "update":
                    for(Item item :  items.getItemsList()) {
                        LOG.fine("Updating Item[ id=" + item.getId()
                                + "; astring=" + item.getAstring()
                                + "; anumber=" + item.getAnumber()
                                + "; adate=" + item.getAdate() + " ]");
                        entityManager.merge(item);
                    }   break;
                case "delete":
                    for(Item item :  items.getItemsList()) {
                        LOG.fine("Removing Item[ id=" + item.getId()
                                + "; astring=" + item.getAstring()
                                + "; anumber=" + item.getAnumber()
                                + "; adate=" + item.getAdate() + " ]");
                        item = entityManager.find(Item.class, item.getId());
                        entityManager.remove(item);
                    }   break;
                default:
                    break;
            }
            entityManager.getTransaction().commit();
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(items, out);
            
            LOG.fine("Response generated: ");
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(items, sw);
            LOG.fine(sw.toString());
        } catch (JAXBException ex) {
            Logger.getLogger(RequestItems.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
