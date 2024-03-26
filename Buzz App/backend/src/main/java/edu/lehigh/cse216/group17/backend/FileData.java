package edu.lehigh.cse216.group17.backend;

public class FileData {
    private int id;
    private String name;
    private String link;

    public FileData(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}

