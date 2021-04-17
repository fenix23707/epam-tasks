package cars;

public interface Car {
    String getModel();
    String getType();
    float getFuelConsumption();
    int getSpeed();
    int getPrice();

    void setPrice(int price);
}
