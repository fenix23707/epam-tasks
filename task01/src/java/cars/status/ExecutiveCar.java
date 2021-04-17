package cars.status;

import cars.PassengerСar;

public class ExecutiveCar extends PassengerСar {
    public ExecutiveCar(String model, float fuelConsumption, int speed, int price) {
        super(model, fuelConsumption, speed, price);
    }

    @Override
    public String getStatus() {
        return "executive";
    }

    @Override
    public void setPrice(int price) {
        if ((price <= 3000) || (price > 6000))
            throw new IllegalArgumentException("price doesn't correspond to status of car");
        this.price = price;
    }
}
