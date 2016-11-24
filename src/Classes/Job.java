package Classes;

import Base.BaseObject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class Job extends BaseObject implements Runnable {
    private double duration;
    private ArrayList<String> skills;

    private boolean done;

    public Job() { super(); }
    public Job(String n) { super(n); }

    public Job(String n, Integer p, double d, ArrayList<String> s) {
        super(n, p);
        duration = d;
        skills = s;
    }

    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s", i, name));
        rootNode.add(new DefaultMutableTreeNode(String.format("Time: %.2f seconds", duration)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Skills: %s", String.join(", ", skills))));

        return rootNode;
    }

    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s\n", name);
        rtr += String.format("Duration: %.2f\n", duration);
        rtr += String.format("Skills: %s\n", String.join(", ", skills));

        return rtr;
    }

    @Override
    public void run() {
        done = false;
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + (long) (1000 * duration);
        double duration = stopTime - time;

    }

    public boolean isDone() {
        return done;
    }

}
