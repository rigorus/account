package ru.sirius.account.model.entity;

import java.math.BigDecimal;

public class Goods {
    
    protected int id;
    protected int parentId;
    protected boolean group;
    protected boolean deleted;
    protected String name;
    protected String shortName;
    protected BigDecimal price;
    protected String description;
    protected int depthIndex;
    protected int breadthIndex;

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

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDepthIndex() {
        return depthIndex;
    }

    public void setDepthIndex(int depthIndex) {
        this.depthIndex = depthIndex;
    }

    public int getBreadthIndex() {
        return breadthIndex;
    }

    public void setBreadthIndex(int breadthIndex) {
        this.breadthIndex = breadthIndex;
    }
}
