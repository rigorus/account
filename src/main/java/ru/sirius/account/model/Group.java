package ru.sirius.account.model;

public class Group {

    private int id;
    private int parentId; // для корня 0
    private String name;           
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){
        return name;
    }
    
    public boolean hasParent(){
        return parentId != 0;
    }        
    
    @Override
    public Group clone(){
        Group group = new Group();
        group.id = this.id;
        group.parentId = this.parentId;
        group.name = this.name;
        group.comment = this.comment;
        return group;
    }
}
