package sample;

/**
 * student card in library
 */
public class Student extends Patron{
    /**
     * setter
     * @param name of student
     * @param phone_number of student
     * @param adress where student lives
     * @param card_number id number
     */
    Student(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.Phone_Number = phone_number;
        this.adress = adress;
        this.card_number = card_number;
        is_facullty_member=false;
    }
}
