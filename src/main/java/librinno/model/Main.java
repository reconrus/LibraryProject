package sample;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException{
        //launching database and enter user in it
        Database db=new Database();
        User user=new User();
        //it is typical version of creation
        //use for project
        //    db.user_creation(user);


        //test!!!!
        //version for the test
        //automatically puts information in table
        db.test(user);
    }
}
