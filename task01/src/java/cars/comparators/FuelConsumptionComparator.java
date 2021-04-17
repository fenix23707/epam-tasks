package cars.comparators;

import cars.Car;

import java.util.Comparator;

public class FuelConsumptionComparator implements Comparator<Car> {

    @Override
    public int compare(Car o1, Car o2) {
        return Float.compare(o1.getFuelConsumption(), o2.getFuelConsumption());
    }
}
