import java.io.*;
import Bank.Bank;
import PhysicalPerson.PhysicalPerson;

public class IOFile {

    private static final String DIR_FINANCE = "Finance";


    public static void write(String pathToFile, String text, boolean append) {

        try {
            File file = new File(pathToFile);
            FileWriter fileWriter = new FileWriter(file, append);
            fileWriter.write(text + "\n");
            fileWriter.close();
        } catch (IOException ioEx) {
            System.out.println("Не удалось записать в файл " + ioEx.getMessage());
        }
    }

    public static String reader(String pathToFile) {

        StringBuffer stringBuffer = new StringBuffer();

        try {
            File file = new File(pathToFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            reader.close();
        } catch (FileNotFoundException fileNotFoundEx) {
            System.out.println("Файл не найден " + fileNotFoundEx.getMessage());
        } catch (IOException ioEx) {
            System.out.println("Не удалось прочитать файл " + ioEx.getMessage());
        }

        return stringBuffer.toString();
    }

    public String getPathToTransactionHistoryFile() {
        File dirFinance = new File(DIR_FINANCE);
        if (!dirFinance.exists()) dirFinance.mkdir();
        String fileName = bank.getBankName() + "_" + physicalPerson.getFirstName() + "_" + physicalPerson.getLastName() + ".txt";
        String pathToTransactionHistoryFile = DIR_FINANCE + File.separator + fileName;
        return pathToTransactionHistoryFile;
    }

}
