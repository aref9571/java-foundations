package day14;
import day05.JobApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileApplicationStore {

    private final Path file;

    public FileApplicationStore(Path file){
        this.file = Objects.requireNonNull(file);
    }

    public void saveAll(List<JobApplication> applications){
        List<String> lines = applications.stream().map(JobApplicationCsvConverter::toLine).toList();

        try {
            Files.write(file,lines, StandardCharsets.UTF_8);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to write applications to file " + file , e);
        }
    }

    public List<JobApplication> loadAll(){
        if (!Files.exists(file)){
            return List.of();
        }

        try{
            List<String> lines = Files.readAllLines(file,StandardCharsets.UTF_8);
            return lines.stream().map(JobApplicationCsvConverter::fromLine).toList();
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read applications from file " + file , e);
        }
    }

}
