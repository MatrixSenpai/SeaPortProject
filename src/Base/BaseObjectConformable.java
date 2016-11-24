package Base;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Matrix on 23-Nov-16.
 */
public interface BaseObjectConformable {
    @Override
    String toString();

    DefaultMutableTreeNode getTree(Integer i); // Allows the parent to pass its index to the child
}
