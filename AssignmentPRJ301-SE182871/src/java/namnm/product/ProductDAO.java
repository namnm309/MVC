/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.product;

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
public class ProductDAO implements Serializable {

    List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void showList()
            throws SQLException,ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        //1.Connect DB
        try {
            con=DBHelper.makeConnection();
        //2.Create SQL String 
        String sql="SELECT sku, name, description, quantity, price, status FROM tblProducts";
        //3.Sreate SQL Statement
        stm=con.prepareStatement(sql);
        //4.Excute Query
        rs=stm.executeQuery();
        //5.Process
        //Kết quả trả về nhiều dòng => while 
        while (rs.next()) {
            int sku=rs.getInt("sku");
            String name=rs.getString("name");
            String description=rs.getString("description");
            int quantity=rs.getInt("quantity");
            float price=rs.getFloat("price");
            boolean status=rs.getBoolean("status");
            ProductDTO dto=new ProductDTO(sku, name, description, quantity, price, status);
            if (this.products == null) {
                this.products=new ArrayList<>();
            }
            this.products.add(dto);
        }
            

        } finally {
            if (rs != null) {

            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
    }
    
    public boolean updateProductQuantity(int sku,int newQuantity) 
                            throws ClassNotFoundException,SQLException {
        Connection con=null;
        PreparedStatement stm=null;
        try {
            //1.Connect DB
            con=DBHelper.makeConnection();
            //2.Create SQL String
            String sql="UPDATE tblProducts SET quantity = ? WHERE sku = ?";
            //3.Create SQL Statement
            stm=con.prepareStatement(sql);
            stm.setInt(1, newQuantity);
            stm.setInt(2, sku);
            //4.Excute query
            int rowsAffted=stm.executeUpdate();
            return rowsAffted>0;
        } finally {
            if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
        }
    }
    
    public int getProductQuantity(int sku) 
                    throws SQLException,ClassNotFoundException {
        Connection con=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        
        try {
            //1.Connect DB
            con=DBHelper.makeConnection();
            //2.Create SQL String 
            String sql="SELECT quantity FROM tblProducts WHERE sku = ?";
            //3.Create SQL Statement 
            stm=con.prepareStatement(sql);
            stm.setInt(1, sku);
            //4.Excute query
            rs=stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } finally {
            if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
        }
        return -1;
    }

}
