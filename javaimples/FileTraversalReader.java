package javaimples;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileTraversalReader {

    static void traverse(File file) throws Exception {
        if (file.isDirectory()) {
            System.out.println("Directory: " + file.getAbsoluteFile());
            for (File f : file.listFiles()) {
                traverse(f);
            }
        } else if (file.getName().endsWith(".txt")) {
            System.out.println("TextFile: " + file.getAbsoluteFile());
            readFile(file);
        }
    }

    static void readFile(File file) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        traverse(new File("/Users/kaushalkumar/Desktop/test"));
    }
}
