package Classes;

import Base.BaseObject;
import Classes.Ships.Ship;

import javax.swing.tree.DefaultMutableTreeNode;

public class Dock extends BaseObject {

    private Ship dockedShip;
    private Integer shipID;

    private Object lock = new Object();

    public Dock() {
        super();
    }

    public Dock(String n, Integer p) {
        super(n, p);
    }

    // Display/UI Methods
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s\n", name);
        if(dockedShip != null) {
            rtr += String.format("(%d) Docked Ship: %s\n", shipID, dockedShip);
        }

        return rtr;
    }
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s", i, name));

        rootNode.add(dockedShip.getTree(shipID));

        return rootNode;
    }

    // Jobs Methods
    public void startJobs() {
        dockedShip.shipShouldBeginWorking();
    }
    public void toggleJobs() {
        dockedShip.shipShouldPauseWorking();
    }
    public void stopJobs() {
        dockedShip.shipShouldEndWorking();
    }

    public void shipDidFinish() {
        // TODO
        // De-init docked ship (mark as complete),
        // grab next ship from queue, restart process
    }

    // Getters/Setters
    public void setDockedShip(Integer i, Ship s) {
        dockedShip = s;
        shipID = i;
    }
    public Integer getShipID() {
        return shipID;
    }
    public Ship getDockedShip() {
        return dockedShip;
    }

    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;

        if(super.matchesIndex(searchTerm, shipID)) return dockedShip;

        b = dockedShip.matchesAnyComponent(searchTerm);

        return b;
    }
}
