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


    Book() {
        //now entering in simple form
        //get information about user
        Scanner sc = new Scanner(System.in);
        setTitle(sc.next());
        setAuthor(sc.next());
        setPublisher(sc.next());
        setEdition(sc.nextInt());
        setPrice(sc.nextInt());
        setKeyWords(sc.next());
        setIs_bestseller(sc.nextBoolean());
        setReference(sc.nextBoolean());
    }

    public Book(String title, String author, String publisher, int edition, int price, String keyWords, Boolean is_bestseller,boolean reference,int year) {
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setEdition(edition);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setYear(year);
    }

    public Book(int id, String title, String author, String publisher, int edition, int price, String keyWords, Boolean is_bestseller,boolean reference,int year) {
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
    }
}


