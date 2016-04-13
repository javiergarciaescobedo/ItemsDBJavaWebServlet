package es.javiergarciaescobedo.sampleitemsdbjavaweb;

import es.javiergarciaescobedo.sampleitemsdbjavaweb.model.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GenerateRandomItem", urlPatterns = {"/GenerateRandomItem"})
public class GenerateRandomItem extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {            
            try {                            
                Random random = new Random();
                
                Item item = new Item();
                String astring = "";
                for(int i=0; i<=10+random.nextInt(10); i++) {
                    astring += (char)('A' + random.nextInt(26));
                }
                item.setAstring(astring);
                item.setAnumber(random.nextInt(1000000));
                Date randomDate = new Date(
                        System.currentTimeMillis() - Math.abs(random.nextInt()));
                item.setAdate(randomDate);
                
                EntityManager entityManager = Persistence.createEntityManagerFactory("SampleItemsDBJavaWebPU").createEntityManager(); 
                entityManager.getTransaction().begin();
                entityManager.persist(item);
                entityManager.getTransaction().commit();
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet GenerateRandomItem</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println(item.getAstring()+"<br>");
                out.println(item.getAnumber()+"<br>");
                out.println(item.getAdate()+"<br>");
                out.println("</body>");
                out.println("</html>");
            } catch(Exception e) {
                out.println("<error>");
                e.printStackTrace(out);
                out.println("</error>");
            }

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
