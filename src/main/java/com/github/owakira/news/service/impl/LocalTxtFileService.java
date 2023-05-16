package com.github.owakira.news.service.impl;

import com.github.owakira.news.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
@Log4j2
public class LocalTxtFileService implements FileService {
    @Value("${statistics.file-save-path}")
    private String savePath;
    private final static String FILE_FORMAT = ".txt";

    @Override
    public void saveFile(String filename, Map<String, String> data) throws IOException {
        try (FileWriter writer = new FileWriter(savePath + filename + FILE_FORMAT)) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        }
    }
}
