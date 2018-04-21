package main.java.librinno.model;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
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
    //properties of material
    public int id;
    public String title;
    public String author;
    public int price;
    public String KeyWords;
    public String status;
    private LocalDate returnDate;
    private int userId;
    protected String type;
    public int numberAvailable;
    private int totalNumber;
    private int fine;

    //getters and setters
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
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
    public int getOverdue(User whoTook,LocalDate now) {
        if (whoTook.getType().equals("Professor")){
            return 0;
        }
        else{
            return (int) DAYS.between(returnDate, now);
        }
    }


    public String getKeyWords() {
        return KeyWords;
    }

    public void setKeyWords(String keyWords) {
        KeyWords = keyWords;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public int getNumberAvailable(){ return this.numberAvailable;}
    public void setNumberAvailable(int number){ this.numberAvailable = number; }
    public int getUserId(){ return this.userId;}
    public void setUserId(int userId){ this.userId = userId; }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getFine() {
        return fine;
    }

    public void setFine() {
        this.fine = Librarian.fine(this.id);
    }
}
