package model;

import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private Group parent;
    private List<Group> subGroups;
    private List<Item> items;

    private Group(int id, String name, Group parent, List<Group> subGroups, List<Item> items) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.subGroups = subGroups;
        this.items = items;
    }

    public Group(String name, Group parent, List<Group> subGroups, List<Item> items) {
        this(0, name, parent, subGroups, items);
    }

    public Group(String name, Group parent, List<Group> subGroups) {
        this(0, name, parent, subGroups, null);
    }

    public Group(String name, Group parent) {
        this(0, name, parent, null, null);
    }

    public Group(String name) {
        this(0, name, null, null, null);
    }

    public Group() {
        this(0, null, null, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public List<Group> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<Group> subGroups) {
        this.subGroups = subGroups;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addSubGroup(Group group) {
        this.subGroups.add(group);
        group.setParent(this);
    }

    public void addItem(Item item){
        items.add(item);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(parent, group.parent) && Objects.equals(subGroups, group.subGroups) && Objects.equals(items, group.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parent, subGroups, items);
    }
}