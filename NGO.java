import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * NGO class is a subclass of User class.
 * NGO class has a private int manpower data field to store the manpower of NGO. 
 */
public class NGO extends User implements Comparable<NGO> {

    private String manpower;
    private int headcount;

    /** 
     *  No-arg constructor for NGO class.
     *  Constructs NGO with manpower set to null.
     */
    public NGO() {}

    /** 
     * Constructs a NGO  with the specified username and manpower.
     * Invokes the overloaded constructor of superclass.
     * @param username the username of NGO
     * @param manpower the manpower of NGO
     */
    public NGO(String username, String manpower) {
        super(username);
        this.manpower = manpower;
    }

    public NGO(String username, int headcount) {
        super(username);
        this.headcount = headcount;
    }

    /** 
     * Constructs a NGO with the specified username, quantity and manpower.
     * Invokes the overloaded constructor of superclass.
     * @param username the username of NGO
     * @param quantity the quantity of aids required by NGO
     * @param manpower the manpower of NGO
     */
    public NGO(String username, int quantity, String manpower) {
        super(username, quantity);
        this.manpower = manpower;
    }

     /** 
     * Constructs a NGO with the specified username, manpower, quantity and name of aids.
     * Invokes the overloaded constructor of superclass.
     * @param username username of NGO
     * @param manpower manpower of NGO
     * @param quantity quantity of aids required by NGO
     * @param aids name of aids required by ngo
     */
    public NGO(String username, String manpower, int quantity, String aids) {
        super(username, quantity, aids);
        this.manpower = manpower;
    }

    public int getHeadcount(){
        return headcount;
    }
  

    /**
     * Gets the manpower of NGO.
     * @return  manpower of NGO
     */
    public String getManpower() {
        return manpower;
    }

    public int getManpowerInt(String manpower) {
        try {
            return Integer.parseInt(manpower);
        }

        catch (NumberFormatException e) {
            return 0;
        } 
    }

    /**
     * Reads the list of NGOs from a CSV file, which contains the username and manpower of NGO, into a list of lines.
     * Splits a line by comma and adds the username and manpower into a list.
     * Returns the list containing username and manpower of NGO.
     *   
     * @return List<NGO> a list that contains the username and manpower of NGO.
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static List<NGO> readNgoFromFile() throws IOException {
        List<NGO> ngos = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("NgoList.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            // items[0] is username, items[1] is manpower
            ngos.add(new NGO(items[0], items[1]));
        }
        return ngos;
    }

    /**
     * Reads NGO list into a list of lines and writes to the CSV file.
     * @param list a list that contains the username and manpower of NGO
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void saveNgoToFile(List<NGO> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            sb.append(list.get(i).toCSVString() + "\n");
        Files.write(Paths.get("NgoList.csv"), sb.toString().getBytes());
    }

    /**
     * Prompts NGO to enter manpower
     * If the data type entered by NGO is wrong or negative, the NGO will be asked to enter the manpower again.
     * If the data type entered by NGO is correct, the username and manpower of NGO will be added to the list of NGOs and saved to the CSV file.
     *    
     * @param ngos a list that contains the username and manpower of NGO
     * @param usernamelogin username used by NGO to login
     * @throws IOException checked exception related to the input and output operations in Java codes.
     */
    public static void InputManPowers(List <NGO> ngos,String usernamelogin) throws IOException {
        
        Scanner input = new Scanner(System.in);
        String manpower;
        do {
            try {
            System.out.println("----------------------------");
            System.out.print("Please enter your manpower: ");
            manpower = input.next();
            IllegalArgumentException(manpower);
            ngos.add(new NGO(usernamelogin, manpower));
            saveNgoToFile(ngos);
            break;
            } 

         catch (InputMismatchException ex) {
            System.out.println("Input Error." + ex.getMessage());
            input.nextLine(); 
         }
         catch (IllegalArgumentException ex) {
            System.out.println("Input Error: " + ex.getMessage());
         }
        } while (true);
    }  

    /**
     * Prompts NGO to enter the name and quantity of aids required.
     * If the NGO enters numeric for name of aids or a negative number for quantity of aids, the NGO will be asked to input again.  
     * If the input entered by NGO is correct, the username of NGO and the name and quantity of aids will be added to the list of Receiver and saved to the CSV file.
     * 
     * @param ngos a list that contains the username and manpower of NGOs
     * @param receiver a list that contains the name of NGO, name and quantity of aids received
     * @param usernamelogin username used by NGO to login 
     * @throws IOException checked exception related to the input and output operations in Java codes
     */
    public static void ngo(List <NGO> ngos, List<User> receiver, String usernamelogin) throws IOException{
        Scanner input = new Scanner(System.in);
        String receiverAids;
        int receiverQuantity;
        do {
             try {
                System.out.print("Please enter the aids that you wish to receive (exit to finish): ");
                receiverAids = input.next();
                if(isNumeric(receiverAids)){
                    throw new InputMismatchException("Please input the name of aid not number.\n");
                }
                receiverAids = receiverAids.toLowerCase();
            
                if (receiverAids.equals("exit"))
                break;

                System.out.print("Please enter the quantity to be received: ");
                receiverQuantity =Integer.parseInt(input.next());
                IllegalInputQty(receiverQuantity);
                receiver.add(new User(usernamelogin, receiverQuantity, receiverAids));
             } 

             catch (InputMismatchException ex) {
                 System.out.println("Input Error." + ex.getMessage());
                 input.nextLine(); 
             }
             catch (IllegalArgumentException ex) {
                 System.out.println("Input Error: " + ex.getMessage());
             }
        } while (true);
    
        int type = 3;
        saveToFile(receiver, type);
    }

    /**
     * Returns a string representation of the username and manpower of NGO.
     * @return a string representation of the username and manpower of NGO.
     */
    @Override
    // public String toString() {
    //     return username + " " + manpower;
    // }

    // public String toString() {
    //     return username + " " + headcount;
    // }

    public String toString() {
        return username;
    }

    /**
     * Returns a string representation of the username and manpower of NGO to be written into the CSV file.
     * @return a string representation of the username and manpower of NGO separated by comma.
     */
    public String toCSVString() {
        return username + "," + manpower;
    }

    public int compareTo (NGO o) {
        return headcount - o.headcount;
    }


}

class testNGO {
    public static void main(String[] args) {
        //NGO n1 = new NGO("Jacob", 100);
       // System.out.println(n1);
        NGO n2 = new NGO("Mich", 200);
        System.out.println(n2);
        int number = n2.getManpowerInt("100");
        System.out.println(number);
    }
}