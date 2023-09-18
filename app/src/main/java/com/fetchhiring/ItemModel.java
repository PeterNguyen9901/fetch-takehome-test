package com.fetchhiring;

public class ItemModel {
    //Represents a data model for the items you're fetching from
    // the server. Each item has an id, listId, and a name
    private int id;
    private int listId;
    private String name;
    private boolean isHeader;


    // Default Constructor
    public ItemModel() {
    }
    public ItemModel(int id, String name, int listId) {
        this.id = id;
        this.name = name;
        this.listId = listId;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public enum ItemType {
        HEADER, ITEM
    }

    public String getName() {
        return name;
    }
    public int getListId() {
        return listId;
    }

    public static ItemModel createHeader(int listId) {
        ItemModel header = new ItemModel();
        header.listId = listId;
        header.isHeader = true;
        return header;
    }
}



