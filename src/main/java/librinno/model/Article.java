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
    private String date;
    private boolean reference;

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public boolean getReference(){
        return reference;
    }

    private LocalDate date_of_return;

    public Article(String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor, String date,String status){
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(date);
        setStatus(status);
        setType("Article");
    }
    public Article(int id,String title,String author,int price, String keyWords,
                    boolean reference,String journal,String editor, String date,String status){
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
        setType("Article");
    }
    public Article(int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor, String date){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(date);
        setType("Article");
    }

    public Article(int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor, String date, int amountAvailable, int amountTotal){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setDate(date);
        setType("Article");
        setNumberAvailable(amountAvailable);
        setTotalNumber(amountTotal);
    }

    public Article(String type,int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor,String date, String status, int userid){
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

    public Article(String type,int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor, LocalDate returnDate, String status, int userid){
        setType(type);
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setReturnDate(returnDate);
        setStatus(status);
        setUserId(userid);
        setType("Article");
    }


    public Article(int id,String title,String author,int price, String keyWords,
                   boolean reference,String journal,String editor, LocalDate returnDate,String status){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setKeyWords(keyWords);
        setReference(reference);
        setJournal(journal);
        setEditor(editor);
        setReturnDate(returnDate);
        setStatus(status);
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
