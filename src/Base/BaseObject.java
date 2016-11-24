package Base;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Matrix on 23-Nov-16.
 */
public class BaseObject implements Comparable<BaseObject>, BaseObjectConformable {

    public String name;
    public Integer parent;

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
}
