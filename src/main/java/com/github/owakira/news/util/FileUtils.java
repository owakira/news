package com.github.owakira.news.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static String generateUniqueFilename(String startFilename) {
        var timestamp = dateFormat.format(new Date());
        var uuid = UUID.randomUUID();
        return String.format("%s-%s-%s", startFilename, timestamp, uuid);
    }
}
