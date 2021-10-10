package com.example.model.group;

public class RequestGroup {
    private String name;
    private int parentId;

    public RequestGroup(String name, int parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public RequestGroup(String name) {
        this(name,0);
    }

    public RequestGroup(Group group){
        this(group.getName());
    }

    public RequestGroup() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
