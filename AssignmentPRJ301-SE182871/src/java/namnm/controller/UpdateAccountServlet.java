/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import namnm.registration.RegistrationDAO;
import namnm.registration.RegistrationDTO;

/**
 *
 * @author Ngoc Lan
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

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
        // 1. Lấy thông tin cần cập nhật từ request
        String username = request.getParameter("txtUsername");
        String newPassword = request.getParameter("txtPassword");
        String searchValue = request.getParameter("lastSearchValue");
        boolean isAdmin = request.getParameter("chkAdmin") != null;
        String url = "errorPage.html"; // Trang lỗi mặc định

        try {
            // 2. Gọi DAO để cập nhật thông tin người dùng
            RegistrationDAO dao = new RegistrationDAO();
            boolean result = dao.updateAccount(username, newPassword, isAdmin);
            
            // 3. Xử lý kết quả cập nhật
            if (result) {
                // Cập nhật thành công, điều hướng lại trang Search với tham số tìm kiếm
                url = "DispatchServlet?btAction=Search&txtSearchValue=" + searchValue;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log("UpdateAccountServlet _ Exception: " + ex.getMessage(), ex);
        } finally {
            response.sendRedirect(url);
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
