package org.mdjarv.androidstudiobootcamp.androidstudiobootcamp.data;

import java.util.ArrayList;

public class Gist {
    private String id;
    private String owner;
    private String description;
    private ArrayList<String> files;

    public Gist() {
        this.id = "";
        this.owner = "";
        this.description = "";
        this.files = new ArrayList<String>();
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
