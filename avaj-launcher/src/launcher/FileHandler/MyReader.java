package launcher.FileHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyReader {

    private final BufferedReader bufferedReader;
    public MyReader(String pathToFile) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(pathToFile));
    }

    public String[] getRow() throws IOException {
        String line = bufferedReader.readLine();
        if (line == null)
            return null;
        return line.split(" ");
    }

    public void closeFile() throws IOException {
        bufferedReader.close();
    }
}
