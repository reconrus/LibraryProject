package main.java.librinno.model;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class AV inherited from class Material
 * AV already have all propirties in Class Material
 */
public class AV extends Material {
    private LocalDate date;
    private String type;
    public AV(String title,String author,int price, String keyWords, Boolean is_bestseller,
               boolean reference,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setStatus(status);
    }
    public AV(String title,String author,int price, String keyWords, Boolean is_bestseller,
              boolean reference){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
    }
    public AV(String av, int id, String title, String author, int price, String keyWords, Boolean is_bestseller,
              boolean reference, LocalDate date,String status,int userid){
        setType(av);
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setDate(date);
        setStatus(status);
        setUserId(userid);

    }
    public AV(int id,String title,String author,int price, String keyWords, Boolean is_bestseller,
              boolean reference){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
}
