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

    public boolean get_is_bestseller() {
        return is_bestseller;
    }

    public void set_is_bestseller(boolean is_bestseller) {
        this.is_bestseller = is_bestseller;
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
    public void set_reference(boolean reference){this.reference=reference;}
    public boolean get_reference(){return reference;}
    Book() {
        //now entering in simple form
        //get information about user
        Scanner sc = new Scanner(System.in);
        setId();
        setTitle(sc.next());
        setAuthor(sc.next());
        setPublisher(sc.next());
        setEdition(sc.nextInt());
        setPrice(sc.nextInt());
        //доделать
        setKeyWords(sc.next());
        //после идёт owner,который ноль
        set_is_bestseller(sc.nextBoolean());
        set_left_time(sc.nextInt());
        set_reference(sc.nextBoolean());
    }
    public Book(String title, String author, String pubisher, int edition, int price, String keyWords, Boolean is_bestseller, int left_time,boolean reference) {
        setId();
        setTitle(title);
        setAuthor(author);
        setPublisher(pubisher);
        setEdition(edition);
        setPrice(price);
        //доделать
        setKeyWords(keyWords);
        //после идёт owner,который ноль
        set_is_bestseller(is_bestseller);
        set_left_time(left_time);
        set_reference(reference);
    }
}
