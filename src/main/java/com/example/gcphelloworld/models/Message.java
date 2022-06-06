package com.example.gcphelloworld.models;

import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import com.google.spanner.v1.TypeCode;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "messages")
@Data
public class Message {
    @PrimaryKey
    @Column(name = "message_id")
    private String messageId;

    private String message;

    @Column(name = "creation_timestamp", spannerType = TypeCode.TIMESTAMP)
    private LocalDateTime creationTimestamp;
}
