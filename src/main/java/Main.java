import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        vmBasic basicVM = new vmBasic();
        String filePath = "basicInit.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] splitLine;
            line = bufferedReader.readLine();
            splitLine = line.split(" ");
            List<String> ST = Arrays.stream(splitLine).toList();
            line = bufferedReader.readLine();
            splitLine = line.split(" ");
            List<String> PT = Arrays.stream(splitLine).toList();
            basicVM.init(ST,PT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}