package Classes;

import Base.BaseObject;
import Classes.Ships.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.util.*;

public class World extends BaseObject {

    private HashMap<Integer, Port> ports = new HashMap<Integer, Port>();

    public World() {

    }

    // UI/Display Methods
    @Override
    public String toString() {
        String rtr = "";

        // String elements
        // Note this is all we have to do, each "child" is responsible for passing itself up
        // Also note that we are printing the "child's" ID in the "parent" object
        // This handles the issue of the child not knowing its own ID
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Integer i = portEntry.getKey();
            Port p = portEntry.getValue();

            rtr += String.format("(%d) Port: %s\n", i, p);
        }

        return rtr;
    }
    public JTree getTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");

        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Integer i = portEntry.getKey();
            Port p = portEntry.getValue();

            rootNode.add(p.getTree(i));
        }

        return new JTree(rootNode);
    }

    public void processFile(File f) {
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNext()) {
                switch (sc.next()) {
                    case "port":
                        addPort(sc);
                        break;
                    case "dock":
                        addDock(sc);
                        break;
                    case "pship":
                        addPassShip(sc);
                        break;
                    case "cship":
                        addCargoShip(sc);
                        break;
                    case "person":
                        addPerson(sc);
                        break;
                    case "job":
                        addJob(sc);
                        break;
                    default: // Ignore trash
                        if (sc.hasNextLine()) sc.nextLine();
                        break;
                }
            }

            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Jobs methods
    public void startJobs() {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            portEntry.getValue().startJobs();
        }
    }
    public void stopJobs() {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            portEntry.getValue().stopJobs();
        }
    }

    // MARK: - Private Add/Create methods
    private void addPort(Scanner sc) {

        String n = sc.next();
        int i = Integer.parseInt(sc.next());
        int parent = Integer.parseInt(sc.next());

        Port p = new Port(n);
        ports.put(i, p);
    }
    private void addDock(Scanner sc) {
        String n = sc.next();
        int i = sc.nextInt();
        int pa = sc.nextInt();

        Dock d = new Dock(n, pa);
        Port p = findPort(pa);
        p.addDock(i, d);
    }
    private void addPassShip(Scanner sc) {
        String name = sc.next();
        int index = sc.nextInt();
        int parent = sc.nextInt();
        double weight = sc.nextDouble();
        double length = sc.nextDouble();
        double width = sc.nextDouble();
        double draft = sc.nextDouble();
        Integer passengers = sc.nextInt();
        Integer rooms = sc.nextInt();
        Integer occupied = sc.nextInt();

        PassengerShip s = new PassengerShip(name, parent, weight, length, width, draft, passengers, rooms, occupied);
        Dock d = findDock(parent);
        if(d != null) { d.setDockedShip(index, s); return; }
        Port p = findPort(parent);
        p.addQueue(index, s);
    }
    private void addCargoShip(Scanner sc) {
        String name = sc.next();
        int index = sc.nextInt();
        int parent = sc.nextInt();
        double weight = sc.nextDouble();
        double length = sc.nextDouble();
        double width = sc.nextDouble();
        double draft = sc.nextDouble();
        double cweight = sc.nextDouble();
        double cvol = sc.nextDouble();
        double cval = sc.nextDouble();

        CargoShip s = new CargoShip(name, parent, weight, length, width, draft, cweight, cvol, cval);
        Dock d = findDock(parent);
        if(d != null) { d.setDockedShip(index, s); return; }
        Port p = findPort(parent);
        p.addQueue(index, s);
    }
    private void addPerson(Scanner sc) {
        String n = sc.next();
        Integer i = sc.nextInt();
        Integer p = sc.nextInt();
        String s = sc.next();

        Person h = new Person(n, p, s);
        Port po = findPort(p);
        po.addPerson(i, h);
    }
    private void addJob(Scanner sc) {
        String n = sc.next();
        int i = sc.nextInt();
        int p = sc.nextInt();
        double d = sc.nextDouble();
        ArrayList<String> s = new ArrayList<>(Arrays.asList(sc.nextLine().split(" ")));
        if(s.get(0).isEmpty()) { s.remove(0); }

        Job j = new Job(n, p, d, s);
        Ship ship = findShip(p);
        ship.addJob(i, j);
    }

    // MARK: - Public Search Methods
    public BaseObject findObject(Integer i) {
        Port p = findPort(i);
        if(p != null) { return p; }

        Dock d = findDock(i);
        if(d != null) { return d; }

        Ship s = findShip(i);
        if(s != null) { return s; }

        Person pe = findPerson(i);
        if(pe != null) { return pe; }

        Job j = findJob(i);
        if(j != null) { return j; }

        return null;
    }

    // MARK: - Private FindBaseObject methods
    private Port findPort(int i) {
        if(ports.containsKey(i)) {
            return ports.get(i);
        }
        return null;
    }
    private Dock findDock(int i) {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Port p = portEntry.getValue();
            Dock d = p.findDock(i);
            if(d != null) { return d; }
        }
        return null;
    }
    private Ship findShip(int i) {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Ship s = portEntry.getValue().findShip(i);
            if(s != null) { return s; }
        }
        return null;
    }
    private Person findPerson(int i) {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Person p = portEntry.getValue().findPerson(i);
            if(p != null) { return p; }
        }
        return null;
    }
    private Job findJob(int i) {
        for(Map.Entry<Integer, Port> portEntry: ports.entrySet()) {
            Job j = portEntry.getValue().findJob(i);
            if(j != null) { return j; }
        }
        return null;
    }
}
