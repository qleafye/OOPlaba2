import java.util.*;

public class FloorCounter {

    private Map<String, int[]> floorsCount;

    public FloorCounter() {
        this.floorsCount = new HashMap<>();
    }

    // Метод для подсчета этажей
    public void countFloors(String city, int floor) {
        floorsCount.putIfAbsent(city, new int[5]);  // 5 элементов для этажей от 1 до 5
        int[] cityFloors = floorsCount.get(city);

        if (floor >= 1 && floor <= 5) {
            cityFloors[floor - 1]++;  // Увеличиваем счетчик для соответствующего этажа
        }
    }

    // Метод для вывода количества этажей по городам
    public void printFloorCounts() {
        System.out.println("\nКоличество этажей по городам:");
        for (Map.Entry<String, int[]> entry : floorsCount.entrySet()) {
            String city = entry.getKey();
            int[] floors = entry.getValue();
            System.out.println("Город: " + city);
            for (int i = 0; i < floors.length; i++) {
                if (floors[i] > 0) {
                    System.out.println((i + 1) + " этажей: " + floors[i]);
                }
            }
        }
    }
}

