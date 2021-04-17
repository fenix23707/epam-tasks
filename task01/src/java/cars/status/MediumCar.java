package cars.status;

import cars.PassengerСar;

public class MediumCar extends PassengerСar {

    public MediumCar(String model, float fuelConsumption, int speed, int price) {
        super(model, fuelConsumption, speed, price);
    }

    @Override
    public String getStatus() {
        return "medium";
    }

    @Override
    public void setPrice(int price) {
        if ((price<=1000) || (price>3000)) {
            throw new IllegalArgumentException("price doesn't correspond to status of car");
        }
        this.price = price;
    }
}
