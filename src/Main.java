import javax.swing.plaf.nimbus.State;
import java.security.spec.ECField;
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
            while(true) {
                System.out.println();
                System.out.println();
                Scanner scanner = new Scanner(System.in);
                System.out.println("HOTEL MANAGEMENT SYSTEM");

                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. User Details");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        reserveRoom(conn,scanner);
                        break;
                    case 2:
                        viewReservation(conn);
                        break;
                    case 3:
                        UserDetails(conn,scanner);
                        break;
                    case 4:
                        updateUser(conn ,scanner);
                        break;
                    case 5:
                        DeleteGuest(conn,scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                       return;
                    default:
                        System.out.println("Invalid choice .Try again");
                }


            }

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }












    }


    private static void reserveRoom(Connection conn, Scanner scanner) {
        try {
            System.out.println("fill the form to reserve a room.....");
            System.out.print("Enter guest name: ");
            String name = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int room_num = scanner.nextInt();
            System.out.print("Enter contact number: ");
            String contact_num = scanner.next();


            String query="insert into reservation(name,room_num,contact_num) values ('" + name + "', " + room_num + ", '" + contact_num + "')";
            try {
                Statement st = conn.createStatement();
                int roweffected = st.executeUpdate(query);
                if (roweffected > 0) {
                    System.out.println("Data inserted successfully");
                } else {
                    System.out.println("failed to insert data");
                }
            }
            catch(Exception e){
                System.out.println("erro falyo reee");
            }



        }
            catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public static void viewReservation(Connection conn) throws SQLException{
        String sql = "select * from reservation";



        try{
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(sql);

            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | GuestName           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (rs.next()) {
                int reservationId = rs.getInt("id");
                String guestName = rs.getString("name");
                int roomNumber = rs.getInt("room_num");
                String contactNumber = rs.getString("contact_num");
                String reservationDate = rs.getString("reservation_date");

                // Format and display the reservation data in a table-like format
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
         }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static  void UserDetails(Connection conn,Scanner scanner){
        System.out.println( "************User Details**********");

        System.out.println("enter the user id:");
        int  id=scanner.nextInt();


        String sql = "SELECT * FROM reservation " +
                   "WHERE id = " + id
                   ;

        try {
            Statement st=conn.createStatement();
            ResultSet result=st.executeQuery(sql);

            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | GuestName           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

               if (result.next()) {
                   int user_id =result.getInt("id");
                    String name = result.getString("name");
                    int room_num=result.getInt("room_num");
                    String contact_num=result.getString("contact_num");
                    String  reserve_date=result.getString("reservation_date");
                   System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                           id, name, room_num, contact_num, reserve_date);
                   System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");


                } else {
                    System.out.println("Reservation not found for the given contact number and guest name.");
                }


        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void updateUser (Connection conn ,Scanner scanner){
        System.out.println("**********update user***********");
        System.out.println("enter the id of the guest");
            int reservation_id=scanner.nextInt();

            if(!checkexist(conn,reservation_id)){
                System.out.println("guest doesnt exist.......");
            }
            else {
                System.out.print("Enter new guest name: ");
            String newGuestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = scanner.next();

            String sql = "UPDATE reservation SET name = '" + newGuestName + "', " +
                    "room_num = " + newRoomNumber + ", " +
                    "contact_num = '" + newContactNumber + "' " +
                    "WHERE id = " + reservation_id;

            try{
                Statement st= conn.createStatement();
                int roweffeced=st.executeUpdate(sql);
                if(roweffeced>0){
                    System.out.println("update successfully.....");
                }
                else {
                    System.out.println("error to update guest....");
                }

            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }



            }




    }

    public static void DeleteGuest(Connection conn, Scanner scanner){
        System.out.println("******************Delete User**************");

        System.out.println("enter the guest id:");
        int guest_id=scanner.nextInt();

        if(!checkexist(conn,guest_id)){
            System.out.println("guest doesnt exist");
        }
        else{
            try
            {
                String query="Delete from reservation  where id="+ guest_id;

                Statement st=conn.createStatement();
                int effectedrow=st.executeUpdate(query);
                if(effectedrow>0){
                    System.out.println("deleted successfully.......");
                }
                else {
                    System.out.println("failed to delete guest.......");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    private static boolean checkexist(Connection conn,int reservation_id){
        try{
            String query ="select id from reservation where id= " +  reservation_id;
            Statement st=conn.createStatement();
            ResultSet result =st.executeQuery(query);
            return result.next();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


       public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
       int i = 5;
        while(i!=0){
           System.out.print(".");
          Thread.sleep(1000);
            i--;
        }
        System.out.println();
       System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }
}
