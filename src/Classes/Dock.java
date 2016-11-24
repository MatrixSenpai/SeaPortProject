package Classes;

import Base.BaseObject;
import Classes.Ships.Ship;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dock extends BaseObject {

    private Ship dockedShip;
    private Integer shipID;

    public Dock() {
        super();
    }

    public Dock(String n, Integer p) {
        super(n, p);
    }

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

    public void startJobs() {
        
    }

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
}
