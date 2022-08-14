package ru.embedika.dto;

public class SortOfCarsDto {
    private String direction;
    private String property;
    private boolean ignoreCase;
    private String nullHandling;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }

    public SortOfCarsDto(String direction, String property, boolean ignoreCase, String nullHandling) {
        this.direction = "ASC";
        if (direction != null) {
            this.direction = direction.trim();
        }
        if (property != null) {
            this.property = property.trim();
        }
        this.ignoreCase = ignoreCase;
        if (nullHandling != null) {
            this.nullHandling = nullHandling.trim().toUpperCase();
        }
    }
}
