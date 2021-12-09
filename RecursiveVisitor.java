import java.io.*;
import java.util.Scanner;

public class RecursiveVisitor {
    private static final String FILE_NAME = "joke.java";

    public static void createFileJoke(String path) {
        try(FileWriter writer = new FileWriter(path)) {
            writer.write(String.format("%s\n\t%s\n\t\t%s\n\t%s\n%s", 
                                        "public class joke {",
                                        "public static void main(String[] args) {",
                                        "System.out.println(\"hello world\");",
                                        "}",
                                        "}"));
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void treeFiles(File filePath) {
        File[] files = filePath.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                createFileJoke(String.format("%s%s%s", file.getPath(), File.separator, FILE_NAME));
                treeFiles(file);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter the location of the file");
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);

        if (file.exists() && file.isDirectory()) {
            treeFiles(file);
        } else if (file.exists() && file.isFile()) {
            System.out.println(file.getPath() + ": This file is not a directory");
        } else {
            System.out.println(file.getPath() + ": No directory / file found");
        }
    }
}