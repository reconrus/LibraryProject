package main.java.librinno.model;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Ilnur Mamedbakov on 03.02.2018.
 * Class Article inherited from class Material
 * And its a properties of Article:
 *journal
 *editor
 *date
 * with setters and getters
 */
public class Article extends Material{
    private String journal;
    private String editor;
    private LocalDate date;

    public Article(String title,String author,int price, String keyWords, Boolean is_bestseller,
                   boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(yearOfDate,monthOfDate,dayOfDate);
        setStatus(status);
    }
    public Article(int id,String title,String author,int price, String keyWords, Boolean is_bestseller,
                   boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate,String status){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setIs_bestseller(is_bestseller);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(yearOfDate,monthOfDate,dayOfDate);
        setStatus(status);
    }
    public String getJournal() {
        return journal;
    }
    public void setJournal(String journal) {
        this.journal = journal;
    }
    public String getEditor() {
        return editor;
    }
    public void setEditor(String editor) {
        this.editor = editor;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(int year,int month,int day) {
        this.date =LocalDate.of(year,month,day);
    }
}
