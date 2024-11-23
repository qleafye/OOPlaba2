import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите путь до файла или 0 для завершения работы: ");
            String filename = sc.nextLine();
            if (filename.equals("0")) {
                break;
            }
            else{
                long startTime = System.currentTimeMillis();
                FileProcessor processor = new FileProcessor();
                processor.processFile(filename);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);

            }
        }
    }
}