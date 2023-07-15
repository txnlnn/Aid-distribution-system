import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Queue {

    private LinkedList<String> list = new LinkedList<>();

    /**
     * Adds the element entered by user to the beginning of the queue.
     * 
     * @param e the element to enqueue.
     */
    public void enqueue(String e) {
        boolean flag = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(e)) {
                System.out.println("The NGO is already in the queue.");
                flag = true;
            }
        }

        if (flag == false) {
            list.add(e);
        }
    }

    /**
     * Removes and returns the first element of the queue.
     * If user dequeues an element successfully, DC records will be updated and displayed. 
     * If user tries to dequeue an empty queue, the program will display an error message. 
     * 
     * @param donation a list that contains the name of donor, aid name and
     *                 quantity of aids.
     * @param receiver a list that contains the name of NGO, name and quantity
     *                 of aids received.
     * @param donors   a list that contains the username and phone number of
     *                 donor.
     * @param ngos     a list that contains the username and manpower of NGOs.
     * @throws IOException checked exception related to Input and Output operations
     *                     in the Java code.
     */
    public void dequeue(List<User> donation, List<User> receiver, List<Donor> donors, List<NGO> ngos)
            throws IOException {
        if (list.isEmpty())
            System.out.println("The queue is already empty.");

        else {
            List<DC> record = DC.matching(donors, donation, receiver, ngos);

            String toRemove = list.get(0);
            for (int i = 0; i < record.size(); i++) {
                if (toRemove.equals(record.get(i).Ngo.getUserName())) {

                    String donorname = record.get(i).donor.getUserName();
                    String ngoname = record.get(i).Ngo.getUserName();
                    String aid = record.get(i).donor.getAids();
                    int donated = record.get(i).donor.getQuantity();
                    String phone = record.get(i).donor.getPhone();
                    String manpower = record.get(i).Ngo.getManpower();

                    record.set(i,
                            new DC(new Donor(donorname, phone, donated, aid), new NGO(ngoname, manpower), "Collected"));
                }
            }
            System.out.println(list.remove(0));
            DC.saveRecordToFile(record);
            DC.DCview(donation, receiver, donors, ngos);
        }
    }

    /**
     * Returns true if, and only if, the length is 0.
     * 
     * @return true if the list is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns a string representation of the queue.
     * 
     * @return the sequence of list in FIFO order.
     */
    public String toString() {
        return list.toString();
    }
}