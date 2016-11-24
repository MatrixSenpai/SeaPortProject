package Classes.Ships;

import Base.BaseObject;
import Classes.Job;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ship extends BaseObject {
    private double weight;
    private double length;
    private double width;
    private double draft;

    private HashMap<Integer, Job> jobs = new HashMap<Integer, Job>();

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

    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("Weight: %.2f\n", weight);
        rtr += String.format("Size:   %.2f x %.2f\n", length, width);
        rtr += String.format("Draft:  %.2f\n", draft);

        return rtr;
    }

    public void shipShouldBeginWorking() {
        synchronized(this) {
            for (Map.Entry<Integer, Job> jobEntry : jobs.entrySet()) {
                try {
                    Thread t = new Thread(jobEntry.getValue());
                    t.start();
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public void addJob(Integer k, Job j) { jobs.put(k, j); }
    public Job findJob(Integer i) {
        if(jobs.containsKey(i)) {
            return jobs.get(i);
        }
        return null;
    }
}
