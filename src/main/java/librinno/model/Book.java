package main.java.librinno.model;

import java.time.LocalDate;

/**
 * Class Book inherited from class Material
 * Article has extra properties: publisher, year, edition
 */
public class Book extends Material {
    //new properties
    private String publisher;
    private int year;
    private String edition;
    private boolean bestseller;
    private boolean reference;
    //getters and setters

    /**
     * setter
     *
     * @param bestseller set if book is bestseller or not
     */
    public void setBestseller(boolean bestseller) {
        this.bestseller = bestseller;
    }

    /**
     * getter
     *
     * @return if book is bestseller
     */
    public boolean getBestseller() {
        return bestseller;
    }


    /**
     * setter
     *
     * @param reference set if book is reference or not
     */
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    /**
     * getter
     *
     * @return if book is reference
     */
    public boolean getReference() {
        return reference;
    }

    /**
     * setter
     *
     * @param year year of book publishing
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * getter
     *
     * @return year of book publishing
     */
    public int getYear() {
        return year;
    }

    /**
     * setter
     *
     * @param edition edition of the book
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    /**
     * getter
     *
     * @return edition of the book
     */
    public String getEdition() {
        return edition;
    }

    /**
     * setter
     *
     * @param publisher publisher of the book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * getter
     *
     * @return publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    //constructors for different cases
    //in some situations not all information is needed
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


