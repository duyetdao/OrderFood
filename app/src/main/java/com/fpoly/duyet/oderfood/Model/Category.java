package com.fpoly.duyet.oderfood.Model;

/**
 * Created by duyet on 10/15/2018.
 */

public class Category {
    public String ID;
    public String Link;
    public String Name;

    public Category(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
