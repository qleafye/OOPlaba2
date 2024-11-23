import java.util.*;

public class DuplicateCounter {

    private Map<String, Integer> duplicates;

    public DuplicateCounter() {
        this.duplicates = new HashMap<>();
    }

    // Метод для подсчета дубликатов
    public void countDuplicate(String record) {
        duplicates.put(record, duplicates.getOrDefault(record, 0) + 1);
    }

    // Метод для вывода дублирующихся записей
    public void printDuplicates() {
        System.out.println("Дублирующиеся записи:");
        for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " - " + entry.getValue() + " раз(а)");
            }
        }
    }
}
