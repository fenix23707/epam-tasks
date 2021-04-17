
import cars.Car;
import cars.PassengerСar;
import cars.comparators.FuelConsumptionComparator;
import cars.filters.Filter;
import cars.filters.SpeedFilter;
import cars.status.BudgetСar;
import cars.status.ExecutiveCar;
import cars.status.MediumCar;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private CabCompany cabCompany;
    private ArrayList<PassengerСar> budgetCars;
    private ArrayList<PassengerСar> mediumCars;
    private ArrayList<PassengerСar> executiveCars;

    public void start() {
        cabCompany = createCabCompany();
        updateListAvailableCars();
        mainMenu();
    }

    private void mainMenu() {
        int choose;
        String[] actionList = {"Список", "Сортировать", "Добавить",
                "Общая стоимость", "Найти автомобиль", "Выход"};

        while (true) {
            printActionsList(actionList);
            choose = scanner.nextInt();

            if(choose == 0) {
                printListCars(cabCompany.getCarPark());
            }
            if(choose == 1) {
                sortMenu();
            }
            if(choose == 2) {
                addCarMenu();
            }
            if(choose == 3) {
                System.out.println("Общая стоимость: " + cabCompany.getPrice());
            }
            if(choose == 4) {
                try {
                    findCarMenu();
                } catch (IllegalArgumentException ex) {
                    System.out.println("Не удалось найти автомобиль");
                }
            }
            if(choose == actionList.length-1) {
                return;
            }
        }
    }

    private void printActionsList(String[] actionList) {
        int num = 0;
        for (String act: actionList) {
            System.out.println(num + " - " + act);
            num++;
        }
    }

    private CabCompany createCabCompany() {
        System.out.print("Ввдите название таксопарка: ");
        CabCompany cabCompany = new CabCompany(scanner.next());
        return cabCompany;
    }

    private void printListCars(ArrayList<PassengerСar> cars) {
        if(cars.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private void sortMenu() {
        String[] actionList = {" сортировка по расходу топлива"};
        printActionsList(actionList);
        int choose = scanner.nextInt();

        if(choose == 0) {
            cabCompany.sort(new FuelConsumptionComparator());
            return;
        }
    }

    private void addCarMenu() {
        System.out.println("Выберите статус автомобиля: ");
        String[] actionList = {"budget","medium","executive","выход"};
        while(true) {
            printActionsList(actionList);
            int choose = scanner.nextInt();
            if (choose == 0) {
                addCar(budgetCars);
            }
            if (choose == 1) {
                addCar(mediumCars);
            }
            if (choose == 2) {
                addCar(executiveCars);
            }
            if (choose == 3) {
                return;
            }
        }
    }

    private void addCar(ArrayList<PassengerСar> cars) {
        if (cars.isEmpty()) {
            System.out.println("Автомобилей данного классса нет в наличии");
            return ;
        }
        System.out.println("Доступные автомобили: ");
        printListCars(cars);

        System.out.println("Введите число от 0 до " + (cars.size()-1) + ", чтобы добавить.");
        int index = scanner.nextInt();
        if ((index < 0) || (index >= cars.size())) {
            System.out.println("не удалось добавить автомобиль.");
            return;
        }
        cabCompany.addCar(cars.get(index));
    }

    private void findCarMenu() {
        int leftSpeed, rightSpeed;

        System.out.print("Введите левый диапазон скорости: ");
        leftSpeed = scanner.nextInt();

        System.out.print("Введите правый диапазон скорости: ");
        rightSpeed = scanner.nextInt();

        Filter filter = new SpeedFilter(leftSpeed,rightSpeed);
        printListCars(cabCompany.find(filter));
    }

    private void updateListAvailableCars() {
        // TODO: вместо массивов сделать чтение из БД
        budgetCars = new  ArrayList<PassengerСar>();
        budgetCars.add(new BudgetСar("Lada Granta",9.9f,176,277));
        budgetCars.add(new BudgetСar("Chery QQ",8.2f,144,200));

        mediumCars = new  ArrayList<PassengerСar>();
        mediumCars.add(new MediumCar("Audi A4",5.8f,220,1380));

        executiveCars = new  ArrayList<PassengerСar>();
        executiveCars.add(new ExecutiveCar("Rimac Concept Two",13.8f,415,5500));
    }
}
