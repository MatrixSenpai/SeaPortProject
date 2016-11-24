package Classes.Ships;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Matrix on 23-Nov-16.
 */
public class CargoShip extends Ship {
    private double cargoWeight;
    private double cargoVolume;
    private double cargoValue;

    public CargoShip(String n) { super(n); }

    public CargoShip(String n, Integer p, double weight, double length, double width, double draft, double cw, double cvo, double cva) {
        super(n, p, weight, length, width, draft);

        cargoWeight = cw;
        cargoVolume = cvo;
        cargoValue = cva;
    }

    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s, Cargo Ship\n", name);
        rtr += super.toString();
        rtr += String.format("Cargo: %.2f lbs (Volume: %.2f)\n", cargoWeight, cargoVolume);
        rtr += String.format("Worth: $%.2f", cargoValue);

        return rtr;
    }

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
