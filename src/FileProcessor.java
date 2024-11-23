import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class FileProcessor {

    private DuplicateCounter duplicateCounter;
    private FloorCounter floorCounter;

    public FileProcessor() {
        this.duplicateCounter = new DuplicateCounter();
        this.floorCounter = new FloorCounter();
    }

    // Метод для обработки файла
    public void processFile(String path) {
        String line;
        String delimiter = ";";

        if (path.endsWith(".xml")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(path));
                document.getDocumentElement().normalize(); // Нормализуем XML-документ

                NodeList nodeList = document.getElementsByTagName("item");

                // Обработка XML
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String city = element.getAttribute("city");
                        String street = element.getAttribute("street");

                        // Получаем атрибуты и проверяем на пустые строки
                        String houseStr = element.getAttribute("house");
                        String floorStr = element.getAttribute("floor");

                        // Преобразуем в int только если значение корректно
                        int house = 0;
                        int floor = 0;
                        try {
                            if (!houseStr.isEmpty()) {
                                house = Integer.parseInt(houseStr);
                            }
                            if (!floorStr.isEmpty()) {
                                floor = Integer.parseInt(floorStr);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Некорректные данные: house = " + houseStr + ", floor = " + floorStr);
                            continue; // Пропустить этот элемент, если данные некорректны
                        }

                        // Формируем строку для поиска дубликатов
                        String record = city + "," + street + "," + house;

                        // Подсчитываем дублирующиеся записи
                        duplicateCounter.countDuplicate(record);

                        // Подсчитываем этажи по городам
                        floorCounter.countFloors(city, floor);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (path.endsWith(".csv")) {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                while ((line = br.readLine()) != null) {
                    String[] tempValues = line.split(delimiter);

                    String city = tempValues[0];
                    String street = tempValues[1];
                    int house = 0;
                    int floor = 0;

                    try {
                        house = Integer.parseInt(tempValues[2]);
                        floor = Integer.parseInt(tempValues[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректные данные в CSV: house = " + tempValues[2] + ", floor = " + tempValues[3]);
                        continue; // Пропустить эту строку, если данные некорректны
                    }

                    // Формируем строку для поиска дубликатов
                    String record = city + "," + street + "," + house;

                    // Подсчитываем дублирующиеся записи
                    duplicateCounter.countDuplicate(record);

                    // Подсчитываем этажи по городам
                    floorCounter.countFloors(city, floor);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Формат файла не поддерживается или файл не существует.");
        }

        // Выводим результаты
        duplicateCounter.printDuplicates();
        floorCounter.printFloorCounts();
    }



public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("Введите путь до файла-справочника или '0' для завершения:");
        String filePath = scanner.nextLine();

        if (filePath.equalsIgnoreCase("0")) {
            System.out.println("Завершение программы.");
            break;
        }

        FileProcessor processor = new FileProcessor();
        long startTime = System.currentTimeMillis();
        processor.processFile(filePath);
        long endTime = System.currentTimeMillis();
        System.out.println("Время работы " + (endTime - startTime) + "ms");

    }
}
}


