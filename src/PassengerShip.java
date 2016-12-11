/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A subclass of Ship that carries passengers
 *
 * @author matrix
 * @since 0.1
 */
public class PassengerShip extends Ship implements BaseObjectConformable {

    /** Number of passengers */
    private Integer passengers;
    /** Number of rooms */
    private Integer rooms;
    /** Number of rooms occupied */
    private Integer occupied;

    /** Default Constructor */
    public PassengerShip() {
        super();
    }

    /**
     * Default constructor - name only
     * @param n The name of the ship
     */
    public PassengerShip(String n) {
        super(n);
    }

    /**
     * Default constructor - all parameters
     * @param n Ship name
     * @param p Ship parent index
     * @param weight Ship weight
     * @param length Ship length
     * @param width Ship width
     * @param draft Ship draft
     * @param pass Number of passengers
     * @param r Number of rooms
     * @param o Number of rooms occupied
     */
   public PassengerShip(String n, Integer p, double weight, double length, double width, double draft, Integer pass, Integer r, Integer o) {
      super(n, p, weight, length, width, draft);

       passengers = pass;
       rooms = r;
       occupied = o;
   }

    /**
     * Print out a description of this ship
     * @return The string to print
     */
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

    /**
     * Returns a JTree default node with a description of the ship
     * @param i The index of this
     * @return JTree default node
     */
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
