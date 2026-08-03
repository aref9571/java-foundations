package day14;
import static org.junit.jupiter.api.Assertions.*;

import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


class FileApplicationStoreTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAllAndLoadAll_roundTrip_multipleApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().build();
        Path file = tempDir.resolve("Applications.txt");

        FileApplicationStore store = new FileApplicationStore(file);

        store.saveAll(List.of(app1,app2));

        FileApplicationStore otherStore = new FileApplicationStore(file);
        List<JobApplication> loaded = otherStore.loadAll();

        assertEquals(2, loaded.size());
        assertTrue(loaded.contains(app1));
        assertTrue(loaded.contains(app2));
    }

    @Test
    void loadAll_returnsEmptyList_whenFileDoesNotExist(){
        Path file = tempDir.resolve("noneExistence.txt");
        FileApplicationStore store = new FileApplicationStore(file);
        List<JobApplication> loaded = store.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveAll_withEmptyList_createsEmptyFile_andLoadAllReturnsEmptyList()throws Exception{
        Path file = tempDir.resolve("applications-empty.txt");
        FileApplicationStore store = new FileApplicationStore(file);
        store.saveAll(List.of());
        assertTrue(Files.exists(file));
        List<JobApplication> loaded = store.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveAll_createsMissingParentDirectories(){
        Path file = tempDir.resolve("nested").resolve("applications.txt");
        FileApplicationStore store = new FileApplicationStore(file);

        store.saveAll(List.of(JobApplicationBuilder.aDefaultApplication().build()));

        assertTrue(Files.exists(file));
    }


}
