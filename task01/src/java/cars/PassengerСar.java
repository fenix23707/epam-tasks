package cars;



abstract public class PassengerСar implements Car {
    protected String model;
    protected float fuelConsumption;
    protected int speed;
    protected int price;

    public PassengerСar(String model, float fuelConsumption, int speed, int price) {
        setModel(model);
        setFuelConsumption(fuelConsumption);
        setSpeed(speed);
        setPrice(price);
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getType() {
        return "passenger";
    }

    @Override
    public float getFuelConsumption() {
        return fuelConsumption;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getPrice() {
        return price;
    }

    abstract public String getStatus();

    protected void setModel(String model) {
        this.model = model;
    }

    protected void setFuelConsumption(float fuelConsumption) {
        if ((fuelConsumption < 3) || (fuelConsumption > 25)) {
            throw new IllegalArgumentException("fuelConsumption:" + fuelConsumption + " is incorrect");
        }
        this.fuelConsumption = fuelConsumption;
    }

    protected void setSpeed(int speed) {
        if ((speed < 1) || (speed > 600)) {
            throw new IllegalArgumentException("speed " + speed + " is incorrect");
        }
        this.speed = speed;
    }

    @Override
    public String toString() {
        return     "type = " + getType()
                + " | status = " + getStatus()
                + " | model=" + getModel()
                + " | fuelConsumption=" + fuelConsumption
                + " | speed=" + speed
                + " | price=" + price;
    }
}
