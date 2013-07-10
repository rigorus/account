package ru.sirius.account.model;


public class Article {
  
    private int id;
    private int classificationId;
    private String fullName;
    private String shortName;
    private String description;            
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }    
    
    
    @Override
    public Article clone() {
        Article article = new Article();
        article.id = this.id;
        article.fullName = this.fullName;
        article.shortName = this.shortName;
        article.classificationId = this.classificationId;
        article.comment = this.comment;
        article.description = this.description;
        return article;
    }
    
    
}
