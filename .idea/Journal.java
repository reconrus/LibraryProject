package sample;

/**
 * Created by kor19 on 03.02.2018.
 */
public class Journal {
    private int id;
    private String name;
    public Journal(){};
    public Journal(int id,String name){
        setId(this.id);
        setName(this.name);
    };

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
