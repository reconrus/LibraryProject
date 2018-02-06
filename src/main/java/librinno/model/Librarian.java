package main.java.librinno.model;

/**
 * librarian of library
 */
public class Librarian extends User {
    /**
     * setter
     * @param name - name of librarian
     * @param phone_number - number os librarian
     * @param adress - where librarian lives(librarian is user too)
     * @param card_number - id of librarian
     */
    public void Librarian(String name,String phone_number,String adress,int card_number){
        this.name=name;
        this.Phone_Number=phone_number;
        this.adress=adress;
        this.card_number=card_number;
    }
}
