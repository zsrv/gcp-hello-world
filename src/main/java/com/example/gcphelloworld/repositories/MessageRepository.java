package com.example.gcphelloworld.repositories;

import com.example.gcphelloworld.models.Message;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends SpannerRepository<Message, String> {
    /**
     * @return the most recently added Message
     */
    Message findFirstByOrderByCreationTimestampDesc();
}
