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
        System.out.println(String.format("Beginning %s. Will take %.2f seconds", name, duration));
        done = false;
        long time = System.currentTimeMillis();
        long stopTime = time + (long) (1000 * duration);

        while(time < stopTime) {
            double remaining = ((stopTime - time) / 1000);
            try {
                Thread.sleep(1000);
                System.out.println(String.format("%s is running. Remaining time %.0f", name, remaining));
                time += 1000;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("Job %s has finished", name));
    }

    public boolean isDone() {
        return done;
    }

    public double getDuration() { return duration; }
}
