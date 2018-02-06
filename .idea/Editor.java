package sample;

/**
 * Created by kor19 on 03.02.2018.
 */

public class Editor {
    private int id;
    private String name;

    public Editor(){}
    public Editor(int id,String name){
        setId(this.id);
        setName(this.name);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
