import java.sql.*;
import java.util.*;

public class Main {
    private static final  String db_url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String  db_username="root";
    private static final String  db_password="jorin!@#1";
    public static void main(String[] args) throws ClassNotFoundException ,SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("load Driver successfully");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{

            Connection conn= DriverManager.getConnection(db_url,db_username,db_password);
            System.out.println("connet database successfully....");

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }








    }
}