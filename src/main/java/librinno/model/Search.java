package main.java.librinno.model;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Search {
    public static final Logger LOGGER = Logger.getLogger("GLOBAL");

    private static Database db = new Database();
    public Search(){}

    public static ArrayList<User> userByName(String name){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {
                if (rs.getString("Name").toLowerCase().indexOf(name.toLowerCase()) >= 0) {
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this name in DB");
            else
                LOGGER.trace("Didn't Found this name in DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching name of User");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<User> userByAddress(String address){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {
                if (rs.getString("Address").toLowerCase().indexOf(address.toLowerCase()) >= 0) {
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this Address in DB");
            else
                LOGGER.trace("Didn't Found this Address in DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching Address of User");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<User> userByPhoneNumber(String number){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {
                if (rs.getString("Phone_number").toLowerCase().indexOf(number.toLowerCase()) >= 0) {
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this number in DB");
            else
                LOGGER.trace("Didn't Found this number in DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching number of User");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<User> userByType(String type){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {
                if (rs.getString("Type").toLowerCase().indexOf(type.toLowerCase()) >= 0) {
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this type in DB");
            else
                LOGGER.trace("Didn't Found this type in DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching type of User");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<User> userByEmail(String email){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {
                if (rs.getString("Email").toLowerCase().indexOf(email.toLowerCase()) >= 0) {
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this email in DB");
            else
                LOGGER.trace("Didn't Found this email in DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching email of User");
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<AV> avByName(String name){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        Statement stmt = null;
        try{
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
            while (rs.next()) {
                if (rs.getString("Name").toLowerCase().indexOf(name.toLowerCase())>=0){
                    AV av = new AV(rs.getString("Name"),rs.getString("Author"),rs.getInt("Price"),rs.getString("Keywords"));
                    arrayList.add(av);
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this name in AV DB");
            else
                LOGGER.trace("Didn't Found this name in AV DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching name of AV");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<AV> avByAuthor(String author){
        //вводится строка из авторов разделенных запятыми. Метод сам распарсивает их на отдельных авторов.
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        ArrayList<String> authorList = new ArrayList(separateIntoList(author));
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
            while (rs.next()){
                for (int i = 0; i <authorList.size() ; i++) {
                    if (rs.getString("Author").toLowerCase().indexOf(authorList.get(i).toLowerCase())>=0){
                        AV av = new AV(rs.getInt("id"),rs.getString("Name"),rs.getString("Author"),rs.getInt("Price"),rs.getString("Keywords"));
                        arrayList.add(av);
                        break;//чтоб не добавлять по несколько раз один и тот же материал
                    }
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this author(authors) in AV DB");
            else
                LOGGER.trace("Didn't Found this author(authors) in AV DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching author(authors) of AV");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<AV> avByPrice(int price){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        Statement stmt = null;
        try{
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
            while (rs.next()) {
                if (rs.getInt("Price")==price){
                    AV av = new AV(rs.getString("Name"),rs.getString("Author"),rs.getInt("Price"),rs.getString("Keywords"));
                    arrayList.add(av);
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this price in AV DB");
            else
                LOGGER.trace("Didn't Found this price in AV DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching price of AV");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<AV> avByKeyWords(String keyWords){
        //вводится строка из кейвордов разделенных запятыми. Метод сам распарсивает их на отдельные кейворды.
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        ArrayList<String> authorList = new ArrayList(separateIntoList(keyWords));
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
            while (rs.next()){
                for (int i = 0; i <authorList.size() ; i++) {//перебираю все распарсенные кейВорды
                    if (rs.getString("Keywords").toLowerCase().indexOf(authorList.get(i).toLowerCase())>=0){
                        AV av = new AV(rs.getInt("id"),rs.getString("Name"),rs.getString("Author"),rs.getInt("Price"),rs.getString("Keywords"));
                        arrayList.add(av);
                        break;
                    }
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this keyWord(keyWords) in AV DB");
            else
                LOGGER.trace("Didn't Found this keyWord(keyWords) in AV DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching keyWord(keyWords) of AV");
            e.printStackTrace();
        }
        return arrayList;
    }
    
    private static ArrayList<String> separateIntoList(String line){
        ArrayList<String> arrayList = new ArrayList<>();
        for(String word:line.split(",")){
            arrayList.add(word.trim());
        }
        return arrayList;
    }
}
