package main.java.librinno.classes;

/**
 * patron of library
 */
public class Patron extends User {
    public boolean is_facullty_member;

    /**
     * setter
     * @param name of patron
     * @param phone_number  of patron
     * @param adress where patron lives
     * @param card_number patron id
     */
    public void Patron(String name,String phone_number,String adress,int card_number){
        this.name=name;
        this.Phone_Number=phone_number;
        this.adress=adress;
        this.card_number=card_number;
    }
}
