package service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import static service.ConverterFiles.*;

public class FilesDir {


    public void listFiles(String dir) {
        File folder = new File(dir);
        String dirName = addDir(dir);
        StringBuilder text = new StringBuilder("");
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {

                long start = System.currentTimeMillis();
                String fileExtension = getFileExtension(file);
                if ("yaml".equals(fileExtension)) {
                    String resConvert = ConverterJSON(dir, file.getName(), dirName);
                    if (!resConvert.equals("")) {
                        long finish = System.currentTimeMillis();
                        long duration = finish - start;
                        String fileNameNew = file.getName().substring(0, file.getName().indexOf(".")) + ".json";
                        File fileNew = new File(dirName + "/" + fileNameNew);

                        text.append(file.getName()).append(" -> ").append(fileNameNew).append(" -> ")
                                .append(duration).append("s").append(" -> size ").append(file.length())
                                .append("kb").append(" -> ").append(fileNew.length()).append("kb").append("\n");
                    } else {
                        text.append(file.getName()).append(" -> ").append("file is invalid").append("\n");
                    }
                } else if ("json".equals(fileExtension)) {
                    String resConvert = ConverterYAML(dir, file.getName(), dirName);
                    if (!resConvert.equals("")) {
                        long finish = System.currentTimeMillis();
                        long duration = finish - start;
                        String fileNameNew = file.getName().substring(0, file.getName().indexOf(".")) + ".yaml";
                        File fileNew = new File(dirName + "/" + fileNameNew);

                        text.append(file.getName()).append(" -> ").append(fileNameNew).append(" -> ")
                                .append(duration).append("s").append(" -> size ").append(file.length())
                                .append("kb").append(" -> ").append(fileNew.length()).append("kb").append("\n");
                    } else {
                        text.append(file.getName()).append(" -> ").append("file is invalid").append("\n");
                    }
                }
            }
        }
        logFiles(dir, text.toString());
    }

    public String addDir(String dir) {
        String dirName = dir + "/converted";
        Path path = Paths.get(dirName);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dirName;
    }

    public void logFiles(String dir, String result) {
        String fileName = dir + "/" + "result.log";
        File folder = new File(fileName);
        writeToFile(folder, result);
        toString(folder);
    }



    public void toString(File file) {

        StringBuilder str = new StringBuilder();
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNext()) {
                str.append(sc.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(String.valueOf(str));
    }

}
