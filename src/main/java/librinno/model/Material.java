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
    protected int id;
    protected String title;
    protected String author;
    protected int price;
    protected String KeyWords;
    protected boolean reference;
    protected boolean is_bestseller;
    protected int number;


    public int getId() {
        return id;
    }
    public void setId(int id) {this.id=id;}
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
    public boolean isIs_bestseller() {
        return is_bestseller;
    }
    public void setIs_bestseller(boolean is_bestseller) {
        this.is_bestseller = is_bestseller;
    }
    public boolean isReference() {
        return reference;
    }
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public void setNumber(int number){ this.number = number; }
    public int getNumber(){ return this.number;}
}
