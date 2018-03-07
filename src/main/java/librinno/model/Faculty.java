package main.java.librinno.model;

/**
 * faculty of library
 */
public class Faculty extends Patron {
    /**
     * setter
     * @param name - name of user
     * @param phone_number -phone number of user
     * @param address where user lives
     * @param card_number user id
     */

    public Faculty(String name, String phone_number, String address, int card_number) {
        this.name = name;
        this.phoneNumber = phone_number;
        this.adress = address;
        this.card_number = card_number;
        is_facullty_member=true;
    }



}
