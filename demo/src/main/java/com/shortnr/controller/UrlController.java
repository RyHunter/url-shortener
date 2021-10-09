package com.shortnr.controller;

import com.shortnr.service.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {
    @Autowired
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(path = "shorten", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String convertToShortUrl(@RequestBody String request) {
        return urlService.saveOrUpsertShortUrl(request);
    }

    @GetMapping(value = "/getOriginal/{shortUrl}")
    public String getOriginal(@PathVariable String shortUrl) {
        // String a = shortUrl.replace("\"", "");
        return urlService.getOriginal(shortUrl);
    }

    @GetMapping(value = "/getNumTimesUsed/{shortUrl}")
    public Integer getNumTimesUsed(@PathVariable String shortUrl) {
        return urlService.getNumTimesUsed(shortUrl);
    }
}
