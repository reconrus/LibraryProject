package main.java.librinno.model;
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
    private int[] date;

    public Article(){
        date =new int[3];
    }
    public Article(String journal,String editor,int[] date){
        date =new int[3];
        setJournal(this.journal);
        setEditor(this.editor);
        setDate(this.date);
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
    public int[] getDate() {
        return date;
    }
    public void setDate(int[] date) {
        this.date = date;
    }
}
