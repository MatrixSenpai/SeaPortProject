/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

/**
 * A collection of docks.
 * This object manages communication between docks and the world,
 * and also handles a lot of searching
 *
 * @author matrix
 * @since 0.1
 */
public class Port extends BaseObject {

    private HashMap<Integer, Dock> docks = new HashMap<Integer, Dock>();
    private HashMap<Integer, Ship> queue = new HashMap<Integer, Ship>();
    private HashMap<Integer, Person> persons = new HashMap<Integer, Person>();

    public Port() {
        super();
    }

    public Port(String n) {
        // Port has no parent (except world), so we only have to call BaseObject(String)
        super(n);
    }

    // UI/Display Methods
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s\n", name);
        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            Integer i = dockEntry.getKey();
            Dock d = dockEntry.getValue();

            rtr += String.format("(%d) Dock: %s\n", i, d);
        }

        rtr += "Port Queue\n";
        for(Map.Entry<Integer, Ship> shipEntry: queue.entrySet()) {
            Integer i = shipEntry.getKey();
            Ship s = shipEntry.getValue();

            rtr += String.format("\t(%d) Ship: %s\n", i, s);
        }
        rtr += "Personnel Assigned\n";
        for(Map.Entry<Integer, Person> personEntry: persons.entrySet()) {
            Integer i = personEntry.getKey();
            Person p = personEntry.getValue();

            rtr += String.format("\t(%d) Person: %s\n", i, p);
        }

        rtr += "\n\n"; // Pushing the next string down
        return rtr;
    }
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s", i, name));

        Integer dc = docks.size();
        DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode(String.format("Docks (%d)", dc));
        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            dockNode.add(dockEntry.getValue().getTree(dockEntry.getKey()));
        }
        rootNode.add(dockNode);

        Integer pc = queue.size();
        DefaultMutableTreeNode queueNode = new DefaultMutableTreeNode(String.format("Port Queue (%d ships)", pc));
        for(Map.Entry<Integer, Ship> shipEntry: queue.entrySet()) {
            queueNode.add(shipEntry.getValue().getTree(shipEntry.getKey()));
        }
        rootNode.add(queueNode);

        Integer ppc = persons.size();
        DefaultMutableTreeNode personnelNode = new DefaultMutableTreeNode(String.format("Personnel Assigned (%d)", ppc));
        for(Map.Entry<Integer, Person> personEntry: persons.entrySet()) {
            personnelNode.add(personEntry.getValue().getTree(personEntry.getKey()));
        }
        rootNode.add(personnelNode);

        return rootNode;
    }
    public DefaultMutableTreeNode reportPool() {
        DefaultMutableTreeNode baseNode = new DefaultMutableTreeNode(name);
        for(Person p: persons.values()) {
            baseNode.add(p.reportToPool());
        }

        return baseNode;
    }

    // Jobs Methods
    public void startJobs() {
        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            dockEntry.getValue().startJobs();
        }
    }
    public void toggleJobs() {
        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            dockEntry.getValue().toggleJobs();
        }
    }
    public void stopJobs() {
        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            dockEntry.getValue().stopJobs();
        }
    }

    // Public adding methods
    public void addDock(Integer k, Dock d) {
        docks.put(k, d);
    }
    public void addQueue(Integer k, Ship s) { queue.put(k, s); }
    public void addPerson(Integer k, Person p) {persons.put(k, p); }

    // Search methods
    public Dock findDock(Integer i) {
        if(docks.containsKey(i)) {
            return docks.get(i);
        }
        return null;
    }
    public Ship findShip(Integer i) {
        if(queue.containsKey(i)) {
            return queue.get(i);
        }

        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            Dock d = dockEntry.getValue();
            Integer id = d.getShipID();
            if (Objects.equals(i, id)) {
                return d.getDockedShip();
            }
        }
        return null;
    }
    public Job findJob(Integer i) {
        for(Map.Entry<Integer, Ship> shipEntry: queue.entrySet()) {
            Job j = shipEntry.getValue().findJob(i);
        }

        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            Job j = dockEntry.getValue().getDockedShip().findJob(i);
            if(j != null) { return j; }
        }
        return null;
    }
    public Person findPerson(Integer i) {
        if(persons.containsKey(i)) {
            return persons.get(i);
        }
        return null;
    }
    public Person findPersonWithSkill(String skillName) {
        for(Map.Entry<Integer, Person> personEntry: persons.entrySet()) {
            Person p = personEntry.getValue();
            if(p.hasSkill(skillName)) {
                return p;
            }
        }
        return null;
    }

    public Ship dockWantsNextShip() {
        if(queue.size() < 1) { return new Ship(); }
        Ship s = queue.entrySet().iterator().next().getValue();
        queue.remove(s);
        return s;
    }

    public Person dockWantsPersonWithSkill(String skill) {
        Person p = dockWantsPersonWithSkill(skill);
        if(p == null) {
            // TODO
            // Release ship - no person with skill available in this port
        }

        return p;
    }

    private Person hasPersonWithSkill(String skill) {
        for(Person p: persons.values()) if (p.hasSkill(skill)) return p;
        return null;
    }

    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;

        for(Map.Entry<Integer, Dock> dockEntry: docks.entrySet()) {
            Integer index = dockEntry.getKey();
            Dock d = dockEntry.getValue();

            if(super.matchesIndex(searchTerm, index)) return d;

            b = d.matchesAnyComponent(searchTerm);

            if(b != null) return b;
        }

        for(Map.Entry<Integer, Ship> shipEntry: queue.entrySet()) {
            Integer index = shipEntry.getKey();
            Ship s = shipEntry.getValue();

            if(super.matchesIndex(searchTerm, index)) return s;

            b = s.matchesAnyComponent(searchTerm);

            if(b != null) return b;
        }

        for(Map.Entry<Integer, Person> personEntry: persons.entrySet()) {
            Integer index = personEntry.getKey();
            Person p = personEntry.getValue();

            if(super.matchesIndex(searchTerm, index)) return p;

            b = p.matchesAnyComponent(searchTerm);

            if(b != null) return b;
        }

        return null;
    }
}
