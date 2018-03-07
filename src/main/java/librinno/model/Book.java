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
    private boolean bestseller;
    private boolean reference;

    public boolean getBestseller() {
        return bestseller;
    }

    public boolean getReference(){
        return reference;
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

    public void setBestseller(boolean bestseller) {
        this.bestseller = bestseller;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public Book(String title, String author, String publisher, String edition, int price, String keyWords, Boolean bestseller, boolean reference, int year, String status) {
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setStatus(status);
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean bestseller, boolean reference, int year, int amount, String status) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setTotalNumber(amount);
        setStatus(status);
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean bestseller, boolean reference, int year, int amount) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setTotalNumber(amount);
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean bestseller, boolean reference, int year, int amount, LocalDate date) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setTotalNumber(amount);
        setReturnDate(date);
        setType("Book");
    }
    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean bestseller, boolean reference, int year, int amount, LocalDate date, String status, int userid) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setTotalNumber(amount);
        setReturnDate(date);
        setStatus(status);
        setUserId(userid);
        setType("Book");
    }

    public Book(int id, String title, String author, String publisher, String edition, int price, String keyWords, boolean bestseller, boolean reference, int year, int amount, int total) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setBestseller(bestseller);
        setReference(reference);
        setYear(year);
        setNumberAvailable(amount);
        setTotalNumber(total);
        setType("Book");
    }
}


