package main.java.librinno.model;

import java.time.LocalDate;

/**
 * Class Article inherited from class Material
 * Article has extra properties: journal,editor,date and reference
 */
public class Article extends Material {
    //new properties
    private String journal;
    private String editor;
    private String date;
    private boolean reference;


    //getters and setters

    /**
     * setter
     *
     * @param reference is reference or not
     */
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    /**
     * getter
     *
     * @return if is reference or not
     */
    public boolean getReference() {
        return reference;
    }

    /**
     * setter
     *
     * @param journal in which journal article is published
     */
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * getter
     *
     * @return journal of article
     */
    public String getJournal() {
        return journal;
    }

    /**
     * setter
     *
     * @param editor of the journal
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * getter
     *
     * @return editor of journal
     */
    public String getEditor() {
        return editor;
    }

    /**
     * setter
     *
     * @param date of publishing
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter
     *
     * @return date of publishing
     */
    public String getDate() {
        return date;
    }


    //constructors for different cases
    //in some situations not all information is needed
    public Article(String title, String author, int price, String keyWords,
                   boolean reference, String journal, String editor, String date, String status) {
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

    public Article(int id, String title, String author, int price, String keyWords,
                   boolean reference, String journal, String editor, String date) {
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

    public Article(int id, String title, String author, int price, String keyWords,
                   boolean reference, String journal, String editor, String date, int amountAvailable, int amountTotal) {
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

    public Article(int id, String title, String author, int price, String keyWords,
                   boolean reference, String journal, String editor, LocalDate returnDate, String status, int userid) {
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
}
