/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * A subclass of Ship that carries cargo
 *
 * @author matrix
 * @since 0.1
 */
public class CargoShip extends Ship implements BaseObjectConformable {
    /**
     * The weight of the cargo
     */
    private double cargoWeight;
    /**
     * The volume of the cargo
     */
    private double cargoVolume;
    /**
     * The value of the cargo
     */
    private double cargoValue;

    /**
     * Default constructor - name only
     * @param n The name of the ship
     */
    public CargoShip(String n) { super(n); }

    /**
     * Default constructor - all parameters
     * @param n The name of the ship
     * @param p The parent index of the ship
     * @param weight The weight of the ship
     * @param length The length of the ship
     * @param width The width of the ship
     * @param draft The draft of the ship
     * @param cw The cargo weight
     * @param cvo The cargo volume
     * @param cva The cargo value
     */
    public CargoShip(String n, Integer p, double weight, double length, double width, double draft, double cw, double cvo, double cva) {
        super(n, p, weight, length, width, draft);

        cargoWeight = cw;
        cargoVolume = cvo;
        cargoValue = cva;
    }

    /**
     * Prints out details specific to cargo ships
     * @return String to print
     */
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s, Cargo Ship\n", name);
        rtr += super.toString();
        rtr += String.format("Cargo: %.2f lbs (Volume: %.2f)\n", cargoWeight, cargoVolume);
        rtr += String.format("Worth: $%.2f", cargoValue);

        return rtr;
    }

    /**
     * Return a JTree node containing info about the cargo ship
     * @param i The index of this
     * @return JTree node
     */
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s - Cargo", i, name));

        rootNode.add(super.getJobTree());
        rootNode.add(super.getTree(i));

        rootNode.add(new DefaultMutableTreeNode(String.format("Cargo Weight: %.2f lbs", cargoWeight)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Cargo Volume: %.2f", cargoVolume)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Cargo Value: $%.2f", cargoValue)));

        return rootNode;
    }
}