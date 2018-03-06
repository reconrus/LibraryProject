package main.java.librinno.model;

import java.time.LocalDate;

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
    private String type;
    private boolean reference;

    public void setReference(boolean regerence) {
        this.reference = regerence;
    }

    public boolean getReference(){
        return reference;
    }

    private LocalDate date_of_return;
    public Article(String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(yearOfDate,monthOfDate,dayOfDate);
        setStatus(status);
        setType("Article");
    }
    public Article(int id,String title,String author,int price, String keyWords,
                    boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate,String status){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(yearOfDate,monthOfDate,dayOfDate);
        setStatus(status);
        setType("Article");
    }
    public Article(int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(yearOfDate,monthOfDate,dayOfDate);
        setType("Article");
    }
    public Article(String type,int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor,LocalDate date, String status, int userid){
        setType(type);
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(date);
        setStatus(status);
        setUserId(userid);
        setType("Article");
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
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate get_return_date() {
        return date_of_return;
    }
    public void set_return_date(int year,int month,int day) {
        this.date_of_return =LocalDate.of(year,month,day);
    }
}
