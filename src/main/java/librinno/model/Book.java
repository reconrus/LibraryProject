package main.java.librinno.model;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class Book inherited from class Material
 * And its a properties of book:
 *publisher
 *year
 *edition
 * with setters and getters
 */
import java.util.Scanner;

public class Book extends Material {
    private String publisher;
    private int year;
    private int edition;
    private boolean is_bestseller;
    private int left_time;
    private boolean reference;
    private int total;

    public int get_left_time() {
        return left_time;
    }
    public void set_left_time(int left_time) {
        this.left_time = left_time;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getEdition() {
        return edition;
    }
    public void setEdition(int edition) {
        this.edition = edition;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Book(String title, String author, String publisher, int edition, int price, String keyWords, Boolean is_bestseller,boolean reference,int year,String status) {
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setYear(year);
        setStatus(status);
    }

    public Book(int id, String title, String author, String publisher, int edition, int price, String keyWords, boolean is_bestseller,boolean reference,int year, int amount,String status) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setYear(year);
        setNumber(amount);
        setStatus(status);
    }
    public Book(int id, String title, String author, String publisher, int edition, int price, String keyWords, boolean is_bestseller,boolean reference,int year, int amount) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setYear(year);
        setNumber(amount);
    }

    public Book(int id, String title, String author, String publisher, int edition, int price, String keyWords, boolean is_bestseller,boolean reference,int year, int amount, int total) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setYear(year);
        setNumber(amount);
        setTotal(total);
    }


}


