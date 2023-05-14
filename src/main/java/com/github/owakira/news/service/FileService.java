package com.github.owakira.news.service;

import java.io.IOException;
import java.util.Map;

public interface FileService {
    void saveFile(String path, Map<String, String> data) throws IOException;
}
