/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import namnm.utils.DBHelper;

/**
 *
 * @author Ngoc Lan
 */
public class RegistrationDAO implements Serializable {

    /*
    Quy tắc đặt tên DAO 
    -Class Name : tên chữ cái thường đầu tiên  viết hoa + DAO 
    VD: RegistrationDAO
    -Package : tên user (viết thường).tên class(viết thường) 
    VD: namnm.registration
     */
    //2b) implement serializable ( vì đang ở phía sever nên giao tiếp thông qua byte or bit stream ) 
    //2c) DAO có nhiệm vụ tạo phương thức để access và lấy data từ DB => tạo phương thức 
    //=> tạo phương thức checkLogin để check username và password 
    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException {
        //3b) Khởi tạo đối tượng cho bước 1 là connect DB 
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result=null;
        //3c) trong mọi trường hợp đóng nó lại và trong mọi trường hợp phải kiểm tra xem nó có khác null hay không =>try finally
        try {

            //Để thực hiện kết nối và xử lí database có những bước như sau 
            //1.Connect DB
            //4 connect 
            con = DBHelper.makeConnection();//ClassNotFoundException
            //4a) Kiểm tra có khác null trong mọi th
            if (con != null) {
                //4b làm bc 2 , 3 ,4 ,5 
                //2.Create SQL String
                String sql = "Select lastname, isAdmin "
                        + "FROM Users2 " //Lưu ý dấu space nếu ko sẽ bị lỗi truy vấn và nó sẽ ko thông báo lỗi 
                        + "WHERE username = ? "
                        + "AND password = ?";
                //3.Create SQL Statement 
                //Khai báo trước => stm
                //Đóng =>stm !=null 
                //Viết code 
                stm = con.prepareStatement(sql);
                stm.setString(1, username);//từ trên xuống , trái -> phải => 1
                stm.setString(2, password);
                //4.Excute Query 
                //Khai báo 
                //Đóng 
                //Viết code
                rs = stm.executeQuery();
                //5.Process ( xử lí ) , đối với insert và update thì là kết quả 
                if (rs.next()) {
                    String fullName=rs.getString("lastname");
                    boolean role=rs.getBoolean("isAdmin");
                    result = new RegistrationDTO(username, password, fullName, role);
//                    return true;
                    //4c) đang ở DAO => Controller = LoginServlet
                }
                //3) từ DAO đến Database => Database dùng chung => Tạo DBHelper or DBUtils hỗ trợ sp cho các method dùng chung cho toàn app (javaclass)

            } //process when connection is existed 
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();//khai báo chiều thuận , đóng chiều ngược 
            }
            if (con != null) {
                con.close();//lệnh này dùng SQLException để fix 
            }
        }

        return result;
    }

//Search trả về nhiều hơn 1 record mà mỗi 1 record sẽ map trên bộ nhớ là 1 DTO => tạo DTO
//Vì trả về nhiều record => dùng kiểu list 
    private List<RegistrationDTO> account;

//Tạo phương thức getter để lấy account , gõ get + ctrl space
    public List<RegistrationDTO> getAccount() {
        return account;
    }

//Tạo phương thức search 
    public void searchLastname(String searchValue)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result=false;

        //Cấu trúc giống login gồm 5 bước 
        try {
            //1.Connect DB
            con = DBHelper.makeConnection();
            //Kiểm tra khác null in mọi situation 
            if (con != null) {
                //2.Create SQL String
                //Khi viết sql nên copy các tên từ sql 
                String sql = "Select username, password, lastname, isAdmin "
                        + "FROM Users2 "
                        + "WHERE lastname like ?";
                //3.Create SQL Statement 
                stm = con.prepareStatement(sql);
                //gán giá trị cho ? 
                //"%" là wildcard ,các giá trị trong search đc bao bọc bởi % 
                stm.setString(1, "%" + searchValue + "%");//bắt đầu = 1 , quy ước JDBC
                //4.Excute query 
                rs = stm.executeQuery();
                //5.Process
                //kết quả search trả về nhiều dòng => while
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    //Map dữ liệu đã lấy ra vào DTO
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullName, role);

                    //check null
                    if (this.account == null) {
                        //Nếu ko có thì tạo mới 
                        this.account = new ArrayList<>();
                    }
                    this.account.add(dto);
                }

            }//xử lí khi tồn tại connection
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();//khai báo chiều thuận , đóng chiều ngược 
            }
            if (con != null) {
                con.close();//lệnh này dùng SQLException để fix 
            }
        }

    }

    public boolean deleteAccount(String username)
                        throws SQLException,ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            // 1. Kết nối tới cơ sở dữ liệu
            con = DBHelper.makeConnection();

            if (con != null) {
                // 2. Tạo câu lệnh SQL DELETE chỉ xóa dữ liệu từ bảng Registration dựa trên username
                String sql = "DELETE FROM Users2 WHERE username = ?";

                // 3. Tạo PreparedStatement và thiết lập tham số username
                stm = con.prepareStatement(sql);
                stm.setString(1, username); // Thiết lập tham số username

                // 4. Thực thi câu lệnh SQL DELETE
                int affectedRows = stm.executeUpdate();

                // 5. Xử lý kết quả của việc xóa
                if (affectedRows > 0) {
                    result = true; // Nếu có hàng bị xóa, trả về true
                }
            }
        } finally {
            // 6. Đóng các tài nguyên (PreparedStatement và Connection) sau khi sử dụng
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result; // Trả về kết quả xóa (true/false)

    }
    
    public boolean updateAccount (String username,String newPassword,boolean isAdmin) 
            throws SQLException,ClassNotFoundException  {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        
        try {
            
            //1.Connect DB
            con=DBHelper.makeConnection();
            if (con!=null) {            
            //2.Create SQL String
            String sql ="UPDATE Users2 SET password = ?, isAdmin = ? WHERE username = ?";
            //3.Create SQL Statement
            stm=con.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setBoolean(2, isAdmin);
            stm.setString(3, username);
            //4.Exucte Query
            int afftedRows=stm.executeUpdate();
            //5.Process 
            if (afftedRows > 0 ){
                result=true;
            }
            }    
        } finally {
             if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean createAccount(RegistrationDTO dto) 
                    throws SQLException,ClassNotFoundException {
        Connection con=null;
        PreparedStatement stm=null;
        boolean result=false;
        
        
        try {
            //1.Connect DB
            con=DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String 
                String sql="INSERT INTO Users2 ("
                        + "username, password, lastname, isAdmin"
                        + ") "
                        + "VALUES (?, ?, ?, ?)";
                //3.Create SQL Statement 
                stm=con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullName());
                stm.setBoolean(4, dto.isRole());
                //4.Excute query
                int affectedRows=stm.executeUpdate();
                
                //5.Process result
                if (affectedRows > 0) {
                    return true;
                }//username and password are verified
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
