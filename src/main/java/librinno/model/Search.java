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

    /**
     * Search in all materials and search in title,author,keyword
     * Without criteria
     * @param requiredMaterial
     * @return
     */
    public static ArrayList<Material> materialByWord(String requiredMaterial){
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        ArrayList<Material> arrayList= new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
            while (rs.next()) {
                if (rs.getString("Name").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0) {
                    arrayList.add(l.avById(rs.getInt("id")));
                    continue;
                }
                if (rs.getString("Author").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0) {
                    arrayList.add(l.avById(rs.getInt("id")));
                    continue;
                }
                if (rs.getString("Keywords").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0) {
                    arrayList.add(l.avById(rs.getInt("id")));
                    continue;
                }
            }
            ResultSet rs2 = stmt.executeQuery("SELECT  * FROM articles");
            while (rs2.next()) {
                if (rs2.getString("Name").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.articleById(rs2.getInt("id")));
                    continue;
                }
                if (rs2.getString("Author").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.articleById(rs2.getInt("id")));
                    continue;
                }
                if (rs2.getString("Keywords").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.articleById(rs2.getInt("id")));
                    continue;
                }
                if (rs2.getString("Journal").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.articleById(rs2.getInt("id")));
                    continue;
                }
                if (rs2.getString("Editor").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.articleById(rs2.getInt("id")));
                    continue;
                }
            }
            ResultSet rs3 = stmt.executeQuery("SELECT  * FROM books");
            while (rs3.next()) {
                if (rs3.getString("Name").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.bookByID(rs3.getInt("id")));
                    continue;
                }
                if (rs3.getString("Author").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.bookByID(rs3.getInt("id")));
                    continue;
                }
                if (rs3.getString("Publisher").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.bookByID(rs3.getInt("id")));
                    continue;
                }
                if (rs3.getString("Keywords").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.bookByID(rs3.getInt("id")));
                    continue;
                }
                if (rs3.getString("Edition").trim().toLowerCase().indexOf(requiredMaterial.trim().toLowerCase())>=0){
                    arrayList.add(l.bookByID(rs3.getInt("id")));
                    continue;
                }
            }
            if (arrayList.size() > 0)
                LOGGER.trace("Found this keyWord(keyWords) in AV DB");
            else
                LOGGER.trace("Didn't Found this keyWord(keyWords) in AV DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching  of material");
            e.printStackTrace();
        }


        return arrayList;
    }

    /**
     * Search in selected material with selected criteria
     *
     * @param word
     * @param isBook
     * @param isArticle
     * @param isAv
     * @param isTitle
     * @param isAuthor
     * @param isKeyword
     * @param isBestseller //if true we show only BestSeller books, if false we show all books
     * @param isReference  //if true we show all books,articles, if false only not reference books,articles
     * @param isAvailable //if true we show only available materials, if false we show all materials
     * @return
     * @throws SQLException
     */
    public static ArrayList<Material> materialByWordWithCriteria(String word, boolean isBook, boolean isArticle, boolean isAv,
                                                                 boolean isTitle, boolean isAuthor, boolean isKeyword,boolean isBestseller, boolean isReference, boolean isAvailable) throws SQLException {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        ArrayList<Material> arrayList = new ArrayList<>();

        Statement stmt = null;
        stmt = db.con.createStatement();
        if (word.indexOf("AND")>=0) {
            if (isBook) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM books ");
                arrayList.addAll(searchingWithAND("book", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
            if (isArticle) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM articles");
                arrayList.addAll(searchingWithAND("article", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
            if (isAv) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM av");
                arrayList.addAll(searchingWithAND("av", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
        }else if(word.indexOf("OR")>=0){
            if (isBook) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM books ");
                arrayList.addAll(searchingWithOR("book", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
            if (isArticle) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM articles");
                arrayList.addAll(searchingWithOR("article", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
            if (isAv) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM av");
                arrayList.addAll(searchingWithOR("av", l, rs, word, isTitle, isAuthor, isKeyword, isBestseller, isReference, isAvailable));
            }
        }else {
            if (isBook) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM books ");
                arrayList.addAll(searching("book",l,rs,word,isTitle,isAuthor,isKeyword,isBestseller,isReference,isAvailable));
            }
            if (isArticle) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM articles");
                arrayList.addAll(searching("article",l,rs,word,isTitle,isAuthor,isKeyword,isBestseller,isReference,isAvailable));
            }
            if (isAv) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM av");
                arrayList.addAll(searching("av",l,rs,word,isTitle,isAuthor,isKeyword,isBestseller,isReference,isAvailable));
            }
        }

        return arrayList;
    }

    private static ArrayList<Material> searchingWithAND(String type,Librarian l,ResultSet rs,String word,boolean isTitle, boolean isAuthor, boolean isKeyword,boolean isBestseller, boolean isReference, boolean isAvailable){
        ArrayList<Material> arrayList = new ArrayList<>();
        String[] searchingWords = word.split("AND");
        try {
            while (rs.next()) {
                if ((isAvailable && l.getNumberOfCopiesOfBook(rs.getInt("id")) > 0) || !isAvailable) {
                    if (isTitle) {
                        int tempSovpad=0;//отвечает за совпадения, если все слова совпали то всё каеф
                        for (int i = 0; i <searchingWords.length ; i++) {//считаю количество совпавших слов, если совпали все введеные слова то смотрим дальше
                            if (rs.getString("Name").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad==searchingWords.length) {
                                if (type.equals("book")) {
                                    if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                            (!isReference && !rs.getBoolean("is_reference") || isReference))
                                        arrayList.add(l.bookByID(rs.getInt("id")));
                                } else if (type.equals("article")) {
                                    if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                        arrayList.add(l.articleById(rs.getInt("id")));
                                } else {
                                    arrayList.add(l.avById(rs.getInt("id")));
                                }
                                continue;

                        }
                    }
                    if (isAuthor) {
                        int tempSovpad=0;//отвечает за совпадения, если все слова совпали то всё каеф
                        for (int i = 0; i <searchingWords.length ; i++) {
                            if (rs.getString("Author").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad==searchingWords.length) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            } else if (type.equals("article")) {
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            } else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    }
                    if (isKeyword) {
                        int tempSovpad=0;//отвечает за совпадения, если все слова совпали то всё каеф
                        for (int i = 0; i <searchingWords.length ; i++) {
                            if (rs.getString("Keywords").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad==searchingWords.length) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            } else if (type.equals("article")) {
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            } else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace(); }
        return arrayList;
    }
    private static ArrayList<Material> searchingWithOR(String type,Librarian l,ResultSet rs,String word,boolean isTitle, boolean isAuthor, boolean isKeyword,boolean isBestseller, boolean isReference, boolean isAvailable){
        ArrayList<Material> arrayList = new ArrayList<>();
        String[] searchingWords = word.split("OR");
        try {
            while (rs.next()) {
                if ((isAvailable && l.getNumberOfCopiesOfBook(rs.getInt("id")) > 0) || !isAvailable) {
                    if (isTitle) {
                        int tempSovpad=0;
                        for (int i = 0; i <searchingWords.length ; i++) {
                            if (rs.getString("Name").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad>0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            } else if (type.equals("article")) {
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            } else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;

                        }
                    }
                    if (isAuthor) {
                        int tempSovpad=0;
                        for (int i = 0; i <searchingWords.length ; i++) {
                            if (rs.getString("Author").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad>0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            } else if (type.equals("article")) {
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            } else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    }
                    if (isKeyword) {
                        int tempSovpad=0;
                        for (int i = 0; i <searchingWords.length ; i++) {
                            if (rs.getString("Keywords").trim().toLowerCase().indexOf(searchingWords[i].trim().toLowerCase()) >= 0)
                                tempSovpad++;
                        }
                        if(tempSovpad>0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            } else if (type.equals("article")) {
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            } else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace(); }
        return arrayList;
    }
    private static ArrayList<Material> searching(String type,Librarian l,ResultSet rs,String word,boolean isTitle, boolean isAuthor, boolean isKeyword,boolean isBestseller, boolean isReference, boolean isAvailable){
        ArrayList<Material> arrayList = new ArrayList<>();
        try {
            while (rs.next()) {
                if ((isAvailable && l.getNumberOfCopiesOfBook(rs.getInt("id")) > 0) || !isAvailable) {
                    if (isTitle)
                        if (rs.getString("Name").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            }else if(type.equals("article")){
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            }else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    if (isAuthor)
                        if (rs.getString("Author").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            }else if(type.equals("article")){
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            }else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                    if (isKeyword)
                        if (rs.getString("Keywords").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0) {
                            if (type.equals("book")) {
                                if ((isBestseller && rs.getBoolean("is_bestseller") || !isBestseller) &&
                                        (!isReference && !rs.getBoolean("is_reference") || isReference))
                                    arrayList.add(l.bookByID(rs.getInt("id")));
                            }else if(type.equals("article")){
                                if (!isReference && !rs.getBoolean("is_reference") || isReference)
                                    arrayList.add(l.articleById(rs.getInt("id")));
                            }else {
                                arrayList.add(l.avById(rs.getInt("id")));
                            }
                            continue;
                        }
                }
            }
        }catch (SQLException e) {
        e.printStackTrace(); }
        return arrayList;
    }

    public static ArrayList<User> userByTitle(String title){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        if (!title.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
                while (rs.next()) {
                    if (rs.getString("Name").toLowerCase().indexOf(title.toLowerCase()) >= 0) {
                        arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this name in DB");
                else
                    LOGGER.trace("Didn't Found this name in DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching name of User");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("Name is empty");
        return arrayList;
    }
    public static ArrayList<User> userByAddress(String address){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        if (!address.trim().isEmpty()) {
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
        }else
            LOGGER.trace("address is empty");
        return arrayList;
    }
    public static ArrayList<User> userByPhoneNumber(String number){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        if (!number.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
                while (rs.next()) {
                    if (rs.getString("Phone_number").toLowerCase().indexOf(number.toLowerCase()) >= 0) {
                        arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this number in DB");
                else
                    LOGGER.trace("Didn't Found this number in DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching number of User");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("number is empty");
        return arrayList;
    }
    public static ArrayList<User> userByType(String type){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        if (!type.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
                while (rs.next()) {
                    if (rs.getString("Type").toLowerCase().indexOf(type.toLowerCase()) >= 0) {
                        arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this type in DB");
                else
                    LOGGER.trace("Didn't Found this type in DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching type of User");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("type is empty");
        return arrayList;
    }
    public static ArrayList<User> userByEmail(String email){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList = new ArrayList();
        if (!email.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
                while (rs.next()) {
                    if (rs.getString("Email").toLowerCase().indexOf(email.toLowerCase()) >= 0) {
                        arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this email in DB");
                else
                    LOGGER.trace("Didn't Found this email in DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching email of User");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("email is empty");
        return arrayList;
    }

    public static ArrayList<User> userByAllInfo(String name,String address,String number,String type,String email){
        //Вводите информацию о юзере, которую знаете, необязательно каждый пункт, некоторые пункты могут быть пустые,
        // если все пункты будут пусты, то будет возвращен пустой лист.
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<User> arrayList= new ArrayList();
        if (name.trim().isEmpty() && address.trim().isEmpty() && number.trim().isEmpty() && type.trim().isEmpty() && email.trim().isEmpty()) {//проверка на пустоту
            LOGGER.trace("Empty search query");
            return arrayList;
        }
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library");
            while (rs.next()) {

                boolean isSameName=false;
                if (!name.trim().isEmpty()) {
                    if (rs.getString("Name").trim().toLowerCase().indexOf(name.trim().toLowerCase()) >= 0)
                        isSameName = true;
                }else
                    isSameName=true;

                boolean isSameaddress=false;
                if (!address.trim().isEmpty()){
                    if (rs.getString("Address").trim().toLowerCase().indexOf(address.trim().toLowerCase()) >= 0)
                        isSameaddress=true;
                }else
                    isSameaddress=true;

                boolean isSameNumber=false;
                if (!number.trim().isEmpty()){
                    if (rs.getString("Phone_number").trim().toLowerCase().indexOf(number.trim().toLowerCase()) >= 0)
                        isSameNumber=true;
                }else
                    isSameNumber=true;

                boolean isSameType=false;
                if (!type.trim().isEmpty()){
                    if (rs.getString("Type").trim().toLowerCase().indexOf(type.trim().toLowerCase()) >= 0)
                        isSameType=true;
                }else
                    isSameType=true;

                boolean isSameEmail=false;
                if (!email.trim().isEmpty()){
                    if (rs.getString("Email").trim().toLowerCase().indexOf(email.trim().toLowerCase()) >= 0)
                        isSameEmail=true;
                }else
                    isSameEmail=true;

                if (isSameName&&isSameaddress&& isSameNumber && isSameType && isSameEmail)
                    arrayList.add(db.getInformationAboutTheUser(rs.getInt("Card_number")));

            }
            if (arrayList.size() > 0)
                LOGGER.trace("Found user in DB");
            else
                LOGGER.trace("Didn't Found this user in DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching email of User");
            e.printStackTrace();
        }
        return arrayList;
    }


    public static ArrayList<AV> avByTitle(String title){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        if (!title.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
                while (rs.next()) {
                    if (rs.getString("Name").toLowerCase().indexOf(title.toLowerCase()) >= 0) {
                        AV av = new AV(rs.getString("Name"), rs.getString("Author"), rs.getInt("Price"), rs.getString("Keywords"));
                        arrayList.add(av);
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this name in AV DB");
                else
                    LOGGER.trace("Didn't Found this name in AV DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching name of AV");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("Name is empty");
        return arrayList;
    }
    public static ArrayList<AV> avByAuthor(String author){
        //вводится строка из авторов разделенных запятыми. Метод сам распарсивает их на отдельных авторов.
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<AV> arrayList = new ArrayList();
        ArrayList<String> authorList = new ArrayList(separateIntoList(author));
        if (!author.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
                while (rs.next()) {
                    for (int i = 0; i < authorList.size(); i++) {
                        if (rs.getString("Author").toLowerCase().indexOf(authorList.get(i).toLowerCase()) >= 0) {
                            AV av = new AV(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"), rs.getInt("Price"), rs.getString("Keywords"));
                            arrayList.add(av);
                            break;//чтоб не добавлять по несколько раз один и тот же материал
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this author(authors) in AV DB");
                else
                    LOGGER.trace("Didn't Found this author(authors) in AV DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching author(authors) of AV");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("author is empty");
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
        if (!keyWords.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM av");
                while (rs.next()) {
                    for (int i = 0; i < authorList.size(); i++) {//перебираю все распарсенные кейВорды
                        if (rs.getString("Keywords").toLowerCase().indexOf(authorList.get(i).toLowerCase()) >= 0) {
                            AV av = new AV(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"), rs.getInt("Price"), rs.getString("Keywords"));
                            arrayList.add(av);
                            break;
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this keyWord(keyWords) in AV DB");
                else
                    LOGGER.trace("Didn't Found this keyWord(keyWords) in AV DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching keyWord(keyWords) of AV");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("keyWords is empty");
        return arrayList;
    }


    public static ArrayList<Article> articleByTitle(String title) {
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        if (!title.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    if (rs.getString("Name").toLowerCase().indexOf(title.toLowerCase()) >= 0) {
                        Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                        arrayList.add(article);
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this name in Article DB");
                else
                    LOGGER.trace("Didn't Found this name in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching name of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("Name is empty");
        return arrayList;
    }
    public static ArrayList<Article> articleByAuthor (String author){
        //вводится строка из авторов разделенных запятыми. Метод сам распарсивает их на отдельных авторов.
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        ArrayList<String> authorList = new ArrayList(separateIntoList(author));
        if (!author.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    for (int i = 0; i < authorList.size(); i++) {
                        if (rs.getString("Author").toLowerCase().indexOf(authorList.get(i).toLowerCase()) >= 0) {
                            Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                    rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                    rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                            arrayList.add(article);
                            break;//чтоб не добавлять по несколько раз один и тот же материал
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this author(authors) in Article DB");
                else
                    LOGGER.trace("Didn't Found this author(authors) in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching author(authors) of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("author is empty");
        return arrayList;
    }
    public static ArrayList<Article> articleByPrice(int price){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        Statement stmt = null;
        try{
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM article");
            while (rs.next()) {
                if (rs.getInt("Price")==price){
                    Article article = new Article(rs.getInt("id"), rs.getString("Name"),rs.getString("Author"),
                            rs.getInt("Price"),rs.getString("Keywords"),rs.getBoolean("is_reference"),
                            rs.getString("Journal"), rs.getString("Editor"),rs.getString("Date"));
                    arrayList.add(article);
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this price in Article DB");
            else
                LOGGER.trace("Didn't Found this price in Article DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching price of Article");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<Article> articleByKeywords(String keyWords){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        ArrayList<String> keyWordsList = new ArrayList(separateIntoList(keyWords));
        if (!keyWords.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    for (int i = 0; i < keyWordsList.size(); i++) {
                        if (rs.getString("Keywords").toLowerCase().indexOf(keyWordsList.get(i).toLowerCase()) >= 0) {
                            Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                    rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                    rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                            arrayList.add(article);
                            break;//чтоб не добавлять по несколько раз один и тот же материал
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this keyWord(keyWords) in Article DB");
                else
                    LOGGER.trace("Didn't Found this keyWord(keyWords) in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching keyWord(keyWords) of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("keyWords is empty");
        return arrayList;
    }
    public static ArrayList<Article> articleByIsReference(boolean isReference){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        Statement stmt = null;
        try{
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
            while (rs.next()) {
                if (rs.getBoolean("is_reference")==isReference){
                    Article article = new Article(rs.getInt("id"), rs.getString("Name"),rs.getString("Author"),
                            rs.getInt("Price"),rs.getString("Keywords"),rs.getBoolean("is_reference"),
                            rs.getString("Journal"), rs.getString("Editor"),rs.getString("Date"));
                    arrayList.add(article);
                }
            }
            if (arrayList.size()>0)
                LOGGER.trace("Found this isReference in Article DB");
            else
                LOGGER.trace("Didn't Found this isReference in Article DB");
        }catch (SQLException e) {
            LOGGER.error("Error in searching isReference of Article");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<Article> articleByJournal(String journal){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        if (!journal.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    if (rs.getString("Journal").toLowerCase().indexOf(journal.toLowerCase()) >= 0) {
                        Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                        arrayList.add(article);
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this Journal in Article DB");
                else
                    LOGGER.trace("Didn't Found this Journal in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching Journal of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("journal is empty");
        return arrayList;
    }
    public static ArrayList<Article> articleByEditor (String editor){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        if (!editor.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    if (rs.getString("Editor").toLowerCase().indexOf(editor.toLowerCase()) >= 0) {
                        Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                        arrayList.add(article);
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this Editor in Article DB");
                else
                    LOGGER.trace("Didn't Found this Editor in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching Editor of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("editor is empty");
        return arrayList;
    }
    public static ArrayList<Article> articleByDate(String date){
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Article> arrayList = new ArrayList();
        if (!date.trim().isEmpty()) {

            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM articles");
                while (rs.next()) {
                    if (rs.getString("Date").toLowerCase().indexOf(date.toLowerCase()) >= 0) {
                        Article article = new Article(rs.getInt("id"), rs.getString("Name"), rs.getString("Author"),
                                rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                                rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"));
                        arrayList.add(article);
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this Date in Article DB");
                else
                    LOGGER.trace("Didn't Found this Date in Article DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching Date of Article");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("Date is empty");
        return arrayList;
    }

    //maybe useless thing
//    public static ArrayList<Book> bookByPartialInfo(String word,boolean isName,boolean isAuthor, boolean isPublisher, boolean isKeyword,
//                                                    boolean isInscribedBestSeller, boolean isBestSeller,
//                                                    boolean isInscribedReference, boolean isReference,boolean isInscribedAvailable, boolean isAvailable){
//        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
//        ArrayList<Book> arrayList = new ArrayList<>();
//
//        Statement stmt = null;
//        try {
//            stmt = db.con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT books * FROM ");
//            while (rs.next()) {
//
//                if (isName)
//                    if (rs.getString("Name").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0) {
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//                if (isAuthor)
//                    if (rs.getString("Author").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0){
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//                if (isPublisher)
//                    if (rs.getString("Publisher").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0){
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//                if (isKeyword)
//                    if (rs.getString("Keywords").trim().toLowerCase().indexOf(word.trim().toLowerCase()) >= 0){
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//                if (isInscribedBestSeller)
//                    if (rs.getBoolean("is_bestseller")==isBestSeller){
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//                if (isInscribedReference)
//                    if (rs.getBoolean("is_reference")==isReference){
//                        arrayList.add(l.bookByID(rs.getInt("id")));
//                        continue;
//                    }
//            }
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//    }

    public static ArrayList<Book> bookByTitle(String title) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        if (!title.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    if (rs.getString("Name").toLowerCase().indexOf(title.toLowerCase()) >= 0) {
                        arrayList.add(l.bookByID(rs.getInt("id")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this name in Book DB");
                else
                    LOGGER.trace("Didn't Found this name in Book DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching name of Book");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("Name is empty");
        return arrayList;
    }
    public static ArrayList<Book> bookByAuthor (String author){
        //вводится строка из авторов разделенных запятыми. Метод сам распарсивает их на отдельных авторов.
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        ArrayList<String> authorList = new ArrayList(separateIntoList(author));
        if (!author.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    for (int i = 0; i < authorList.size(); i++) {
                        if (rs.getString("Author").toLowerCase().indexOf(authorList.get(i).toLowerCase()) >= 0) {
                            arrayList.add(l.bookByID(rs.getInt("id")));
                            break;//чтоб не добавлять по несколько раз один и тот же материал
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this author(authors) in Books DB");
                else
                    LOGGER.trace("Didn't Found this author(authors) in Books DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching author(authors) of Books");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("author is empty");
        return arrayList;
    }
    public static ArrayList<Book> bookByPublisher(String publisher) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        if (!publisher.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    if (rs.getString("Publisher").toLowerCase().indexOf(publisher.toLowerCase()) >= 0) {
                        arrayList.add(l.bookByID(rs.getInt("id")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this publisher in Book DB");
                else
                    LOGGER.trace("Didn't Found this publisher in Book DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching publisher of Book");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("publisher is empty");
        return arrayList;
    }
    public static ArrayList<Book> bookByEdition(String edition) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        if (!edition.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    if (rs.getString("Edition").toLowerCase().indexOf(edition.toLowerCase()) >= 0) {
                        arrayList.add(l.bookByID(rs.getInt("id")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this edition in Book DB");
                else
                    LOGGER.trace("Didn't Found this edition in Book DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching edition of Book");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("edition is empty");
        return arrayList;
    }
    public static ArrayList<Book> bookByPrice(int price) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    if (rs.getInt("Price")==price) {
                        arrayList.add(l.bookByID(rs.getInt("id")));
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this price in Book DB");
                else
                    LOGGER.trace("Didn't Found this price in Book DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching price of Book");
                e.printStackTrace();
            }
        return arrayList;
    }
    public static ArrayList<Book> bookByKeywords (String keyword){
        //вводится строка из авторов разделенных запятыми. Метод сам распарсивает их на отдельных авторов.
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        ArrayList<String> keyWordList = new ArrayList(separateIntoList(keyword));
        if (!keyword.trim().isEmpty()) {
            Statement stmt = null;
            try {
                stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
                while (rs.next()) {
                    for (int i = 0; i < keyWordList.size(); i++) {
                        if (rs.getString("Keywords").toLowerCase().indexOf(keyWordList.get(i).toLowerCase()) >= 0) {
                            arrayList.add(l.bookByID(rs.getInt("id")));
                            break;//чтоб не добавлять по несколько раз один и тот же материал
                        }
                    }
                }
                if (arrayList.size() > 0)
                    LOGGER.trace("Found this keyWord(keyWords) in Books DB");
                else
                    LOGGER.trace("Didn't Found this keyWord(keyWords) in Books DB");
            } catch (SQLException e) {
                LOGGER.error("Error in searching keyWord(keyWords) of Books");
                e.printStackTrace();
            }
        }else
            LOGGER.trace("keyWord is empty");
        return arrayList;
    }
    public static ArrayList<Book> bookByBestSeller(boolean isBestSeller) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
            while (rs.next()) {
                if (rs.getBoolean("is_bestseller")==isBestSeller) {
                    arrayList.add(l.bookByID(rs.getInt("id")));
                }
            }
            if (arrayList.size() > 0)
                LOGGER.trace("Found this isBestSeller in Book DB");
            else
                LOGGER.trace("Didn't Found this isBestSeller in Book DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching isBestSeller of Book");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<Book> bookByReference(boolean isReference) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
            while (rs.next()) {
                if (rs.getBoolean("is_reference")==isReference) {
                    arrayList.add(l.bookByID(rs.getInt("id")));
                }
            }
            if (arrayList.size() > 0)
                LOGGER.trace("Found this isReference in Book DB");
            else
                LOGGER.trace("Didn't Found this isReference in Book DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching isReference of Book");
            e.printStackTrace();
        }
        return arrayList;
    }
    public static ArrayList<Book> bookByYear(int year) {
        Librarian l = new Librarian("1", "1", "1", 0, "Librarian Priv3", "1","1");
        PropertyConfigurator.configure("log4j.properties");
        ArrayList<Book> arrayList = new ArrayList();
        Statement stmt = null;
        try {
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM books");
            while (rs.next()) {
                if (rs.getInt("Year")==year) {
                    arrayList.add(l.bookByID(rs.getInt("id")));
                }
            }
            if (arrayList.size() > 0)
                LOGGER.trace("Found this year in Book DB");
            else
                LOGGER.trace("Didn't Found this year in Book DB");
        } catch (SQLException e) {
            LOGGER.error("Error in searching year of Book");
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
