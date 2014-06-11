package org.mdjarv.androidstudiobootcamp.androidstudiobootcamp.data;

import java.util.ArrayList;

public class Gist {
    private String owner;
    private String description;
    private ArrayList<String> files;

    public Gist() {
        this("", "");
    }

    public Gist(String owner, String description) {
        this(owner, description, new ArrayList<String>());
    }

    public Gist(String owner, String description, ArrayList<String> files) {
        this.owner = owner;
        this.description = description;
        this.files = files;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
