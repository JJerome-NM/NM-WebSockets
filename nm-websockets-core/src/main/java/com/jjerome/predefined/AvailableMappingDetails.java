package com.jjerome.predefined;


import java.util.UUID;

public class AvailableMappingDetails {

    private String path;
    private String regex;
    private UUID mappingID;

    public AvailableMappingDetails(String path, String regex, UUID mappingID) {
        this.path = path;
        this.regex = regex;
        this.mappingID = mappingID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public UUID getMappingID() {
        return mappingID;
    }

    public void setMappingID(UUID mappingID) {
        this.mappingID = mappingID;
    }
}
