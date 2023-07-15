
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/** 
 * Donor class is a subclass of User class.
 * Donor class has a private String phone data field to store the phone number of donor.  
 */
public class Donor extends User {
    private String phone;

    /** 
     * No-arg constructor for Donor class.
     * Constructs a donor with phone number set to null.
     */
    public Donor(){}

    /** 
     * Constructs a donor with the specified username and phone number.
     * Invokes the overloaded constructor of superclass.
     * @param username username of donor
     * @param phone phone number of donor
     */
    public Donor(String username, String phone) {
        super(username);
        this.phone = phone;
    }

    /** 
     * Constructs a donor with the specified username, quantity and phone number.
     * Invokes the overloaded constructor of superclass.
     * @param username username of donor
     * @param quantity quantity of aids donated by donor
     * @param phone phone number of donor
     */       
    public Donor(String username, int quantity, String phone) {
        super(username, quantity);
        this.phone = phone;
    }
    
    /** 
     * Constructs a donor with the specified username, phone number, quantity and name of aids.
     * Invokes the overloaded constructor of superclass.
     * 
     * @param username username of donor
     * @param phone phone number of donor
     * @param quantity quantity of aids donated by donor
     * @param aids name of aids donated by donor
     */
    public Donor(String username, String phone, int quantity, String aids) {
        super(username, quantity, aids);
        this.phone = phone;
    }

    /** 
     * Gets the phone number of the donor.
     * 
     * @return phone number of donor.
     */
    public String getPhone(){
        return phone;
    }

    /** 
     * Reads the list of donors from a CSV file, which contains the username and phone number of donor, into a list of lines.
     * Splits a line by comma and adds the username and phone number into a list.
     * Returns the list containing username and phone number of donor.
     *   
     * @return List<Donor> a list that contains the username and phone number of donor.
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static List<Donor> readDonorFromFile() throws IOException {
        List<Donor> donors = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("DonorList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            // items[0] is username, items[1] is phone
            donors.add(new Donor(items[0], items[1]));
        }
        return donors;
    }
    
    /** 
     * Prompts donor to enter a valid phone number that is numeric only.
     * If the data type entered by donor is wrong, the donor will be asked to enter the phone number again.
     * If the data type entered by donor is correct, the username and phone number of donor will be added to the list of donors and saved to the CSV file.
     *    
     * @param donors a list that contains the username and phone number of donor 
     * @param usernamelogin username used by donor to login
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void inputPhone (List <Donor> donors,String usernamelogin) throws IOException{
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        do {
            try{
                System.out.println("--------------------------------");
                System.out.print("Please enter your phone number: ");
                String phone = input.next();

                if(isNumeric(phone) == false)
                    throw new InputMismatchException("Please input valid phone number.\n");
                flag = false;
                donors.add(new Donor(usernamelogin, phone));
                saveDonorToFile(donors);
            }
            catch (InputMismatchException ex) {
                System.out.println("Input Error. Please try again.\n");
                input.nextLine(); 
            }
        } while (flag == true);
    }

    /** 
     * Prompts donor to enter the name and quantity of aids to be donated.
     * If the donor enters numeric for name of aids or a negative number for quantity of aids, the donor will be asked to input again.  
     * If the input entered by donor is correct, the username of donor and the name and quantity of aids will be added to the list of donation and saved to the CSV file.
     * 
     * @param donors a list that contains the username and phone number of donors
     * @param donation a list that contains the username of donor and the name and quantity of aids donated
     * @param usernamelogin username used by donor to login
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void donor(List <Donor> donors, List<User> donation, String usernamelogin) throws IOException{
        Scanner input = new Scanner(System.in);
        String donorAids;
        int donorQuantity;

        do {
             try {        
                System.out.print("Please enter the aids that you wish to donate (exit to finish): ");
                donorAids = input.next();                
                if(isNumeric(donorAids)) {
                    throw new InputMismatchException("Please input the name of aid instead of number.\n");
                }

                donorAids = donorAids.toLowerCase();

                if (donorAids.equals("exit"))
                    break;

                System.out.print("Please enter the quantity to be donated: ");
                donorQuantity = Integer.parseInt(input.next());
                IllegalInputQty(donorQuantity);
              
                donation.add(new User(usernamelogin, donorQuantity, donorAids));
             } 
             catch (InputMismatchException ex) {
                    System.out.println("Input Error. Try again. "+ ex.getMessage());
                 input.nextLine(); 
             }
             catch (IllegalArgumentException ex) {
                 System.out.println("Input Error: " + ex.getMessage());
             }
        } while (true);
  
        int type = 2;
        saveToFile(donation, type);
    }
 
    /** 
     * Reads donor list into a list of lines and writes to the CSV file.
     * 
     * @param list a list that contains the username and phone number of donors
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void saveDonorToFile(List<Donor> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            sb.append(list.get(i).toCSVString() + "\n");
        Files.write(Paths.get("DonorList.csv"), sb.toString().getBytes());
    }

    /** 
     * Returns a string representation of the username and phone number of donor.
     * @return a string representation of the username and phone number of donor.
     */
    public String toString() {
        return username + " " + phone;
    }

    /** 
     * Returns a string representation of the username and phone number of donor to be written into the CSV file.
     * @return a string representation of the username and phone number of donor separated by comma.
     */
    public String toCSVString() {
        return  username + "," + phone;
    }
}
