import java.util.ArrayList;

/**
 * Created by kor19 on 25.01.2018.
 */
public class Material {
    private int id;
    private String title;
    private ArrayList<String> author;
    private int price;
    private ArrayList<String> KeyWords;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public ArrayList<String> getKeyWords() {
        return KeyWords;
    }
    public void setKeyWords(ArrayList<String> keyWords) {
        KeyWords = keyWords;
    }
    public ArrayList<String> getAuthor() {
        return author;
    }
    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }
}
