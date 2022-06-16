import exceptions.InvalidFileException;

import java.io.*;

public class FileManager {
    private File filePath;

    public FileManager(String fileName) {
        this.filePath = new File(fileName);

        if (!this.filePath.exists() || !this.filePath.isFile() || !this.filePath.canRead())
            throw new InvalidFileException("Impossible to open or read the file indicated" + this.filePath.toString());
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
