package Classes;

import Base.BaseObject;
import Base.MainInterface;
import Classes.Ships.Ship;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class Job extends BaseObject implements Runnable {
    private double duration;
    private ArrayList<String> skills;

    private double progress = 0;
    private boolean runFlag = true;
    private boolean endFlag = false;
    private boolean finishFlag = false;

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
            try {
                Thread.sleep(500);
                if(!runFlag) { continue; }
                if(endFlag) { finished(); return; }
                time += 0.5;
                progress = ((time/duration) * 100);
                updateStatus();
            } catch (InterruptedException e) {
                return;
            }
        }
       finished();
    }

    public double getDuration() { return duration; }
    public String getSkills() {
        if(skills.isEmpty()) return "None";
        return String.join(", ", skills);
    }
    public double getProgress() {
        return progress;
    }
    public boolean isEndFlag() {
        return finishFlag;
    }

    public void toggleFlag() {
        runFlag = !runFlag;
    }
    public void stop() { endFlag = true; }

    private void finished() {
        finishFlag = true;
        baseWorld.jobDidComplete(this);
        Ship s = (Ship) baseWorld.findObject(parent);
        s.jobDidFinish();
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
