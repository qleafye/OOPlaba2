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
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("item");

                // Обработка XML
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String city = element.getAttribute("city");
                        String street = element.getAttribute("street");
                        int house = Integer.parseInt(element.getAttribute("house"));
                        int floor = Integer.parseInt(element.getAttribute("floor"));

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
                    int house = Integer.parseInt(tempValues[2]);
                    int floor = Integer.parseInt(tempValues[3]);

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


}

