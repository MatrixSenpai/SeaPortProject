/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Ship extends BaseObject {
    private double weight;
    private double length;
    private double width;
    private double draft;

    private HashMap<Integer, Job> jobs = new HashMap<Integer, Job>();

    private Object lock = new Object();
    private ScheduledExecutorService scheduler;

    public Ship() { super(); }
    public Ship(String n) {
        super(n);
    }

    public Ship(String n, Integer p, double we, double l, double wi, double d) {
        super(n, p);

        weight = we;
        length = l;
        width = wi;
        draft = d;
    }

    // Display/UI Methods
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("Weight: %.2f\n", weight);
        rtr += String.format("Size:   %.2f x %.2f\n", length, width);
        rtr += String.format("Draft:  %.2f\n", draft);

        return rtr;
    }
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        // Meant to be called by children to return specs
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Specifications");
        rootNode.add(new DefaultMutableTreeNode(String.format("Weight: %.2f", weight)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Length: %.2f", length)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Width:  %.2f", width)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Draft : %.2f", draft)));

        return rootNode;
    }
    public DefaultMutableTreeNode getJobTree() {
        DefaultMutableTreeNode jobsNode = new DefaultMutableTreeNode("Jobs");
        for(Map.Entry<Integer, Job> jobEntry: jobs.entrySet()) {
            jobsNode.add(jobEntry.getValue().getTree(jobEntry.getKey()));
        }

        return jobsNode;
    }

    // Jobs methods per ship
    public void shipShouldBeginWorking() {
        synchronized(lock) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            for (Map.Entry<Integer, Job> jobEntry : jobs.entrySet()) {
                Job j = jobEntry.getValue();
                baseWorld.jobReportedRunning(j);
                scheduler.submit(j);
            }
        }
    }
    public void shipShouldPauseWorking() {
        for(Map.Entry<Integer, Job> jobEntry: jobs.entrySet()) {
            Job j = jobEntry.getValue();
            synchronized (lock) {
                j.toggleFlag();
            }
        }
    }
    public void shipShouldEndWorking() {
        synchronized (lock) {
            scheduler.shutdownNow();
        }
    }
    public void jobDidFinish() {
        for(Map.Entry<Integer, Job> jobEntry: jobs.entrySet()) {
            Job j = jobEntry.getValue();
            if(!j.isEndFlag()) {
                return;
            }
        }

        Dock p = (Dock) baseWorld.findObject(parent);
        p.shipDidFinish();
    }

    // Getters/Setters
    public void addJob(Integer k, Job j) { jobs.put(k, j); }
    public Job findJob(Integer i) {
        if(jobs.containsKey(i)) {
            return jobs.get(i);
        }
        return null;
    }

    // Search methods

    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;

        for(Map.Entry<Integer, Job> jobEntry: jobs.entrySet()) {
            Integer index = jobEntry.getKey();
            Job j = jobEntry.getValue();

            if(super.matchesIndex(searchTerm, index)) return j;

            b = j.matchesAnyComponent(searchTerm);
            if(b != null) return b;
        }

        return null;
    }
}
