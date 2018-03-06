package main.java.librinno.model;

import java.time.LocalDate;

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
    public int id;
    public String title;
    public String author;
    public int price;
    public String KeyWords;
    public int number;
    public String status;
    private LocalDate returnDate;
    private int userId;
    protected String type;

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(int year,int month,int day) {
        this.returnDate =LocalDate.of(year,month,day);
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setNumber(int number){ this.number = number; }
    public int getNumber(){ return this.number;}
    public void setUserId(int userId){ this.userId = userId; }
    public int getUserId(){ return this.userId;}
}
