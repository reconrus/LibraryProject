package main.java.librinno.model;

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * librarian of library
 */
public class Librarian extends User {
    /**
     * setter
     *
     * @param name - name of librarian
     * @param phone_number - number os librarian
     * @param adress - where librarian lives(librarian is user too)
     * @param card_number - id of librarian
     */
    private Database db = new Database();

    public void Librarian(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.Phone_Number = phone_number;
        this.adress = adress;
        this.card_number = card_number;
    }

    public void add_user() {
        User user = new User();
        db.user_creation(user);
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library SET Type='User' WHERE Card_number=" + user.card_number);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while creating user");
        }
    }

    public void delete_user_by_id(int user_id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("user with such id didn't found");
        }
        //есть еще идея удалять по именам,но чтобы библиотекарю вылезло уведомление,мол,может удалиться более 1 юзера
    }

    public void modify_user(User user) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library " +
                    "SET Name=?,Address=?,Phone_number=?,Type=?,Password=? where Card_number=" + user.get_card_number());
            pr.setString(1, user.get_name());
            pr.setString(2, user.get_adress());
            pr.setString(3, user.get_number());
            pr.setString(4, user.get_type());
            pr.setString(5, user.get_password());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add_book() {
        Book book = new Book();
        db.book_creation(book);
    }

    public void delete_book_by_id(int id) {

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Books WHERE id=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("book with such id didn't found");
        }
    }


    public void modify_book(Book book) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Books " +
                    "SET Name=?,Author=?,Publisher=?,Edition=?,Price=?,Keywords=?,owner=?,is_bestseller=?,time_left=?,is_reference=? where id=" + book.getId());
            pr.setString(1, book.getTitle());
            pr.setString(2, book.getAuthor());
            pr.setString(3, book.getPublisher());
            pr.setInt(4, book.getEdition());
            pr.setInt(5, book.getPrice());
            pr.setString(6, book.getKeyWords());
            //пока только так,по идее считывать надо
            pr.setInt(7, 0);
            pr.setBoolean(8, book.get_is_bestseller());
            pr.setInt(9, book.get_left_time());
            pr.setBoolean(10, book.get_reference());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<User> get_all_users() {
        Database db = new Database();
        LinkedList<User> users = new LinkedList<User>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from Users_of_the_library");

            while (rs.next()) {
                String name=rs.getString("Name");
                String address=rs.getString("Address");
                String Phonenumber=rs.getString("Phone_number");
                int id=rs.getInt("Card_number");
                String type=rs.getString("Type");
                String password=rs.getString("Password");
                User user=new User(name, address,Phonenumber,id,type,password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //вам для удобства,чтобы видели,что брать TODO delete
       // for(int i=0;i<users.size();i++){
        //   System.out.println(users.get(i).get_name()+" "+users.get(i).get_adress()+
        //   " "+users.get(i).get_number()+" "+users.get(i).get_another_card_number()+" "+users.get(i).get_type()+" "+users.get(i).get_password());
       // }

        return users;
    }
}
