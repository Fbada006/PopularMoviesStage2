package com.disruption.popularmovies.model.trailer;

public class Trailer {

    private String id;

    private String name;

    private String site;

    private String size;

    private String type;

    public Trailer(String id, String name, String site, String size, String type) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public Trailer() {
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
