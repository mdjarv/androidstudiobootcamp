package org.mdjarv.androidstudiobootcamp.androidstudiobootcamp.data;

public class Gist {
    /**
     * Simple data model for a Gist
     */
    private String id;
    private String owner;
    private String description;

    public Gist() {
        this.id = "";
        this.owner = "";
        this.description = "";
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
