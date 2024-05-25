import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //create output file
        String filePath = "output.txt";
        BufferedWriter writer = createWriter(filePath);

        vmBasic basicVM = new vmBasic();
        String initFilePath = "sampleBasicInit.txt";
        String commandFilePath = "sampleBasicCommand.txt";
        try {
            // Initialize PM
            BufferedReader initBufferedReader = new BufferedReader(new FileReader(initFilePath));
            String line;
            String[] splitLine;
            line = initBufferedReader.readLine();
            splitLine = line.split(" ");
            List<String> ST = Arrays.stream(splitLine).toList();

            line = initBufferedReader.readLine();
            splitLine = line.split(" ");
            List<String> PT = Arrays.stream(splitLine).toList();
            basicVM.init(ST,PT);

            // Read Commands
            BufferedReader commandBufferedReader = new BufferedReader(new FileReader(commandFilePath));
            while ((line = commandBufferedReader.readLine()) != null) {
                splitLine = line.split(" ");

                if(splitLine[0].equals("TA")){
                    int VA = Integer.parseInt(splitLine[1]);
                    String PA = String.valueOf(basicVM.translateVA(VA));
                    writer.write(PA + " ");
                    writer.flush();
                }else if(splitLine[0].equals("RP")){
                    int PA = Integer.parseInt(splitLine[1]);
                    String val = String.valueOf(basicVM.readPA(PA));
                    writer.write(val + " ");
                    writer.flush();
                }else if(splitLine[0].equals("NL")){
                    writer.newLine();
                    writer.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BufferedWriter createWriter(String filePath){
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                // Create a new file
                file.createNewFile();
            }
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("Could not create writer");
            return null;
        }
    }
}