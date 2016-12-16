/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * The job class.
 * Managing status, runtime, and synchronization
 *
 * @author matrix
 * @since 0.1
 */
public class Job extends BaseObject implements Runnable {
    /** The duration of the job */
    private double duration;
    /** Skills required */
    private ArrayList<String> skills;

    /** The current progress of the job */
    private double progress = 0;
    /** Whether the job should be running */
    private boolean runFlag = true;
    /** Whether the job should end */
    private boolean endFlag = false;
    /** Whether the job is finished */
    private boolean finishFlag = false;

    /**
     * Default Constructor
     */
    public Job() { super(); }

    /**
     * Default Constructor - name only
     * @param n Job name
     */
    public Job(String n) { super(n); }

    /**
     * Default Constructor - all parameters
     * @param n Job name
     * @param p Job parent
     * @param d Job duration
     * @param s Job skills required
     */
    public Job(String n, Integer p, double d, ArrayList<String> s) {
        super(n, p);
        duration = d;
        skills = s;
    }

    /**
     * Return a JTree node with details about this job
     * @param i The index of this
     * @return JTree default node
     */
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%d: %s", i, name));
        rootNode.add(new DefaultMutableTreeNode(String.format("Time: %.2f seconds", duration)));
        rootNode.add(new DefaultMutableTreeNode(String.format("Skills: %s", String.join(", ", skills))));

        return rootNode;
    }

    /**
     * Descriptive string about this job
     * @return The string to be printed
     */
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s\n", name);
        rtr += String.format("Duration: %.2f\n", duration);
        rtr += String.format("Skills: %s\n", String.join(", ", skills));

        return rtr;
    }

    /**
     * Begin running the job, including reporting progress
     */
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

    /**
     * Get the duration of the job
     * @return Duration in seconds
     */
    public double getDuration() { return duration; }

    /**
     * Get skills required as a single string
     * @return String of skills required
     */
    public String getSkills() {
        if(skills.isEmpty()) return "None";
        return String.join(", ", skills);
    }

    /**
     * Get the skills required in an array
     * @return Skills list
     */
    public ArrayList<String> getSkillsArray() {
        return skills;
    }

    /**
     * Get current progress of the job
     * @return Current progress
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Get whether the job has completed
     * @return Finished flag
     */
    public boolean isEndFlag() {
        return finishFlag;
    }

    /** Toggle the run state */
    public void toggleFlag() {
        runFlag = !runFlag;
    }
    /** Kill job */
    public void stop() { endFlag = true; }

    /**
     * Report the end of a job and set its status
     * to completed internally
     */
    private void finished() {
        finishFlag = true;
        baseWorld.jobDidComplete(this);
        Ship s = (Ship) baseWorld.findObject(parent);
        s.jobDidFinish();
    }

    /**
     * Report the current status to the base world
     */
    private void updateStatus() {
        baseWorld.updateJob(this);
    }

    /**
     * Find a portion of this job
     * @param searchTerm The term to be searched
     * @return This if matches
     */
    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;
        if(skills.contains(searchTerm)) return this;

        return null;
    }
}
