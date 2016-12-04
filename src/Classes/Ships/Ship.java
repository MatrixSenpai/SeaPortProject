package Classes.Ships;

import Base.BaseObject;
import Classes.Job;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public void shipShouldBeginWorking() {
        synchronized(lock) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            for (Map.Entry<Integer, Job> jobEntry : jobs.entrySet()) {
                Job j = jobEntry.getValue();
                baseWorld.jobReportedRunning(j);
                scheduler.scheduleAtFixedRate(j, 0, (long) j.getDuration(), TimeUnit.SECONDS);
            }
        }
    }
    public void shipShouldEndWorking() {
        scheduler.shutdown();
    }

    // Getters/Setters
    public void addJob(Integer k, Job j) { jobs.put(k, j); }
    public Job findJob(Integer i) {
        if(jobs.containsKey(i)) {
            return jobs.get(i);
        }
        return null;
    }
}
