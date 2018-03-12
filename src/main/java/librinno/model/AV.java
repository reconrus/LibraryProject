package main.java.librinno.model;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class AV inherited from class Material
 * AV already have all properties in Class Material
 */
public class AV extends Material {
    //constructors for different cases
    //in some situations not all information is needed
    public AV(String title,String author,int price, String keyWords,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setStatus(status);
        setType("AV");
    }
    public AV(String title,String author,int price, String keyWords){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setType("AV");
    }
    public AV(int id, String title, String author, int price, String keyWords,LocalDate date,String status,int userid){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReturnDate(date);
        setStatus(status);
        setUserId(userid);
        setType("AV");
    }
    public AV(int id,String title,String author,int price, String keyWords, int amountAvailable, int amountTotal){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setType("AV");
        setNumberAvailable(amountAvailable);
        setTotalNumber(amountTotal);
    }

    public AV(int id,String title,String author,int price, String keyWords){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setType("AV");
    }

}
