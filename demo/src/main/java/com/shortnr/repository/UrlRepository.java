package com.shortnr.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shortnr.model.UrlEntity;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {
  Optional<UrlEntity> findByLongUrl(String longUrl);

  Optional<UrlEntity> findByShortUrl(String shortUrl);
}
