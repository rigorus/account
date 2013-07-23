package ru.sirius.account.model.entity;

public class Category {

    private int id;
    private int parentId; // для корня 0
    private String name;     
    protected int sortNumber;

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


    @Override
    public String toString(){
        return name;
    }
    
    public boolean hasParent(){
        return parentId != 0;
    }        

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }
    
    @Override
    public Category clone(){
        Category group = new Category();
        group.id = this.id;
        group.parentId = this.parentId;
        group.name = this.name;
        return group;
    }
}
