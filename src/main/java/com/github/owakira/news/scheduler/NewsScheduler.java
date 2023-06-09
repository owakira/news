package com.github.owakira.news.scheduler;

import com.github.owakira.news.service.FileService;
import com.github.owakira.news.service.NewsService;
import com.github.owakira.news.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class NewsScheduler {
    private final NewsService newsService;
    private final FileService fileService;

    @Value("${statistics.file-start-name}")
    private String fileStartName;


    private final static String CRON = "0 0 0 * * ?";

    @Scheduled(cron = CRON)
    public void collectAnalytics() {
        log.info("Collect analytics");

        var news = newsService.getNewsCountBySource();
        log.info("Found news by source: [news={}]", news);

        var count = new HashMap<String, String>();
        news.forEach((source, value) -> count.put(source.getId().toString(), value.toString()));

        var filename = FileUtils.generateUniqueFilename(fileStartName);
        log.info("Save analytics: [filename={}]", filename);

        try {
            fileService.saveFile(filename, count);
            log.info("File saved successfully: [filename={}]", filename);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
