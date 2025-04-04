package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class Reader {
    public static String readFileAsString(String filePath) throws IOException {
    	
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
