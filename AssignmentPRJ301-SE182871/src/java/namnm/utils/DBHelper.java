/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ngoc Lan
 */

//Vẫn ở phía sever => implement serializable
public class DBHelper implements Serializable {
    
    //Các method liên quan đến helper mang tính chất lib sp xử lí ko cần tạo object => nên có static tránh cấp phát vùng nhớ và thu hồi 
    public static Connection makeConnection()
        throws ClassNotFoundException, SQLException {
        
        //Tạo connection có 3 bước 
        //1.Load driver 
            //Add JAR/Folder in Libraries => add sqljdbc4.jar
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //bấm . . . nếu nó bung ra => đã add driver , nếu đã add mà vẫn 0 bung => add lại 
            //Hiện tại đang bị lỗi => classnotfoundexception 
            //Có 2 cách fix lỗi : xử lí nội tài hàm or thông báo cho tg caller  
            //tại đây đưa cho caller xử lí => throws 


        //2.Create Connection String 
        String url="jdbc:sqlserver:"
                +"//localhost:1433"
                +";databaseName=mndb"
                +";instanceName=SQL2017";
        


        //3.Open Connection
        Connection con =DriverManager.getConnection(url,"sa","12345");
        
        //3a) Đang ở database => dao 
    
    return con;
    }      
}
