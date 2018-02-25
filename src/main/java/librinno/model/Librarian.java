package main.java.librinno.model;

import java.sql.*;
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

    public void update_status(int id, String new_type) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library SET Type=? WHERE Card_number=" + id);
            pr.setString(1, new_type);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modify_user(int id) {
        try {
            //if new string is empty,then don't change
            Statement stmt = Database.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name,Address,Phone_number,Type,Password from Users_of_the_library WHERE Card_number=" + id);

            String username = "";
            String useradress = "";
            String userphone = "";
            String usertype = "";
            String userpassword = "";
            while (rs.next()) {
                username = rs.getString("Name");
                useradress = rs.getString("Address");
                userphone = rs.getString("Phone_number");
                usertype = rs.getString("Type");
                userpassword = rs.getString("Password");
            }
            Scanner sc = new Scanner(System.in);
            String new_name = sc.nextLine();
            if (new_name.equals(""))
                new_name = username;
            String new_adress = sc.nextLine();
            if (new_adress.equals(""))
                new_adress = useradress;
            String new_phone = sc.nextLine();
            if (new_phone.equals(""))
                new_phone = userphone;
            String new_type = sc.nextLine();
            if (new_type.equals(""))
                new_type = usertype;
            String new_password = sc.nextLine();
            if (new_password.equals(""))
                new_password = userpassword;
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library SET Name=?,Address=?,Phone_number=?,Type=?,Password=? where Card_number=" + id);
            pr.setString(1, new_name);
            pr.setString(2, new_adress);
            pr.setString(3, new_phone);
            pr.setString(4, new_type);
            pr.setString(5, new_password);
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

    public void modify_book(int id) {
        try {
            //if new string is empty,then don't change
            Statement stmt = Database.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name,Author,Publisher,Edition,Price,Keywords,owner,is_bestseller,time_left,is_reference" +
                    " from Books WHERE id=" + id);
            String name = "";
            String author = "";
            String publisher = "";
            int edition = 0;
            int price = 0;
            String keywords = "";
            int owner = 0;
            boolean is_bestseller = false;
            String time = "";//in future will be time type
            boolean reference = false;
            while (rs.next()) {
                //about edition and price and owner: if they are -1,then don't change
                //bestseller and reference - make as strings,later convert to boolean
                name = rs.getString("Name");
                author = rs.getString("Author");
                publisher = rs.getString("Publisher");
                edition = rs.getInt("Edition");
                price = rs.getInt("Price");
                keywords = rs.getString("Keywords");
                owner = rs.getInt("owner");
                is_bestseller = rs.getBoolean("is_bestseller");
                time = rs.getString("time_left");
                reference = rs.getBoolean("is_reference");
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("book name");
            String new_name = sc.nextLine();
            if (new_name.equals(""))
                new_name = name;
            System.out.println("author");
            String new_author = sc.nextLine();
            if (new_author.equals(""))
                new_author = author;
            System.out.println("publisher");
            String new_publisher = sc.nextLine();
            if (new_publisher.equals(""))
                new_publisher = publisher;
            System.out.println("edition");
            int new_edition = sc.nextInt();
            if (new_edition==-1)
                new_edition = edition;
            System.out.println("price");
            int new_price = sc.nextInt();
            if (new_price==-1)
                new_price = price;
            System.out.println("key words");
            //nexline isn't working
            String new_key_words = sc.next();
            if (new_key_words.equals(""))
                new_key_words = keywords;
            System.out.println("owner");
            int new_owner = sc.nextInt();
            if (new_owner==-1)
                new_owner = owner;
            //i don't know how to make an exception for not changing
            boolean new_is_bestseller = sc.nextBoolean();
            String new_time = sc.next();
            if (new_time.equals(""))
                new_time = time;
            boolean new_is_reference = sc.nextBoolean();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Books " +
                    "SET Name=?,Author=?,Publisher=?,Edition=?,Price=?,Keywords=?,owner=?,is_bestseller=?,time_left=?,is_reference=? where id=" + id);
            pr.setString(1, new_name);
            pr.setString(2, new_author);
            pr.setString(3, new_publisher);
            pr.setInt(4, new_edition);
            pr.setInt(5, new_price);
            pr.setString(6,new_key_words);
            pr.setInt(7,new_owner);
            pr.setBoolean(8,new_is_bestseller);
            pr.setString(9,new_time);
            pr.setBoolean(10,new_is_reference);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
