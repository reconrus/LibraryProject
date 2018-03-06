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
import java.time.LocalDate;

public class Book extends Material {
    private String publisher;
    private int year;
    private String edition;
    private boolean is_bestseller;
    private int left_time;
    private boolean reference;
    private int total;
    @Override
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private LocalDate date;

    public boolean isIs_bestseller() {
        return is_bestseller;
    }

    public boolean isReference(){
        return reference;
    }

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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
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

    public void setIs_bestseller(boolean is_bestseller) {
        this.is_bestseller = is_bestseller;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Book(String title, String author, String publisher, String edition, int price, String keyWords, Boolean is_bestseller, boolean reference, int year, String status) {
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
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean is_bestseller, boolean reference, int year, int amount, String status) {
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
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean is_bestseller, boolean reference, int year, int amount) {
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
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean is_bestseller, boolean reference, int year, int amount, LocalDate date) {
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
        setDate(date);
        setType("Book");
    }
    public Book(String book,int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean is_bestseller, boolean reference, int year, int amount, LocalDate date, String status, int userid) {
        setType(book);
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
        setDate(date);
        setStatus(status);
        setUserId(userid);
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean is_bestseller, boolean reference, int year, int amount, int total) {
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
        setDate(date);
        setTotal(total);
        setType("Book");
    }
}


