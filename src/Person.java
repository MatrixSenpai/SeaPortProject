/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Represents a person
 *
 * @author matrix
 * @since 0.1
 */
public class Person extends BaseObject implements BaseObjectConformable {

    /** This person's skill */
    private String skill;

    /** Whether this person is available in the pool */
    public boolean isAvailableInPool = true;

    /** Default Constructor */
    public Person() { super(); }

    /**
     * Default Constructor - name only
     * @param n Person's name
     */
    public Person(String n) { super(n); }

    /**
     * Default Constructor - all parameters
     * @param n Person's name
     * @param p Index of parent
     * @param s Person's skill
     */
    public Person(String n, Integer p, String s) {
        super(n, p);

        skill = s;
    }

    /**
     * Return a description of this person
     * @return The string to be printed
     */
    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s - %s\n", name, skill);

        return rtr;
    }

    /**
     * Return a JTree node with a description of this person
     * @param i The index of this
     * @return JTree default node
     */
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("(%d) %s", i, name));
        rootNode.add(new DefaultMutableTreeNode(String.format("Skill: %s", skill)));

        return rootNode;
    }
    public DefaultMutableTreeNode reportToPool() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        rootNode.add(new DefaultMutableTreeNode(String.format("Skill: %s", skill)));

        String available = "";
        if(isAvailableInPool) available = "Available for tasking";
        else available = "Tasked to ship";

        rootNode.add(new DefaultMutableTreeNode(available));

        return rootNode;
    }

    /**
     * Check if person has the skill specified
     * @param skillName The skill to check
     * @return If matches
     */
    public boolean hasSkill(String skillName) {
        return skill.equals(skillName);
    }

    /**
     * Search function to see if user has any matching terms
     * @param searchTerm The term to be searched
     * @return This if matching
     */
    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;
        if(hasSkill(searchTerm)) return this;
        return null;
    }
}
