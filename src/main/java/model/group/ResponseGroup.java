package model.group;

public class ResponseGroup {
    private int id;
    private String name;
    private int parentId;

    public ResponseGroup(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public ResponseGroup(int parentId){
        this(0,null,parentId);
    }

    public ResponseGroup(){}

    public ResponseGroup(Group group){
        this(group.getId(),group.getName());
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public ResponseGroup(int id, String name){
        this(id,name,0);
    }
}
