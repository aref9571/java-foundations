package day14;
import day05.JobApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;


public class FileApplicationStore {

    private final Path file;

    public FileApplicationStore(Path file){
        this.file = Objects.requireNonNull(file);
    }

    public void saveAll(List<JobApplication> applications){
        System.out.println("[INFO] [FileApplicationStore] Saving applications | " +
                "file=" + file +
                ", count=" + applications.size());
        List<String> lines = applications.stream().map(JobApplicationCsvConverter::toLine).toList();

        try {
            Files.write(file,lines, StandardCharsets.UTF_8);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to write applications to file " + file , e);
        }
    }

    public List<JobApplication> loadAll(){
        System.out.println("[DEBUG] [FileApplicationStore] Loading applications | file=" + file);
        if (!Files.exists(file)){
            return List.of();
        }

        try{
            List<String> lines = Files.readAllLines(file,StandardCharsets.UTF_8);
            System.out.println("[INFO] [FileApplicationStore] Read lines from file | " +
                    "file=" + file +
                    ", lineCount=" + lines.size());
            return lines.stream().map(line -> {
                try {
                    return JobApplicationCsvConverter.fromLine(line);
                }
                catch (RuntimeException e){
                    System.out.println("[ERROR] [FileApplicationStore] Failed to parse line | line=\"" + line + "\"");
                    throw e;
                }
            }).toList();
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read applications from file " + file , e);
        }
    }

}
