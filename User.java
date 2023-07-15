import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * User class is a superclass that implements the Comparable interface to compare objects of User class.
 * User class has 5 protected data fields.
 * The protected String username data field stores the username of user.
 * The protected String password data field stores the password of user.
 * The protected String role data field stores the role of user.
 * The protected String aids data field stores the name of aids donated/ received by user.
 * The protected int quantity data field stores the quantity of aids donated/ received by user.
 */
public class User {

    protected String username;
    protected String password;
    protected String role;
    protected String aids;
    protected int quantity;

    /**
     * No-arg constructor for User class.
     * Constructs a user.
     */
    public User() {
    }

    /**
     * Constructs a User object with the specified role, username and password.
     * 
     * @param role role of user
     * @param username username of user
     * @param password password of user
     */
    public User(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs a User object with the specified username.
     * 
     * @param username username of user
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Constructs a User object with the specified username and quantity.
     * 
     * @param username username of user
     * @param quantity quantity of aids donated/ received by user
     */
    public User(String username, int quantity) {
        this.username = username;
        this.quantity = quantity;
    }

    /**
     * Constructs a User object with the specified username, quantity and aids.
     * 
     * @param username username of user
     * @param quantity quantity of aids donated/ received by user
     * @param aids name of aids donated/ received by user
     */
    public User(String username, int quantity, String aids) {
        this.username = username;
        this.quantity = quantity;
        this.aids = aids;
    }

    /**
     * Constructs a User object with the specified role, username, password, aids and quantity.
     * 
     * @param role role of user.
     * @param username username of user.
     * @param password password of user.
     * @param aids name of aids donated/ received by user.
     * @param quantity quantity of aids donated/ received by user.
     */
    public User(String role, String username, String password, String aids, int quantity) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.aids = aids;
        this.quantity = quantity;
    }

    /**
     * Sets the username of user.
     * 
     * @param username username entered by user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username of user
     * 
     * @return username of user
     */
    public String getUserName() {
        return username;
    }

    /**
     * Gets the password of user
     * 
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the name of aids donated/ received by user.
     * 
     * @return name of aids donated/ received by user
     */
    public String getAids() {
        return aids;
    }

    /**
     * Gets the quantity of aids donated/ received by user.
     * 
     * @return quantity of aids donated/ received by user
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the role of user.
     * 
     * @return role of user
     */
    public String getRole() {
        return role;
    }

    /**
     * Reads the list of users from a User.csv file, which contains the role, username and password of users
     * into a list of lines.
     * Splits a line by comma and adds the role, username and password into a list.
     * Returns the list containing the role, username and password of users.
     * 
     * @return List<User> a list that contains the role, username and password of users.
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static List<User> readUserFromFile() throws IOException {
        List<User> users = new ArrayList<User>();

        List<String> lines = Files.readAllLines(Paths.get("User.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            // items[0] is role, items[1] is username, items[2] is password
            users.add(new User(items[0], items[1], items[2]));
        }
        return users;
    }

    /**
     * Reads the list of donation from Donation.csv file, which contains the username, quantity and aids donated 
     * into a list of lines.
     * Splits a line by commma and adds the username, quantity and aid.
     * Returns the list containing the username, quantity and aid.
     * 
     * @return List<User> a list that contains the username, quantity and aid of
     *         donation.
     * @throws IOException checked exception related to the input and output
     *                     operations in Java codes.
     */
    public static List<User> readDonationFromFile() throws IOException {
        List<User> donation = new ArrayList<User>();

        List<String> lines = Files.readAllLines(Paths.get("Donation.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            // items[0] is username, items[1] is quantity, items[2] is aid
            int quantity = Integer.parseInt(items[1]);
            donation.add(new User(items[0], quantity, items[2]));
        }
        return donation;
    }

    /**
     * Reads the list of receiver from Receiver.csv file, which contains the username, quantity and aid received
     * into a list of lines.
     * Splits a line by commma and adds the username, quantity and aid.
     * Returns the list containing the username, quantity and aid.
     * 
     * @return List<User> a list that contains the username, quantity and aid of receiver.
     * @throws IOException checked exception related to the input and output
     *                     operations in Java codes.
     */
    public static List<User> readReceiverFromFile() throws IOException {
        List<User> receiver = new ArrayList<User>();

        List<String> lines = Files.readAllLines(Paths.get("Receiver.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            // items[0] is username, items[1] is quantity, items[2] is aid
            int quantity = Integer.parseInt(items[1]);
            receiver.add(new User(items[0], quantity, items[2]));
        }
        return receiver;
    }

    /**
     * Reads the specified list into a list of lines and writes to the respective CSV file depending on the type.
     * If the type is 1, the list is saved to User.csv file.
     * If the type is 2, the list is saved to Donation.csv file.
     * If the type is 3, the list is saved to Receiver.csv file.
     * 
     * @param list a list of User type
     * @param type type of files
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void saveToFile(List<User> list, int type) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (type == 1) {
            for (int i = 0; i < list.size(); i++)
                sb.append(list.get(i).toCSVString() + "\n");
            Files.write(Paths.get("User.csv"), sb.toString().getBytes());
        } else if (type == 2) {
            for (int i = 0; i < list.size(); i++)
                sb.append(list.get(i).toCSVString1() + "\n");
            Files.write(Paths.get("Donation.csv"), sb.toString().getBytes());
        } else if (type == 3) {
            for (int i = 0; i < list.size(); i++)
                sb.append(list.get(i).toCSVString1() + "\n");
            Files.write(Paths.get("Receiver.csv"), sb.toString().getBytes());
        }
    }

    /**
     * Return a string representation of the role, username and password of user.
     * 
     * @return a string representation of the role, username and password of user.
     */
    public String toString() {
        return role + " " + username + " " + password;
    }

    /**
     * Returns a string representation of the role, username and password of user to be written into the CSV file.
     * 
     * @return a string representation of the role, username and password of user separated by comma.
     */
    public String toCSVString() {
        return role + "," + username + "," + password;
    }

    /**
     * Returns a string representation of the username, quantity and aids donated/ received by the user to be
     * written into the CSV file.
     * 
     * @return a string representation of the username, quantity and aids donated/ received by the user 
     * separated by comma.
     */
    public String toCSVString1() {
        return username + "," + quantity + "," + aids;
    }

    /**
     * Detects if the user input is a positive number.
     * If user inputs a negative value, the function will throw an IllegalArgumentException.
     * If an exception occurs, a system generated error message is shown to the user. 
     * 
     * @param number a number entered by the user
     * 
     */
    public static void IllegalArgumentException(String input) {
        int number = Integer.parseInt(input);
        if (number < 0)
            throw new IllegalArgumentException("The number must be positive.\n");
    }
    
    
    public static void IllegalInputQty(int number) {
        if (number < 0)
            throw new IllegalArgumentException("The number must be positive.\n");
    }

    /**
     * Checks if the argument is numeric. Leading and trailing whitespace characters in strNum are ignored. 
     * The method will return false if strNum is null.
     * 
     * @param strNum the string to be checked.
     * @return boolean true if the argument is numeric and can be parsed to an integer.
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}


