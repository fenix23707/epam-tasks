package cars.filters;

import cars.Car;

public class SpeedFilter implements Filter{
    private int speedLeft;
    private int speedRight;

    public  SpeedFilter(int speedLeft, int speedRight) {
        setSpeed(speedLeft, speedRight);
    }

    private void setSpeed(int speedLeft, int speedRight) {
        if((speedLeft<=0) || (speedRight<=0) || (speedLeft>speedRight)) {
            throw new IllegalArgumentException("speeds is incorrect");
        }
        this.speedLeft = speedLeft;
        this.speedRight = speedRight;
    }

    @Override
    public boolean check(Car car) {
        return (speedLeft <= car.getSpeed()) && (car.getSpeed() <= speedRight);
    }
}
