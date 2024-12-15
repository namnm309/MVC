/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import namnm.cart.CartBean;
import namnm.product.ProductDAO;
import namnm.product.ProductDTO;

/**
 *
 * @author Ngoc Lan
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
private final String SHOW_LIST="ShowCartListServlet";
private final String VIEW_CART_PAGE="viewCart.jsp";
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
        String url=VIEW_CART_PAGE;
        try  {
           //1.Get user information
           String name=request.getParameter("nameOfCustomer");
           String email=request.getParameter("emailOfCustomer");
           String address=request.getParameter("addressOfCustomer");
           
           //2.Check form hợp lệ 
           if (name == null || name.trim().isEmpty() || 
                   email == null || !email.matches("\\w+@\\w+\\.\\w+") || //regular expression 
                   address == null || address.trim().isEmpty()) {
               request.setAttribute("ERROR", "Invalid input fields!");
           }else {
               HttpSession session=request.getSession(false);
               if (session != null ) {
                   CartBean cart=(CartBean) session.getAttribute("CART");
                   if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                       //Update quantity trong db 
                       ProductDAO dao=new ProductDAO();
                       Map<Integer,ProductDTO> items=cart.getItems();
                       for (ProductDTO dto : items.values()) {
                           int currentQuantity=dao.getProductQuantity(dto.getSku());
                           int updateQuantity=currentQuantity-dto.getQuantity();
                           dao.updateProductQuantity(dto.getSku(), updateQuantity);
                       }
                       //Check out xong xóa cart 
                       session.removeAttribute("CART");
                       url=SHOW_LIST;
                   }
               }
           }
        }catch (SQLException | ClassNotFoundException ex) {
            log("CheckOutServlet _ SQL " + ex.getMessage());
        } finally {
            RequestDispatcher rd=request.getRequestDispatcher(url);
            rd.forward(request, response);
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
