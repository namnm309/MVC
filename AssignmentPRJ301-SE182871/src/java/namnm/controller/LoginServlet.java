/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import namnm.registration.RegistrationDAO;
import namnm.registration.RegistrationDTO;

/**
 *
 * @author Ngoc Lan
 */
public class LoginServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid.html";
    private final String SEARCH_PAGE = "search.jsp";

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
        response.setContentType("text/html;charset=UTF-8");//xác định respone trả về dạng gì,response obj  
        PrintWriter out = response.getWriter();//set value vào html string  lấy từ trong respose obj qua method getWriter , bao gồm try và finally 
        /*1.Khi user bấm nút login thì từ client truyền vào 3 parameter đó là txtUsername,txtPassword và btAction(nút login) 
        và những đối những parameter này đang được lưu trong request obj 
        =>Lưu ý : request obj đã có sẵn , ta đang ở Servlet obj và đã được forward request obj và respone obj vào rồi 
         */

        //1a)Lấy parameter từ request obj , lưu ý nên copy paste từ login nếu sai sẽ xuất hiện lỗi NULLLPOINTEREXCEPTION
        //Parameter đang lấy có type String 
        //String username = request.getParameter("txtUsername");
        //1b) Tương tự với password
        //String password = request.getParameter("txtPassword");
        //1c) Tương tự với button
        //String button = request.getParameter("btAction");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String url = INVALID_PAGE;//Mặc định nếu login sai 

        //Lưu ý : nhiệm vụ của controller là chuyển hướng xử lí chứ 0 phải xứ lý 
        try {

            //Hai dòng code sau dùng để check bug xem các parameter đã được servlet obj nhận chưa 
            /*out.println("User " + username + " _ password " + password +
                        " _ button " + button);
         
            System.out.println("User " + username + " _ password " + password +
                        " _ button " + button);*/
            //2 Check xem nguời ta có bấm nút login ko 
            //if (button.equals("Login")) {//"Login" là label là value 
            //Lưu ý : tối ưu lại code tránh gây phí bộ nhớ , sau khi check button thì ms lấy giá tri parameter chứ ko phải nếu 0 có button thì bỏ 
            //=> chuyển txtUsername và txtPassword vào đây 
//                String username = request.getParameter("txtUsername");
//                String password = request.getParameter("txtPassword");
            //5 từ controller qua dao bằng động từ call gồm 2 action là new và gọi 
            RegistrationDAO dao = new RegistrationDAO();
            //boolean result = dao.checkLogin(username, password);
            RegistrationDTO result = dao.checkLogin(username,password);
            //Tại đây là chặn cuối => xử lí lỗi = nội tại hàm => catch

            if (result != null) {
                //Nếu có kết quả chuyển đến trang search page 
                //Tạo url 
                url = SEARCH_PAGE;
                //Thêm cookie 
//                    Cookie cookie=new Cookie(username, password);
//                    cookie.setMaxAge(60*3);
//                    response.addCookie(cookie);
                HttpSession session = request.getSession();
                session.setAttribute("USER", result);
                session.setAttribute("USERNAME", username);
            }

            //}//end if user has clicked login button 
            //2a) check username and password servlet send to DAO for DA) process => create DAO 
        } catch (SQLException ex) {
            ex.printStackTrace();
            log("CreateAccountServlet_SQL" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            log("CreateAccountServlet_ClassNotFound" + ex.getMessage());
        } finally {
            //response.sendRedirect(url);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
