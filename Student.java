package sample;

public class Student extends Patron{
    Student(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.Phone_Number = phone_number;
        this.adress = adress;
        this.card_number = card_number;
        is_facullty_member=false;
    }
}
