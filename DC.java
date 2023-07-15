import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.login.FailedLoginException;

/**
 * DC has Donor class and NGO class.
 * DC has a static final integer which represents the minimum length of password.
 */
public class DC {
    public static final int MinPASSWORD_LENGTH = 5;

    // change visiblity for queue java file to use them
    Donor donor;   
    NGO Ngo;
    private String status;

    /** 
     * Constructs a DC with the specified Donor and NGO.
     * @param donor a Donor class object
     * @param Ngo a NGO class object
     */
    public DC(Donor donor, NGO Ngo,String status) {
        this.donor = donor;
        this.Ngo = Ngo;
        this.status = status;
    }

    /**
     * Gets the status of the aid in DC.
     * @return status of the aid in DC
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the string representation of donor name, phone, aid, quantity, ngo name and manpower
     * to be written in csv file
     * @return the string representation of donor name, phone, aid, quantity, ngo name and manpower seperated by coma
     */
    public String toCSVString() {

        return donor.getUserName() + "," + donor.getPhone() + "," + donor.getAids() + ","
                + donor.getQuantity()
                + "," + Ngo.getUserName() + "," + Ngo.getManpower() +"," + status ;
    }

    /** 
     * Detects if the user enters a valid choice that is within the range.
     * @param choice a number entered by user
     */
    public static void ThreeChoiceInput (int choice){
        if(choice < 1 || choice > 3)
            throw new IllegalArgumentException();
    }

    /** 
     * Detects if the user enters a valid choice that is within the range.
     * @param choice a number entered by user
     */
    public static void FourChoiceInput (int choice){
        if(choice < 1 || choice > 4)
            throw new IllegalArgumentException();
    }
    
    /** 
     * Displays the main menu and prompts user to choose a role (donor, NGO or DC), then displays the respective menu accordingly  
     * 
     * @param users a list of information that contains the role, username and password of user.  
     * @param donors a list that contains the username and phone number of donor.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void topmenu(List<User> users, List<Donor> donors, List<User> donation, List<User> receiver,
        List<NGO> ngos) throws IOException {        
        int choice = 0;        
        Scanner input = new Scanner(System.in);
        do 
        {
            System.out.println("------------------------------------");
            System.out.println(" Welcome to Aid Distribution System ");
            System.out.println("------------------------------------");
            System.out.println("1.Donor");
            System.out.println("2.NGO");
            System.out.println("3.DC");
            System.out.println("4.Collection Simulation");
            System.out.println("------------------------------------");
            System.out.print("Please select: ");

            try {
                    choice = Integer.parseInt(input.nextLine());
                    FourChoiceInput (choice);
                }
            catch (IllegalArgumentException e) {       
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }

            switch (choice) {
                case 1:
                    RegisterAndLoginamenu(users, donors, ngos, donation, receiver, "Donor");
                    break;
                case 2:
                    RegisterAndLoginamenu(users, donors, ngos, donation, receiver, "NGO");
                    break;
                case 3:
                    DC.DCview(donation,receiver,donors, ngos);
                    break;
                case 4: 
                    QueueMenu(donation, receiver, donors, ngos);
                    break;
            }
        } while(choice < 1 || choice > 4);  
    }

    /**
     * Displays queue menu used in Collection Simulation to read the user's selection.
     *
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received. 
     * @param donors a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void QueueMenu(List<User> donation, List<User> receiver, List<Donor> donors, List<NGO> ngos)throws IOException{

        int choice = 0;        
        Scanner input = new Scanner(System.in);

        do{
            System.out.println();
            System.out.println("------------------------------------");
            System.out.println("1.First in First Out Queue");
            System.out.println("2.Priority Queue");
            System.out.println("3.Quit");
            System.out.println("------------------------------------");
            System.out.print("Please select: ");
            
            try {
                choice = Integer.parseInt(input.nextLine());
                ThreeChoiceInput (choice);
                System.out.println();
            }
            catch (IllegalArgumentException e) {       
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            switch (choice) {
                    case 1 :FIFOQueuemenu(donation, receiver, donors, ngos);
                            break;
                    case 2 :PriorityQueueMenu(ngos,donation,receiver,donors);
                            break;
                    case 3: System.exit(-1);
                            break;
                }
        } while(choice < 1 || choice > 3); 
    }

    /**
     * Saves the record whereby aid is not matched to the specified ngo.
     *
     * @param j  a serial number of a record list.
     * @param donorname name of donor.
     * @param aid items donated.
     * @param phone donor's phone number.
     * @param remain the quantity of aids donated to the DC. 
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param record a list that contains the matched record list
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void saveunmatched (int j ,String donorname , String aid, String phone, int remain, List<User> receiver,List<DC> record ){
        if(j == receiver.size() - 1)
            record.add(
                    new DC(new Donor(donorname, phone, remain, aid),new NGO("-","-"),"Available"));
    }

    /**
     * Checks if the status of record is updated to "Collected".
     *
     * @param donorname name of donor.
     * @param phone donor's phone number.
     * @param aid items donated.
     * @param donated the quantity of aids donated to the ngo.
     * @param ngoname the name of ngo.
     * @param manpower the manpower of ngo.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static boolean checkUpdatedRecord(String donorname,String phone, String aid, int donated, String ngoname,String manpower)throws IOException{
    
        List<DC> record = readRecordFromFile();
        for(int i = 0; i < record.size(); i++){
            if (record.get(i).donor.getUserName().equals(donorname) && record.get(i).Ngo.getUserName().equals(ngoname) &&
            record.get(i).donor.getAids().equals(aid) && record.get(i).donor.getQuantity() == donated && record.get(i).Ngo.getManpower().equals(manpower)
            && record.get(i).getStatus().equals("Collected"))
            return true;
        }
        return false;
    }

    /**
     * Saves the matched record data to the record list.
     *
     * @param donorname name of donor.
     * @param userphone donor's phone number.
     * @param aids items donated.
     * @param donated the quantity of aids donated to the ngo.
     * @param ngoname the name of ngo.
     * @param manpower the manpower of ngo.
     * @param record a list that contains the matched record list
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void saveRecord (String donorname,String userphone, String aids, int donated, String ngoname,String manpower,List<DC> record)
    throws IOException{

        if (checkUpdatedRecord(donorname, userphone, aids, donated, ngoname, manpower))
        record.add(
            new DC(new Donor(donorname, userphone, donated, aids), new NGO(ngoname, manpower),"Collected"));
    else
        record.add(
                new DC(new Donor(donorname, userphone, donated, aids), new NGO(ngoname, manpower),"Reserved"));
    }
  
    /**
     * Saves data of the list to the "record.csv" file.
     *
     * @param list a list that contains the matched record list
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void saveRecordToFile(List<DC> list) throws IOException {
        
        StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++)
                sb.append(list.get(i).toCSVString() + "\n");
            Files.write(Paths.get("record.csv"), sb.toString().getBytes());
    }
    
    /**
     * Gets matched record list from record csv file.
     * @return a matched record list.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static List<DC> readRecordFromFile() throws IOException {
        List<DC> record = new ArrayList<DC>();

        List<String> lines = Files.readAllLines(Paths.get("record.csv"));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split(",");
            int quantity = Integer.parseInt(items[3]);
            record.add(new DC(new Donor (items[0],items[1],quantity, items[2]),new NGO(items[4],items[5]),items[6]));
        }
        return record;
    }

    /** 
     *  Displays the menu of priority queue which compares the manpower of ngo then read user's selection.
     *
     * @param ngos a list that contains the username and manpower of NGOs.    
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param donors a list that contains the username and phone number of donor.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void PriorityQueueMenu(List<NGO> ngos,List<User> donation, List<User> receiver, List<Donor> donors)throws IOException {
            
        PriorityQueue PQ = new PriorityQueue();
        Scanner input = new Scanner(System.in);
        int command = 0;
        String ngoChoice;
        
        System.out.println();
        System.out.println("DC RECORDS");
        DC.DCview(donation, receiver, donors, ngos);

        do {
            boolean flag = false;
            System.out.println();
            System.out.println("Priority queue: " + PQ);
            System.out.println("Option");
            System.out.println("1 - Enqueue an NGO");
            System.out.println("2 - Dequeue an NGO");
            System.out.println("0 - Exit");
            System.out.print("Command > ");
            
            try {
                
                command = Integer.parseInt(input.next());
                if (command < 0 || command > 2) 
                    throw new IllegalArgumentException();
            }

            catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("Invalid input. Please try again.\n");
                PriorityQueueMenu(ngos,donation,receiver,donors);
            }

            switch (command) {
                case 1: ngoChoice = input.next();
                        for (int i = 0; i < ngos.size(); i++) {
                            if (ngos.get(i).getUserName().equals(ngoChoice)) {
                                String manpower = DC.getManPowerfromlist(ngoChoice, ngos);  
                                int headcount = Integer.parseInt(manpower);
                                PQ.enqueue(new NGO(ngoChoice, headcount));
                                flag = true;
                            }
                        }
                        if (flag == false)
                            System.out.println("This NGO does not exist.");
                        break;
                    
                case 2: PQ.dequeue(donation, receiver, donors, ngos);
                        break;

                case 0: System.exit(-1);
                        break;
            }
        } while (command != 0);
    }

    /** 
     * Displays the menu of first in first out (FIFO) queue then read user's selection.
     * 
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param donors a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @throws IOException checked exception related to Input and Output operations in the Java code. 
     */
    public static void FIFOQueuemenu(List<User> donation, List<User> receiver, List<Donor> donors, List<NGO> ngos) throws IOException{
        Queue queue = new Queue();
        Scanner input = new Scanner(System.in);
        int command =0;
        String ngoChoice;
        
        System.out.println();
        System.out.println("DC RECORDS");
        DC.DCview(donation, receiver, donors, ngos);

        try {
            do {
                boolean flag = false;
                System.out.println();
                System.out.println("FIFO queue: " + queue);
                System.out.println("Option");
                System.out.println("1 - Enqueue an NGO");
                System.out.println("2 - Dequeue an NGO");
                System.out.println("0 - Exit");
                System.out.print("Command > ");
                try {
                
                    command = Integer.parseInt(input.next());
                    if (command < 0 || command > 2) 
                        throw new IllegalArgumentException();
                }
    
                catch (IllegalArgumentException | InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.\n");
                    FIFOQueuemenu(donation,receiver,donors,ngos);
                }

                switch (command) {
                    case 1:
                            ngoChoice = input.next();
                            for (int i = 0; i < ngos.size(); i++) {
                                if (ngos.get(i).getUserName().equals(ngoChoice)) {
                                    queue.enqueue(ngoChoice);
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag == false)
                                System.out.println("This NGO does not exist.");
                            break;

                    case 2:
                            queue.dequeue(donation, receiver, donors, ngos);
                        
                            break;
                    case 0: System.exit(-1);
                            break;
                }
            } while (command != 0);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: The queue is already empty.");
        }
    }
    
    /** 
     * Matches the relationship between donor and NGO by delaring two loops and stores the result of matching in a list.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param ngos a list that contains the username and manpower of NGOs.
     * 
     * @return a matched list that contains the name and phone number of donor, quantity of aids donated, name of aid, the name and manpower of NGO
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static List<DC> matching(List<Donor> donor, List<User> donation, List<User> receiver, List<NGO> ngos)
            throws IOException {
        List<DC> record = new ArrayList<DC>();
        for (int i = 0; i < donation.size(); i++) {
            for (int j = 0; j < receiver.size(); j++) {

                if (donation.get(i).getAids().equals(receiver.get(j).getAids())) {
                    String aids = donation.get(i).getAids();
                    String donorname = donation.get(i).getUserName();
                    String userphone = getPhonefromlist(donorname, donor);
                    String ngoname = receiver.get(j).getUserName();
                    String manpower = getManPowerfromlist(ngoname, ngos);
                    int donated = 0;

                    if (receiver.get(j).getQuantity() == 0)
                        continue;

                    if (donation.get(i).getQuantity() > (receiver.get(j).getQuantity())) {
                        donated = receiver.get(j).getQuantity();
                        int remain = donation.get(i).getQuantity() - receiver.get(j).getQuantity();
                        donation.set(i, new User(donorname, remain, aids));
                        receiver.set(j, new User(ngoname, 0, aids));

                        saveRecord(donorname, userphone, aids, donated, ngoname, manpower,record);

                        saveunmatched (j, donorname, aids, userphone,  remain, receiver, record );

                    } else if (donation.get(i).getQuantity() < (receiver.get(j).getQuantity())) {
                        donated = donation.get(i).getQuantity();
                        int remain = receiver.get(j).getQuantity() - donation.get(i).getQuantity();
                        donation.set(i, new User(donorname, 0, aids));
                        receiver.set(j, new User(ngoname, remain, aids));

                        saveRecord(donorname, userphone, aids, donated, ngoname, manpower,record);
                        break;

                    } else {
                        donated = donation.get(i).getQuantity();
                        donation.set(i, new User(donorname, 0, aids));
                        receiver.set(j, new User(ngoname, 0, aids));

                        saveRecord(donorname, userphone, aids, donated, ngoname, manpower,record);
                        break;            
                    }
                }
                else
                {
                        String aids = donation.get(i).getAids();
                        String donorname = donation.get(i).getUserName();
                        String userphone = getPhonefromlist(donorname, donor);
                        int donated = donation.get(i).getQuantity();
                        saveunmatched (j, donorname, aids, userphone, donated, receiver, record );
                }
            }
        }
        DC.saveRecordToFile(record);
        retorelist(donation,receiver);
        return record;
    }

    /** 
     * Restores the lists of donation and receiver by declaring two temporary variable list<User> restoredonation and restorereceiver to get data from donation and receiver file.
     * <p>
     * Sets donation list's data to be the same as restoredonation list and receiver list's data to be the same as restorereceiver list.
     * 
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received. 
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void retorelist(List<User> donation, List<User> receiver) throws IOException{

        List<User> restoredonation, restorereceiver = new ArrayList<User>();
        restoredonation = User.readDonationFromFile();
        restorereceiver = User.readReceiverFromFile();
        for(int i = 0; i < donation.size(); i++){
            donation.set(i,restoredonation.get(i));
        }
        for(int i = 0; i < receiver.size(); i++){
            receiver.set(i,restorereceiver.get(i));
        }
    }
    
    /** 
     * Gets the phone number of donor whose name is the same as donorname from the donor list.
     * The loop is declared to match the donorname and username in donor list.
     * @param donorname donor's name which is used to login.
     * @param donor a list that contains the username and phone number of donor.
     * @return phone number of donor.
     */
    public static String getPhonefromlist(String donorname, List<Donor> donor) {
        String phone = null;
        for (int i = 0; i < donor.size(); i++) {
            if (donor.get(i).getUserName().equals(donorname)) {
                phone = donor.get(i).getPhone();
                break;
            }
        }
        return phone;
    }

    /** 
     * Gets the manpower of NGO whose name is the same as ngoname from the ngos list.
     * The loop is declared to match the ngoname and username in ngos list.
     * @param ngoname NGO's name which is used to login
     * @param ngos a list that contains the username and manpower of NGOs
     * @return manpower of NGO.
     */
    public static String getManPowerfromlist(String ngoname, List<NGO> ngos) {
        String manpower = null;
        for (int i = 0; i < ngos.size(); i++) {
            if (ngos.get(i).getUserName().equals(ngoname)) {
                manpower = ngos.get(i).getManpower();
                break;
            }
        }
        return manpower;
    }

    /** 
     * Prompts user to register, login or return to the previous menu.
     * If the user enters an invalid input, the input will passed to ThreeChoiceInput  to detect if there is a need to throw IllegalArgumentException.
     * The method also handles the InputMismatchException.
     * 
     * @param users a list of information that contains the role, username and password of user. 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param role role of user (donor or NGO)
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void RegisterAndLoginamenu(List<User> users, List<Donor> donor, List<NGO> ngos, List<User> donation,
    List<User> receiver, String role) throws IOException {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do 
        {
            System.out.println("------------------------------------");  
            System.out.println("\nWhat would you like to do?");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Back");
            System.out.println("------------------------------------");  
            System.out.print("Please select: ");

            try {
                choice = Integer.parseInt(input.nextLine());
                ThreeChoiceInput (choice);
            }
            catch (IllegalArgumentException e) {       
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            catch(InputMismatchException e){
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
                switch (choice) {
                    case 1: registeraccount(users, donor, ngos, donation, receiver, role);
                            break;
                    case 2: loginaccount(users, donor, ngos, donation, receiver, role);
                            break;
                    case 3: topmenu(users, donor, donation, receiver, ngos);
                            break;
                }
        } while(choice !=3);
    }

    /** 
     * Allows the user to register an account by entering a username and password. 
     * If the username entered by user already exists, the system will throw IllegalArgumentException and ask user to input again. 
     * If the password entered by user does not include at least one character and one numeric, user will be prompted to input again.  
     * When account registration is successful, the user's role, username and password will be recorded.
     * <p>
     * If user is NGO, he/she will be asked to input manpower.
     * If user is donor, he/she will be asked to input phone number.
     * 
     * @param users a list of information that contains the role, username and password of user. 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param role role of user (donor or NGO).
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void registeraccount(List<User> users, List<Donor> donor, List<NGO> ngos, List<User> donation,
            List<User> receiver, String role) throws IOException {
        boolean exist = true;
        int type = 1;
        
        while (exist == true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter your username (without any spacing): ");
                String userRegisterInput = input.next();
                userRegisterInput = userRegisterInput.replaceAll("\\s.*", "");

                for (int i = 0; i < users.size(); i++) {
                    if (role.equals(users.get(i).getRole()) &&
                            userRegisterInput.equals(users.get(i).getUserName()))
                        throw new IllegalArgumentException("Username is already taken.");
                    }
                    System.out.print("Please enter your password that has a minumum of 5 characters containing at least one letter and one digit: ");
                    String passRegisterInput = input.next();
                    if (is_Valid_Password(passRegisterInput)) {
                        users.add(new User(role, userRegisterInput, passRegisterInput));
                        User.saveToFile(users, type);

                        if (role.equals("Donor"))
                            Donor.inputPhone(donor,userRegisterInput);
                        else
                            NGO.InputManPowers(ngos,userRegisterInput);
                        System.out.println("Registration is completed.");
                        System.out.println("The registration name is "+ userRegisterInput + ". Please login with your new account.\n");
                        loginaccount(users, donor, ngos, donation, receiver, role);
                        exist = false;
                        }
                    else
                        System.out.println("No valid password. Please try again.\n");                                                       
            } 
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a new username or login with your account.\n");
            }
        }
    }

    /** 
     * Checks the validity of password entered by user.
     * If the length of passRegisterInput is less than MinPASSWORD_LENGTH(5), the method will return false. 
     * If the password contains at least one character and one digit, the method will return true.
     * 
     * @param passRegisterInput a string that represents password which is entered by user.
     * @return boolean password validity is true or false.
     */
    public static boolean is_Valid_Password(String passRegisterInput) {
        if (passRegisterInput.length() < MinPASSWORD_LENGTH)
            return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < passRegisterInput.length(); i++) {

            char ch = passRegisterInput.charAt(i);

            if (Password_Numeric(ch))
                numCount++;
            else if (Password_Letter(ch))
                charCount++;
            else
                return false;
        }

        return (charCount >= 1 && numCount >= 1);
    }

    /**
     * Checks if the character is letter.
     * If it is a letter, the method will return true.
     * @param ch a character from string password
     * @return boolean that indicates if the character is a letter 
     */
    public static boolean Password_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    /** 
     * Checks if the character is a digit.
     * If it is a digit, the method will return true.
     * @param ch a character from string password
     * @return boolean that indicates if the character is a digit
     */
    public static boolean Password_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    /** 
     * Allows user to login with existing account by comparing the username and password one by one with those in the list of users in a loop.
     * If login is successful, the relevant menu based on user's role will be displayed.
     * If login is not successful, the program will throw FailedLoginException to handle the error.
     *  
     * @param users a list of information that contains the role, username and password of user. 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param role role of user (donor or NGO)
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void loginaccount(List<User> users, List<Donor> donor, List<NGO> ngos, List<User> donation,
            List<User> receiver, String role) throws IOException {

        boolean found = false;
        while (found == false) {
            try {
                System.out.println("------------------------------------");  
                Scanner input = new Scanner(System.in);
                System.out.print("\nPlease enter your username: ");
                String usernamelogin = input.next();

                System.out.print("\nPlease enter your password: ");
                String passwordlogin = input.next();

                for (int i = 0; i < users.size(); i++) {
                    if (role.equals(users.get(i).getRole())
                            && usernamelogin.equals(users.get(i).getUserName())
                            && passwordlogin.equals(users.get(i).getPassword())) {
                        System.out.println("Successful login\n");
                        found = true;
                        if (role.equals("NGO"))
                            ngomenu(donor, ngos, donation, receiver, usernamelogin);
                        else
                            donormenu(donor, ngos, donation, receiver, usernamelogin);
                    }
                }
                if (found == false)
                    throw new FailedLoginException("Login failed. Invalid username or password");
            }

            catch (FailedLoginException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    /** 
     * Displays the menu for NGO and prompts NGO to make a choice. The NGO can request for aids, view the list requested or quit the program.
     * If NGO inputs an invalid choice, the InputMismatchException or IllegalArgumentException will be thrown to handle the error and NGO will be asked to input again.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param usernamelogin username used by user to login.
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void ngomenu(List<Donor> donor, List<NGO> ngos, List<User> donation, List<User> receiver,
    String usernamelogin) throws IOException {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do 
        {  
            System.out.println("------------------------------------");      
            System.out.println("1.Request now");
            System.out.println("2.View the list requested");
            System.out.println("3.Quit");
            System.out.println("------------------------------------");    
            System.out.print("Please select: ");

            try {
                choice = Integer.parseInt(input.nextLine());
                ThreeChoiceInput (choice);
            }
            catch (IllegalArgumentException e) {       
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            switch (choice) {
                case 1:
                    NGO.ngo(ngos, receiver, usernamelogin);
                    break;
                case 2:
                    DC.NGOview(donor, ngos, donation, receiver, usernamelogin);
                    break;
                case 3:
                    System.exit(-1);
            }
        } while(choice !=3);
    }

    /**
     * Displays the menu for donor and prompts donor to make a choice. The donor can donate aids, view the list donated or quit the program.
     * If donor inputs an invalid choice, the InputMismatchException or IllegalArgumentException will be thrown to handle the error and donor will be asked to input again.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param usernamelogin username used by user to login.
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void donormenu(List<Donor> donor, List<NGO> ngos, List<User> donation, List<User> receiver,
    String usernamelogin) throws IOException {
        Scanner input = new Scanner(System.in);
        int choice=0;
        do 
        {  
            System.out.println("------------------------");      
            System.out.println("1.Donate now");
            System.out.println("2.View the list donated");
            System.out.println("3.Quit");            
            System.out.println("------------------------");
            System.out.print("Please select: ");

            try {
                choice = Integer.parseInt(input.nextLine());
                ThreeChoiceInput (choice);
            }
            catch (IllegalArgumentException e) {       
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            catch(InputMismatchException e){
                    System.out.println("Invalid choice. Please press enter to select again.\n");
                    input.nextLine();
            }
            switch (choice) {
                case 1:
                    Donor.donor(donor, donation, usernamelogin);
                    break;
                case 2:
                    DC.Donorview(donor, ngos, donation, receiver, usernamelogin);
                    break;
                case 3:
                    System.exit(-1);
            }
        } while(choice !=3);
    }

    /** 
     * Allows donor to view all aids donated and the NGOs receiving the aids in tabular form.
     * List<DC> record is declared and returns list from matching method.
     * The system will print the table and get details of the donation from record list which can be matched to the username of user.
     * The table will show the name of NGO that receied aid from donor and the name of aid.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param usernamelogin username used by user to login.
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void Donorview(List<Donor> donors, List<NGO> ngos, List<User> donation, List<User> receiver,
            String usernamelogin) throws IOException {

        List<DC> record = DC.matching(donors, donation, receiver, ngos);

        System.out.println();
        System.out.printf("%10s%12s%15s%12s%11s%12s%12s%n", "Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower","Status");
        System.out.println("----------  ----------  -------------  ----------  ---------  ---------- -----------");


        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).donor.getUserName().equals(usernamelogin))
            {
                System.out.printf("%10s%12s%15s%12d%11s%12s%12s%n",
                record.get(i).donor.getUserName(), record.get(i).donor.getPhone(),
                record.get(i).donor.getAids(), record.get(i).donor.getQuantity(),
                record.get(i).Ngo.getUserName(), record.get(i).Ngo.getManpower(), record.get(i).getStatus());
            }
        }
        System.out.println();
        System.out.println();
    }

    /** 
     * Allows the NGO to view all aids received and the donor of the aids in tabular form.
     * List<DC> record is declared and returns list from matching method.
     * The system will print the table and get details of the donation from record list which can be matched to the username of user.
     * The table will show the name of donor that donates aid to NGO and the name of aid.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param usernamelogin username used by user to login.
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void NGOview(List<Donor> donors, List<NGO> ngos, List<User> donation, List<User> receiver,
            String usernamelogin) throws IOException {

        List<DC> record = DC.matching(donors, donation, receiver, ngos);

        System.out.println();
        System.out.printf("%10s%12s%15s%12s%11s%12s%12s%n", "Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower","Status");
        System.out.println("----------  ----------  -------------  ----------  ---------  ---------- -----------");


        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).Ngo.getUserName().equals(usernamelogin))
                {
                    System.out.printf("%10s%12s%15s%12d%11s%12s%12s%n",
                    record.get(i).donor.getUserName(), record.get(i).donor.getPhone(),
                    record.get(i).donor.getAids(), record.get(i).donor.getQuantity(),
                    record.get(i).Ngo.getUserName(), record.get(i).Ngo.getManpower(), record.get(i).getStatus());
                }
        }
        System.out.println();
        System.out.println();
    }

    /** 
     * Allows DC to view all aids donated to the DC, the donor of the aids and the NGOs receiving the aids in tabular form.
     * List<DC> record is declared and returns list from matching method.
     * The system will print table containing donor's name, donor's phone number, aids, quantity, NGO's name and NGO's manpower.
     * 
     * @param donor a list that contains the username and phone number of donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @param donation a list that contains the name of donor, aid name and quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity of aids received.
     * @param usernamelogin username used by user to login.
     * @throws IOException checked exception related to Input and Output operations in the Java code.
     */
    public static void DCview(List<User> donation, List<User> receiver, List<Donor> donors, List<NGO> ngos)
            throws IOException {

        List<DC> record = DC.matching(donors, donation, receiver, ngos);
        System.out.println();
        System.out.printf("%10s%12s%15s%12s%11s%12s%12s%n", "Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower","Status");
        System.out.println("----------  ----------  -------------  ----------  ---------  ---------- -----------");

        for (int i = 0; i < record.size(); i++) {
            System.out.printf("%10s%12s%15s%12d%11s%12s%12s%n",
                    record.get(i).donor.getUserName(), record.get(i).donor.getPhone(),
                    record.get(i).donor.getAids(), record.get(i).donor.getQuantity(),
                    record.get(i).Ngo.getUserName(), record.get(i).Ngo.getManpower(), record.get(i).getStatus());
        }
        System.out.println();
    }

}

    /** 
     * SystemDC contains the main method to run the program.
     */
class SystemDC{
    /** 
     * Lists of users, donation, receiver, donor and ngos are declared and return the details of list from the respective file.
     * The lists are passed to the static method DC.topmenu, which is the main menu of the program.
     * If file reading fails, the IOException will handle the error.
     * @param args not required.
     */
    public static void main(String[] args) {

        try {
            List<User> users = User.readUserFromFile();
            List<User> donation = User.readDonationFromFile();
            List<User> receiver = User.readReceiverFromFile();
            List<Donor> donors = Donor.readDonorFromFile();
            List<NGO> ngos = NGO.readNgoFromFile();

            DC.topmenu(users, donors, donation, receiver, ngos);

        }
        catch (IOException ex) {
            System.out.println("Error: file does not exist");
        }
    }
}