package service;

import model.Car;
import model.Customer;
import model.Rental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Avtomobillərin, müştərilərin və kirayə əməliyyatlarının idarə olunması üçün xidmət sinfi.
 */
public class CarRentalManager {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private int nextCarId = 1;
    private int nextCustomerId = 1;

    public CarRentalManager() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();

        // Test üçün ilkin avtomobil məlumatları
        addCar("Toyota", "Corolla", 50.0);
        addCar("Hyundai", "Elantra", 55.0);
        addCar("Kia", "Optima", 65.0);
        addCar("Mercedes-Benz", "E-Class", 120.0);
        addCar("BMW", "5 Series", 130.0);
    }

    /**
     * Yeni avtomobili sistemə əlavə edir.
     */
    public Car addCar(String brand, String model, double dailyPrice) {
        Car car = new Car(nextCarId++, brand, model, dailyPrice);
        cars.add(car);
        return car;
    }

    /**
     * Bütün avtomobillərin siyahısını qaytarır.
     */
    public List<Car> getAllCars() {
        return Collections.unmodifiableList(cars);
    }

    /**
     * Bütün kirayə qeydlərinin siyahısını qaytarır.
     */
    public List<Rental> getAllRentals() {
        return Collections.unmodifiableList(rentals);
    }

    /**
     * Bütün müştərilərin siyahısını qaytarır.
     */
    public List<Customer> getAllCustomers() {
        return Collections.unmodifiableList(customers);
    }

    /**
     * ID-yə əsasən avtomobili tapır.
     */
    public Car findCarById(int carId) {
        for (Car car : cars) {
            if (car.getId() == carId) {
                return car;
            }
        }
        return null;
    }

    /**
     * Avtomobili müştəriyə kirayələyir.
     */
    public Rental rentCar(int carId, Customer customer, int days) {
        Car car = findCarById(carId);

        if (car == null) {
            throw new IllegalArgumentException("Seçilmiş ID ilə maşın tapılmadı: " + carId);
        }
        if (!car.isAvailable()) {
            throw new IllegalStateException("Bu maşın artıq kirayədədir!");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("Kirayə günü ən azı 1 gün olmalıdır!");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Müştəri məlumatları boş ola bilməz!");
        }

        // Əgər müştərinin ID-si təyin olunmayıbsa təyin edirik
        if (customer.getId() <= 0) {
            customer.setId(nextCustomerId++);
        }
        if (!customers.contains(customer)) {
            customers.add(customer);
        }

        // Avtomobili kirayədə kimi işarələyirik
        car.setAvailable(false);

        // Yeni kirayə obyekti yaradılır və qeydə alınır
        Rental rental = new Rental(car, customer, days);
        rentals.add(rental);

        return rental;
    }

    /**
     * Sadələşdirilmiş parametrli kirayələmə metodu.
     */
    public Rental rentCar(int carId, String customerName, String customerPhone, int days) {
        Customer customer = new Customer(nextCustomerId++, customerName, customerPhone);
        return rentCar(carId, customer, days);
    }

    /**
     * Kirayədə olan avtomobili geri qaytarır.
     */
    public boolean returnCar(int carId) {
        Car car = findCarById(carId);
        if (car != null && !car.isAvailable()) {
            car.setAvailable(true);
            return true;
        }
        return false;
    }
}
