package model;

/**
 * Avtomobil və müştəri arasındakı kirayə müqaviləsini əks etdirən model sinfi (Kompozisiya).
 */
public class Rental {
    private Car car;
    private Customer customer;
    private int rentalDays;
    private double totalPrice;

    public Rental(Car car, Customer customer, int rentalDays) {
        this.car = car;
        this.customer = customer;
        this.rentalDays = rentalDays;
        this.totalPrice = calculateTotalPrice();
    }

    // Qiymətin (gün sayı * günlük qiymət) hesablanması metodu
    public double calculateTotalPrice() {
        return this.rentalDays * this.car.getDailyPrice();
    }

    // Encapsulation: Getter metodları
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
