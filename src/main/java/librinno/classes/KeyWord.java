package main.java.librinno.classes;

/**
 * Created by kor19 on 25.01.2018.
 */
public class KeyWord {
    private int id;
    private String keyword;

    public KeyWord(){}
    public KeyWord(int id,String keyword){
        setId(this.id);
        setKeyword(this.keyword);
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setKeyword(String keyword){
        this.keyword=keyword;
    }
    public String getKeyword(){
        return(this.keyword);
    }
}

