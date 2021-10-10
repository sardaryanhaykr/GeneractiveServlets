package com.example.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.model.item.Item;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"groups\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_seq")
    @SequenceGenerator(name = "groups_id_seq", sequenceName = "groups_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Group parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Group> subGroups;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Item> items;

    private Group(int id, String name, Group parent, List<Group> subGroups, List<Item> items) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.subGroups = subGroups;
        this.items = items;
    }


    public Group(int id, String name, Group parent) {
        this(id, name, parent, null, null);
    }

    public Group(int id) {
        this(id, null, null);
    }

    public Group(int id, String name) {
        this(id, name, null, null, null);
    }

    public Group(String name, Group parent, List<Group> subGroups, List<Item> items) {
        this(0, name, parent, subGroups, items);
    }

    public Group(ResponseGroup group) {
        this(group.getId(), group.getName(), new Group(group.getParentId()));
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

    public void addItem(Item item) {
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
        return Objects.hash(id);
    }
}
