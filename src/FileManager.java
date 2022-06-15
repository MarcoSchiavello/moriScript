import exeptions.InvalidFileException;

import java.io.*;

public class FileManager {
    private File filePath;

    public FileManager(String fileName) {
        this.filePath = new File(fileName);

        if (!this.filePath.exists())
            throw new InvalidFileException("No such file or directory in " + this.filePath.toString());
    }

    public String readString() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(this.filePath))) {
            String fileString = "";
            String line;
            while((line = fileReader.readLine()) != null) {
                fileString += (line + "\n");
            }

            return fileString;
        } catch (IOException e) {
            return null;
        }
    }
}
