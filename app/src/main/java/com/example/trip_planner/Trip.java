package com.example.trip_planner;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {
    private String destination;
    private String description;
    private int imageID;
    private double budgetPerIndividual;
    private String category;
    private boolean isSaved;
    private boolean isBooked;
    private Date date;
    private static int serialNumber = 0;
    private final int id;

    public Trip(String destination, String description, int imageID, double budgetPerIndividual, String category, boolean isSaved, boolean isBooked, Date date) {
        this.destination = destination;
        this.description = description;
        this.imageID = imageID;
        this.budgetPerIndividual = budgetPerIndividual;
        this.category = category;
        this.isSaved = isSaved;
        this.isBooked = isBooked;
        this.date = date;

        this.id = this.serialNumber;
        serialNumber ++;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public double getBudgetPerIndividual() {
        return budgetPerIndividual;
    }

    public void setBudgetPerIndividual(double budgetPerIndividual) {
        this.budgetPerIndividual = budgetPerIndividual;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean isSaved){
        this.isSaved = isSaved;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked){
        this.isBooked = isBooked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object trip){
        if(trip instanceof Trip)
            return ((Trip) trip).id == this.id;

        return false;
    }
}
