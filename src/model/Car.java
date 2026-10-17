package model;

/**
 * Avtomobil məlumatlarını və mövcudluq statusunu saxlayan model sinfi.
 */
public class Car {
    private int id;
    private String brand;
    private String model;
    private double dailyPrice;
    private boolean isAvailable;

    public Car(int id, String brand, String model, double dailyPrice) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isAvailable = true; // Yeni avtomobil ilkin olaraq boşdur
    }

    // Encapsulation: Getter və Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return brand + " " + model + " (" + dailyPrice + " AZN/gün)";
    }
}
