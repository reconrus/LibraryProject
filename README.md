# LibraryProject
Librinno project runs only on Java 9 and recent version of IntelliJIdea. 

When you first open GUI (through MainApp) program will ask your database information. After that it will ask you to create admin. To enter admin screen (from login screen) you should remember his login (its id will pop up after creation) and his password (that you entered while creating).

If MySQL server and MySQL workbench aren’t downloaded, download them via next link: https://dev.mysql.com/downloads/windows/installer/5.7.html

Class with launcher(with tests): LibraryProject/src/main/java/librinno/model/Main.java 
Class with launcher(with GUI): LibraryProject/src/main/java/librinno/ui/main/MainApp.java

During installation in a window “choosing setup type” choose a “Developer Default” and install all. In window “installation” execute all programs and wait for installation. Press next until “accounts and roles” and there write your MySQL password. Then press next until the “apply configuration” and here press execute. Press finish, next, finish, next, then right your username and password for root user, press check, and press next if connection succeeded, then press next; otherwise rewrite your password. Press execute. If there was warning about long installation, press ok and wait. Press next, finish. Open LibraryProject, in it open class Main (it is in src.main.java.librinno.model.Main) then setup Version of Java9. 

If you have a problem with Java SDK:

For setting up version of Java press ctrl+Alt+Shift+S. Here in SDK choose projectSDK, choose compile output folder. Then go to libraries menu and here press “+” Java and choose “lib” folder which lies in the root folder and press ok, press apply, close window and run main class for tests and MainApp for GUI version.

How to activate asserts in tests: 
Choose Run -> Edit configurations -> VM Options: "-ea" (without quotes) for Main Class


Please, follow links to download our application. Yandex disk: https://yadi.sk/d/RAVQ1B2h3TPjBk or Google Drive: https://drive.google.com/open?id=1sABNXhXzWo7h8EWzKIhBlfFxueJaRBKO

Sincerely, LibrInno team.
