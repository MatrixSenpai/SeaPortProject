package Classes;

import Base.BaseObject;

import javax.swing.tree.DefaultMutableTreeNode;

public class Person extends BaseObject {

    private String skill;

    public Person() { super(); }
    public Person(String n) { super(n); }

    public Person(String n, Integer p, String s) {
        super(n, p);

        skill = s;
    }

    @Override
    public String toString() {
        String rtr = "";

        rtr += String.format("%s - %s\n", name, skill);

        return rtr;
    }

    @Override
    public DefaultMutableTreeNode getTree(Integer i) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("(%d) %s", i, name));
        rootNode.add(new DefaultMutableTreeNode(String.format("Skill: %s", skill)));

        return rootNode;
    }

    public boolean hasSkill(String skillName) {
        return skill.equals(skillName);
    }

    @Override
    public BaseObject matchesAnyComponent(String searchTerm) {
        BaseObject b = super.matchesAnyComponent(searchTerm);
        if(b != null) return b;
        if(hasSkill(searchTerm)) return this;
        return null;
    }
}
