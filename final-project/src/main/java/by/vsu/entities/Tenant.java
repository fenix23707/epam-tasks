package by.vsu.entities;

/**
 * Класс Tenant хранит необходимую информацию о жильцах этой системы.
 *
 * @author Kovzov Vladislav
 */
public class Tenant extends User{
    private String address;

    private int apartmentNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "address: " + address + "apartmentNumber: " + apartmentNumber;
    }
}
