package Classes;

import Base.BaseObject;
import Base.MainInterface;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class Job extends BaseObject implements Runnable {
    private double duration;
    private ArrayList<String> skills;

    private double progress = 0;

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
        double time = 0;
        while(duration > time) {
            progress = ((time/duration) * 100);
            updateStatus();
            try {
                Thread.sleep(500);
                time += 0.5;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double getDuration() { return duration; }
    public String getSkills() { return String.join(", ", skills); }
    public double getProgress() {
        return progress;
    }

    private void updateStatus() {
        baseWorld.updateJob(this);
    }

    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;
        if(skills.contains(searchTerm)) return this;

        return null;
    }
}
