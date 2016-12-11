/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The basic object behind every other object.
 *
 * This object is designed to be the foundation
 * object for this project.
 *
 * It contains several convenience constructors and functions
 *
 * @author matrix
 * @version 1.0
 * @since 0.1
 */
public class BaseObject implements Comparable<BaseObject>, BaseObjectConformable {

    /**
     * The name of the object
     */
    public String name;
    /**
     * The parent's index
     */
    public Integer parent;

    /**
     * Static reference to the base world, used by the Main Interface and all subclasses of BaseObject
     */
    public static World baseWorld = new World();

    /**
     * Default Constructor
     */
    public BaseObject() {
        name = "";
        parent = 0;
    }

    /**
     * Constructor
     * @param n The name of the object
     */
    public BaseObject(String n) {
        name = n;
        parent = 0;
    }

    /**
     * Constructor
     * @param n The name of the object
     * @param p The parent object
     */
    public BaseObject(String n, Integer p) {
        name = n;
        parent = p;
    }

    /**
     * Compare to a given other BaseObject
     * @param o The base object to compare to
     * @return Index
     */
    public int compareTo(BaseObject o) {
        return parent - o.parent;
    }

    /**
     * Get a JTree for the particular object. Meant to be implemented by subclasses
     * @param i The index of this
     * @return JTree with descriptive nodes
     */
    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        return null;
    }

    /**
     * Printable string for a particular object. Meant to be implemented by subclasses
     * @return String to be printed
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * Check if term passed matches anything in the object. Meant to be further implemented by subclasses
     * @param searchTerm The term to be searched
     * @return this if matches
     */
    public BaseObject matchesAnyComponent(String searchTerm) {
        if(name.equals(searchTerm)) return this;
        if(matchesIndex(searchTerm, parent)) { return this; }
        return null;
    }

    /**
     * Check if index matches this or any child objects. Meant to by implemented by subclasses
     * @param searchIndex The index to search
     * @return Any matching object
     */
    public BaseObject matchesAnyComponent(Integer searchIndex) {
        return null;
    }

    /**
     * A quick conversion function to see if the index passed matches
     * @param s The index in string format
     * @param i the index in integer format
     * @return if matches
     */
    protected boolean matchesIndex(String s, Integer i) {
        try {
            return i.equals(Integer.parseInt(s));
        } catch (Exception e) { return false; }
    }
}