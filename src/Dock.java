/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * The Dock object is responsible for managing a single ship and communicating about its
 * progress and status. It is responsible for requesting crew members for a ship and releasing
 * them back to the assigned port
 *
 * @author matrix
 * @since 0.1
 */
public class Dock extends BaseObject implements BaseObjectConformable {

    /**
     * The currently docked ship
     */
    private Ship dockedShip;
    /**
     * The ID of the ship docked
     */
    private Integer shipID;
    private ArrayList<Person> inUsePersons = new ArrayList<>();

    /**
     * A synchronization object to prevent race conditions
     * inside the dock when requesting data
     */
    private Object lock = new Object();

    /**
     * Default constructor
     */
    public Dock() {
        super();
    }

    /**
     * Default constructor - All parameters
     * @param n The name of the dock
     * @param p The parent index
     */
    public Dock(String n, Integer p) {
        super(n, p);
    }

    // Display/UI Methods

    /**
     * Prints a descriptive string about the dock
     * @return The string to print
     */
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s\n", name);
        if(dockedShip != null) {
            rtr += String.format("(%d) Docked Ship: %s\n", shipID, dockedShip);
        }

        return rtr;
    }

    /**
     * Returns a JTree node containing information about this dock
     * and it's child ship
     * @param i The index of this
     * @return JTree default node
     */
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s", i, name));

        rootNode.add(dockedShip.getTree(shipID));

        return rootNode;
    }

    // Jobs Methods

    /**
     * Tell a ship to begin its jobs
     */
    public void startJobs() {
        dockedShip.shipShouldBeginWorking();
    }

    /**
     * Tell a ship to toggle the state of its jobs
     */
    public void toggleJobs() {
        dockedShip.shipShouldPauseWorking();
    }

    /**
     * Tell a ship to stop its jobs
     */
    public void stopJobs() {
        dockedShip.shipShouldEndWorking();
    }

    public boolean getPersonsForJob(Job j) {
        Port p = (Port) baseWorld.findObject(parent);

        for(String sk: j.getSkillsArray()) {
            if(sk.toLowerCase() == "none") return true;
            if(!p.hasPersonWithSkill(sk)) return false;
        }

        Runnable r = () -> {
            for(String s: j.getSkillsArray()) {
                Person pe;
                while(true) {
                    pe = p.dockWantsPersonWithSkill(s);
                    if(pe == null) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    inUsePersons.add(pe);
                    break;

                }
            }
        };


        Thread t = new Thread(r);
        t.start();
        return true;
    }

    /**
     * Delegate function called when the ship completes.
     * Includes the process to pull a new ship from the dock
     */
    public void shipDidFinish() {
        dockedShip = null;
        Port p = (Port) baseWorld.findObject(parent);
        p.releasePersonsToDock(inUsePersons);
        inUsePersons.clear();
        dockedShip = p.dockWantsNextShip();

        baseWorld.shipDidLeaveDock();
        startJobs();
    }

    // Getters/Setters

    /**
     * Docking a new ship
     * @param i The index of the ship to be docked
     * @param s The new ship to be docked
     */
    public void setDockedShip(Integer i, Ship s) {
        dockedShip = s;
        shipID = i;
    }

    /**
     * Get the ID of the currently docked ship
     * @return Docked ship's ID
     */
    public Integer getShipID() {
        return shipID;
    }

    /**
     * Get the currently docked ship
     * @return Docked ship
     */
    public Ship getDockedShip() {
        return dockedShip;
    }

    /**
     * Search this dock and its docked ship for a term
     * @param searchTerm The term to be searched
     * @return This or a child
     */
    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;

        if(super.matchesIndex(searchTerm, shipID)) return dockedShip;

        b = dockedShip.matchesAnyComponent(searchTerm);

        return b;
    }
}
