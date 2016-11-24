package Classes.Ships;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Matrix on 23-Nov-16.
 */
public class PassengerShip extends Ship {

    private Integer passengers;
    private Integer rooms;
    private Integer occupied;

    public PassengerShip() {
        super();
    }
    public PassengerShip(String n) {
        super(n);
    }

   public PassengerShip(String n, Integer p, double weight, double length, double width, double draft, Integer pass, Integer r, Integer o) {
      super(n, p, weight, length, width, draft);

       passengers = pass;
       rooms = r;
       occupied = o;
   }

    @Override
    public String toString() {
        double calc = ((occupied / rooms) * 100);
        String rtr = "";

        rtr += String.format("%s, Passenger Ship\n", name);
        rtr += super.toString();
        rtr += String.format("Contains %d Passengers\n", passengers);
        rtr += String.format("Rooms: %d (%d/%d [%.2f%%] occupied)", rooms, occupied, rooms, calc);

        return rtr;
    }

    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s - Passenger", i, name));

        rootNode.add(super.getJobTree());
        rootNode.add(super.getTree(i));

        rootNode.add(new DefaultMutableTreeNode(String.format("Passengers: %d", passengers)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Total Rooms: %d", rooms)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Occupied: %d", occupied)));

        return rootNode;
    }
}
