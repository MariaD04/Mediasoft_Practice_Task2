import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // Массивы (Работа с парком машин)
        // Создание массива с годами выпуска втомобилей
        int[] productionYears = new int[50];
        Random random = new Random();

        for (int i = 0; i < productionYears.length; i++) {
            productionYears[i] = 2000 + random.nextInt(26); // 2000-2025
        }

        // Машины, выпущенные после 2015 года
        System.out.println("Машины, выпущенные после 2015 года:");
        for (int year : productionYears) {
            if (year > 2015) {
                System.out.println(year);
            }
        }

        // Средний возраст автомобиля
        int currentYear = java.time.Year.now().getValue();
        double sum = 0;
        for (int year : productionYears) {
            sum += currentYear - year;
        }
        double averageAge = sum / productionYears.length;
        System.out.printf("Средний возраст авто: %.1f лет%n", averageAge);

        // Коллекции (Управление моделями)
        // Список с названиями моделей машин с дубликатами
        List<String> models = new ArrayList<>(List.of("Toyota Camry", "BMW X5", "Tesla Model Y", "Toyota Camry", "BMW X5", "Tesla Model X"));

        // Список с названиями моделей машин без дубликатов и заменой Tesla на ELECTRO_CAR
        Set<String> sortedUniqueModels = models.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(m -> m.contains("Tesla") ? "ELECTRO_CAR" : m)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println("Список машин без дубликатов:");
        sortedUniqueModels.forEach(System.out::println);

        // equals/hashCode (Сравнение автомобилей)
        Set<Car> carSet = new HashSet<>();

        Car car1 = new Car("VIN123", "Camry", "Toyota", 2018, 45000, 25000, CarType.SEDAN);
        Car car2 = new Car("VIN456", "Model Y", "Tesla", 2020, 15000, 45000, CarType.ELECTRIC);
        Car car3 = new Car("VIN123", "Corolla", "Toyota", 2015, 80000, 18000, CarType.SEDAN); // Дубликат VIN
        Car car4 = new Car("VIN789", "Model X", "Tesla", 2021, 80000, 18000, CarType.ELECTRIC);

        System.out.println("Добавление car1: " + carSet.add(car1));
        System.out.println("Добавление car2: " + carSet.add(car2));
        System.out.println("Добавление car3 (дубликат VIN): " + carSet.add(car3));
        System.out.println("Добавление car4: " + carSet.add(car4));

        System.out.println("\nМашины в наборе:");
        carSet.forEach(System.out::println);

        // Сортировка по году выпуска
        List<Car> sortedCars = new ArrayList<>(carSet);
        Collections.sort(sortedCars);
        System.out.println("\nМашины, отсортированные по году выпуска (новые сначала):");
        sortedCars.forEach(System.out::println);

        // Stream API (Анализ автопарка)
        List<Car> cars = Arrays.asList(
                new Car("VIN001", "Camry", "Toyota", 2018, 45000, 25000, CarType.SEDAN),
                new Car("VIN002", "Model 3", "Tesla", 2020, 15000, 45000, CarType.ELECTRIC),
                new Car("VIN003", "Corolla", "Toyota", 2015, 80000, 18000, CarType.SEDAN),
                new Car("VIN004", "X5", "BMW", 2019, 30000, 55000, CarType.ELECTRIC),
                new Car("VIN005", "Accord", "Honda", 2017, 40000, 22000, CarType.SEDAN),
                new Car("VIN006", "Model S", "Tesla", 2021, 10000, 80000, CarType.ELECTRIC),
                new Car("VIN007", "Civic", "Honda", 2016, 60000, 20000, CarType.SEDAN)
        );

        // Фильтрация по пробегу < 50_000 км
        List<Car> lowMileageCars = cars.stream()
                .filter(car -> car.mileage < 50_000)
                .collect(Collectors.toList());
        System.out.println("Машины с пробегом < 50_000 км:");
        lowMileageCars.forEach(System.out::println);

        // Сортировка по цене (по убыванию)
        List<Car> sortedByPrice = cars.stream()
                .sorted(Comparator.comparingDouble(car -> -car.price))
                .collect(Collectors.toList());
        System.out.println("\nМашины, отсортированные по цене (по убыванию):");
        sortedByPrice.forEach(System.out::println);

        // Топ-3 самых дорогих машин
        List<Car> top3Expensive = sortedByPrice.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("\nТоп-3 самые дорогие машины:");
        top3Expensive.forEach(System.out::println);

        // Средний пробег всех машин
        double averageMileage = cars.stream()
                .mapToInt(car -> car.mileage)
                .average()
                .orElse(0);
        System.out.printf("\nСредний пробег всех машин: %.2f км%n", averageMileage);

        // Группировка по производителю
        Map<String, List<Car>> carsByManufacturer = cars.stream()
                .collect(Collectors.groupingBy(car -> car.manufacturer));
        System.out.println("\nМашины, сгруппированные по производителю:");
        carsByManufacturer.forEach((manufacturer, carList) -> {
            System.out.println(manufacturer + ":");
            carList.forEach(car -> System.out.println("  " + car));
        });

        // Автоцентр (Реализация системы)
        CarDealership dealer = new CarDealership();
        cars.forEach(dealer::addCar);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить машину\n2. Поиск по производителю\n3. Средняя цена машин определённого типа\n4. Сортировка по году выпуска\n5. Количество машин каждого типа\n6. Самая старая и новая машины\n0. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("VIN: "); String vin = scanner.nextLine();
                    System.out.print("Модель: "); String model = scanner.nextLine();
                    System.out.print("Производитель: "); String m = scanner.nextLine();
                    System.out.print("Год: "); int y = scanner.nextInt();
                    System.out.print("Пробег: "); int mil = scanner.nextInt();
                    System.out.print("Цена: "); double pr = scanner.nextDouble();
                    System.out.print("Тип машины (SEDAN, SUV, ELECTRIC...): "); scanner.nextLine();
                    CarType t = CarType.valueOf(scanner.nextLine().toUpperCase());
                    dealer.addCar(new Car(vin, model, m, y, mil, pr, t));
                    System.out.print("Машина успешно добавлена!");
                }
                case 2 -> {
                    System.out.print("Введите производителя: ");
                    String man = scanner.nextLine();
                    dealer.findByManufacturer(man).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Введите тип: ");
                    CarType type = CarType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.println("Средняя цена: " + dealer.averagePriceByType(type));
                }
                case 4 -> dealer.getSortedByYear().forEach(System.out::println);
                case 5 -> dealer.getTypeStats().forEach((k, v) -> System.out.println(k + ": " + v));
                case 6 -> {
                    System.out.println("Самая старая: " + dealer.getOldestCar());
                    System.out.println("Самая новая: " + dealer.getNewestCar());
                }
                case 0 -> System.exit(0);
            }
        }
    }
}