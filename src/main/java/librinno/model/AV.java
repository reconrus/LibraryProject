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
    public AV(String title,String author,int price, String keyWords,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setStatus(status);
    }
    public AV(String title,String author,int price, String keyWords){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
    }
    public AV(String av, int id, String title, String author, int price, String keyWords,LocalDate date,String status,int userid){
        setType(av);
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setDate(date);
        setStatus(status);
        setUserId(userid);

    }
    public AV(int id,String title,String author,int price, String keyWords ){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
