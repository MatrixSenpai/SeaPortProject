package Base;

import Classes.World;

import javax.swing.tree.DefaultMutableTreeNode;

public class BaseObject implements Comparable<BaseObject>, BaseObjectConformable {

    public String name;
    public Integer parent;

    public static World baseWorld = new World();

    public BaseObject() {
        name = "";
        parent = 0;
    }

    public BaseObject(String n) {
        name = n;
        parent = 0;
    }

    public BaseObject(String n, Integer p) {
        name = n;
        parent = p;
    }

    public int compareTo(BaseObject o) {
        return parent - o.parent;
    }

    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }

    public BaseObject matchesAnyComponent(String searchTerm) {
        if(name.equals(searchTerm)) return this;
        if(matchesIndex(searchTerm, parent)) { return this; }
        return null;
    }
    public BaseObject matchesAnyComponent(Integer searchIndex) {
        return null;
    }

    protected boolean matchesIndex(String s, Integer i) {
        try {
            return i.equals(Integer.parseInt(s));
        } catch (Exception e) { return false; }
    }
}
