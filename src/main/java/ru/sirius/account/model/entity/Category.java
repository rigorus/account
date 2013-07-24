package ru.sirius.account.model.entity;

public class Category {

    private int id;
    private String name;     
    protected int sortNumber;

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


    @Override
    public String toString(){
        return name;
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
        group.name = this.name;
        return group;
    }
}
