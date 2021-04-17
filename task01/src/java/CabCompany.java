

import cars.Car;
import cars.PassengerСar;
import cars.filters.Filter;

import java.util.*;

public class CabCompany {
    private String name;
    private ArrayList<PassengerСar> carPark;

    public CabCompany(String name) {
        setName(name);
        carPark = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<PassengerСar> getCarPark() {
        return carPark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        int price = 0;
        for (Car car : getCarPark()) {
            price += car.getPrice();
        }
        return price;
    }

    public void sort(Comparator<Car> comparator) {
        Collections.sort(carPark, comparator);
    }

    public ArrayList<PassengerСar> find(Filter filter) {
        ArrayList<PassengerСar> passengerCars = new ArrayList<PassengerСar>();

        for (PassengerСar car: getCarPark()) {
            if (filter.check(car)) {
                passengerCars.add(car);
            }
        }

        return passengerCars;
    }

    public void addCar(PassengerСar car) {
        carPark.add(car);
    }
}
