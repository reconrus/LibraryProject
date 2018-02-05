package main.java.librinno.classes;


/**
 * faculty of library
 */
public class Faculty extends Patron {
    /**
     * setter
     * @param name - name of user
     * @param phone_number -phone number of user
     * @param adress where user lives
     * @param card_number user id
     */
    Faculty(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.Phone_Number = phone_number;
        this.adress = adress;
        this.card_number = card_number;
        is_facullty_member=true;
    }
}
