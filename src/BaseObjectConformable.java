/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * The basic protocol that every BaseObject must conform to.
 *
 * Contains several convenience functions that are used to pass
 * data between objects
 *
 * @author matrix
 * @since 0.1
 */

public interface BaseObjectConformable {
    /**
     * Overriding the string to be printed out
     * @return The string to print
     */
    @Override
    String toString();

    /**
     * Requiring every BaseObject to provide a node for a JTree
     * that is callable
     * @param i The index of this
     * @return JTree default node
     */
    DefaultMutableTreeNode getTree(Integer i);

    /**
     * Requiring every BaseObject to search through itself
     * and provide itself or a child back if a match is found
     * @param searchTerm The term to be searched
     * @return This or child if matching
     */
    BaseObject matchesAnyComponent(String searchTerm);

    /**
     * Requiring every BaseObject to search through itself
     * and provide itself or a child back if a matching index
     * is found
     * @param searchIndex The index to be searched
     * @return This or child if matching
     */
    BaseObject matchesAnyComponent(Integer searchIndex);
}