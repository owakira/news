package com.github.owakira.news.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static String generateUniqueFilename(String startFilename, String fileFormat) {
        var timestamp = dateFormat.format(new Date());
        var uuid = UUID.randomUUID();
        return String.format("%s-%s-%s%s", startFilename, timestamp, uuid, fileFormat);
    }
}