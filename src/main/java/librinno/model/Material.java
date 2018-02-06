package main.java.librinno.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ilnur Mamedbakov on 25.01.2018.
 * All types of materials have a few same properties:
 *id
 *title
 *author
 *price
 *KeyWords
 * with setters and getters
 * And all types are inherited from this class Material
 */
public class Material {
    private int id;
    private String title;
    private String author;
    private int price;
    private String KeyWords;



    public int getId() {
        return id;
    }
    public void setId() {
        //each card_number is individual in case of increasing there won't be identic id's
        int max_id=0;
        try {
            Statement stmt=Database.con.createStatement();
            //get infromation from database
            ResultSet rs = stmt.executeQuery("SELECT id FROM Books");
            while (rs.next()){
                int id_an=rs.getInt("id");
                if(id_an>max_id)
                    max_id=id_an;
            }
            max_id++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.id=max_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyWords() {
        return KeyWords;
    }

    public void setKeyWords(String keyWords) {
        KeyWords = keyWords;
    }
}
