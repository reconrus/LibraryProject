package main.java.librinno.model;

import java.util.Scanner;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class AV inherited from class Material
 * AV already have all propirties in Class Material
 */
public class AV extends Material {
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
    public AV(int id,String title,String author,int price, String keyWords, Boolean is_bestseller,
              boolean reference,String status){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setStatus(status);
    }
}
