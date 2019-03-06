package com.fpoly.duyet.oderfood.Model;

/**
 * Created by duyet on 10/15/2018.
 */

public class Drink {
    public String ID;
    public String Name;
    public String Link;
    public String Price;
    public String MenuId;
    public String Detail;

    public Drink()
    {

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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getDetial() {
        return Detail;
    }

    public void setDetial(String detial) {
        Detail = detial;
    }
}

