package cars.status;

import cars.*;

public class BudgetСar extends PassengerСar {

    public BudgetСar(String model, float fuelConsumption, int speed, int price) {
        super(model, fuelConsumption, speed, price);
    }

    @Override
    public String getStatus() {
        return "budget";
    }

    @Override
    public void setPrice(int price) {
        if ((price <= 100) || (price > 1000))
            throw new IllegalArgumentException("price doesn't correspond to status of car");
        this.price = price;
    }
}
