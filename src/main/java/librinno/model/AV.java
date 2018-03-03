package main.java.librinno.model;

import java.util.Scanner;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class AV inherited from class Material
 * AV already have all propirties in Class Material
 */
public class AV extends Material {
    AV(){
        Scanner sc = new Scanner(System.in);
        setTitle(sc.next());
        setAuthor(sc.next());
        setPrice(sc.nextInt());
        setKeyWords(sc.next());
        setIs_bestseller(sc.nextBoolean());
        setReference(sc.nextBoolean());
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
}
