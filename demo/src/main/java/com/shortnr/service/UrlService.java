package com.shortnr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import com.shortnr.model.UrlEntity;
import com.shortnr.repository.UrlRepository;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /*
     * Check if a short link already exists. 
     * If not, save it to the db 
     * input: long URL 
     * output: short URL
     */
    public String saveOrUpsertShortUrl(String longUrl) {
        Optional<UrlEntity> retreived = urlRepository.findByLongUrl(longUrl);
        UrlEntity newUrl = null;

        if (!retreived.isPresent()) {
            newUrl = new UrlEntity();
            newUrl.setLongUrl(longUrl);
            newUrl.setShortUrl(encode(newUrl.getId()));

            urlRepository.save(newUrl);
        } else {
            newUrl = retreived.get();
        }

        return newUrl.getShortUrl();
    }

    /*
     * Get an original long URL back 
     * input: short URL 
     * output: original URL
     */
    public String getOriginal(String shortUrl) {
        Optional<UrlEntity> retreived = urlRepository.findByShortUrl(shortUrl);

        if (!retreived.isPresent()) {
            return "This link doesn't exist";
        } else {
            UrlEntity originalUrl = retreived.get();
            Integer counter = originalUrl.getCounter();
            originalUrl.setCounter(counter + 1);
            urlRepository.save(originalUrl);

            return originalUrl.getLongUrl();
        }
    }

    /*
     * Number of times a link has been used 
     * input: short URL link 
     * output: number of
     * times used
     */
    public Integer getNumTimesUsed(String shortUrl) {
        Optional<UrlEntity> retreived = urlRepository.findByShortUrl(shortUrl);

        if (!retreived.isPresent()) {
            return 0;
        } else {
            return retreived.get().getCounter();
        }
    }

    /*
     * Generate a new url 
     * input: long URL link 
     * output: short url link
     */
    private final static int SHORTURL_LENGTH = 6;
    private Random rand = new Random();

    public String encode(String longUrl) {
        StringBuilder shortURL = new StringBuilder();

        for (int i = 0; i < SHORTURL_LENGTH; ++i) {
            int r = rand.nextInt(62);
            if (r < 10) {
                shortURL.append(r);
            } else if (r < 36) {
                shortURL.append((char) (r - 10 + 'a'));
            } else {
                shortURL.append((char) (r - 36 + 'A'));
            }
        }
        return shortURL.toString();
    }
}
