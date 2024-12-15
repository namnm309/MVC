/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ngoc Lan
 */
//Điều phối chuyển hướng page 
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})// copy urlPattern bỏ / paste vào welcome-file in web.xml
public class DispatchServlet extends HttpServlet {

    //Tạo biến constant
    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String SEARCH_PAGE = "SearchLastNameServlet";
    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
    private final String UPDATE_ACCOUNT = "UpdateAccountServlet";
    private final String STARTUP_CONTROLLER = "StartUpServlet";
    private final String ADD_TO_CART="AddToCartServlet";
    private final String REMOVE_FROM_CART="RemoveFromCartServlet";
    private final String SHOW_LIST="viewCart.jsp";
    private final String CHECK_OUT="CheckOutServlet";
    private final String CREATE_ACCOUNT_SERVLET="CreateAccountServlet";
    private final String LOG_OUT="LogoutServlet";

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
        //Khai báo nút nhấn của client
        String button = request.getParameter("btAction");//copy in name in login.html 
        //Tạo url mặc định 
        String url = LOGIN_PAGE;
        try {
            if (button == null) {
                url = STARTUP_CONTROLLER;
            } else if (button.equals("Login")) {//copy value in login.html
                //chuyển hướng sang login.html => tạo biến hằng có gán url để sau có modify cho dễ 
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Search")) {//copy value in search.html
                url = SEARCH_PAGE;
            } else if (button.equals("Delete")) {
                url = DELETE_ACCOUNT_CONTROLLER;
            } else if (button.equals("Update")) {
                url = UPDATE_ACCOUNT;
            } else if (button.equals("Add to your cart")){
                url=ADD_TO_CART;
            } else if (button.equals("Remove Selected Items")){
                url=REMOVE_FROM_CART;
            } else if (button.equals("View your cart")){
                url=SHOW_LIST;
            }else if (button.equals("Check Out")){
                url=CHECK_OUT;
            } else if (button.equals("Create Account")) {
                url=CREATE_ACCOUNT_SERVLET;
            } else if (button.equals("Logout")) {
                url=LOG_OUT;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
