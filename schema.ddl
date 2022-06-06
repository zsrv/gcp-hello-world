CREATE TABLE messages (
    message_id STRING(36) NOT NULL,
    message STRING(255) NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL
) PRIMARY KEY (message_id);
