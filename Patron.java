package sample;

public class Patron extends User {
    public boolean is_facullty_member;
    public void Patron(String name,String phone_number,String adress,int card_number){
        this.name=name;
        this.Phone_Number=phone_number;
        this.adress=adress;
        this.card_number=card_number;
    }
}
