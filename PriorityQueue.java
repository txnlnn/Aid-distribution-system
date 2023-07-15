import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * PriorityQueue class has a private Linked list of NGO and private integer size
 */
public class PriorityQueue {
    private LinkedList<NGO> list = new LinkedList<>();
    private int size;

    /**
     * Returns an integer representation of this queue.
     * @return the size of the list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Adds the element entered by user into the queue.
     * An NGO with higher manpower will have higher priority and is always at the front of the queue.
     * 
     * @param e the element to enqueue.
     */
    public void enqueue(NGO e) {
        boolean flag = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserName().equals(e.getUserName())) {
                System.out.println("The NGO is already in the queue.");
                flag = true;
            }
        }

        if (flag == false) {  
            list.add(e);
            Collections.sort(list, Collections.reverseOrder());
            size++;
        }
    }

    /**
     * Removes and returns the first element of the PriorityQueue.
     * If the user dequeues an element successfully, DC records will be updated and displayed. 
     * If the user tries to dequeue an empty queue, the program will display an error message. 
     * 
     * @param donation a list that contains the name of the donor, aid name and
     *                 quantity of aids.
     * @param receiver a list that contains the name of NGO, name, and quantity
     *                 of aids received.
     * @param donors   a list that contains the username and phone number of
     *                 donor.
     * @param ngos a list that contains the username and manpower of NGOs.
     * @throws IOException checked exception related to Input and Output operations
     *                     in the Java code.
     */
    public void dequeue(List<User> donation, List<User> receiver, List<Donor> donors, List<NGO> ngos) throws IOException {
        if (size == 0)
            System.out.println("The queue is already empty.");

        else {
            size--;
   
                List<DC> record = DC.matching(donors, donation, receiver, ngos);
                
                NGO toRemove = new NGO(list.get(0).getUserName(), list.get(0).getHeadcount());
                for (int i = 0 ; i<record.size(); i++){
                    if(toRemove.getUserName().equals(record.get(i).Ngo.getUserName())){

                        String donorname = record.get(i).donor.getUserName();
                        String ngoname = record.get(i).Ngo.getUserName();
                        String aid = record.get(i).donor.getAids();
                        int donated = record.get(i).donor.getQuantity();
                        String phone = record.get(i).donor.getPhone();
                        String manpower = record.get(i).Ngo.getManpower();

                        record.set(i,new DC(new Donor(donorname,phone,donated,aid),new NGO (ngoname,manpower),"Collected"));
                    }
                }
                System.out.println(list.remove(0));
                DC.saveRecordToFile(record);
                DC.DCview(donation, receiver, donors, ngos);  
            }
    }
    
    /**
     * Returns a string representation of the queue.
     * 
     * @return the sequence of the list in PriorityQueue order.
     */
    public String toString() {
        return list.toString();
    }           
}