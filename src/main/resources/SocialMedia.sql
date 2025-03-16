DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS ACCOUNT; 

CREATE TABLE ACCOUNT ( 
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE MESSAGE ( 
    message_id INT PRIMARY KEY AUTO_INCREMENT,
    posted_by INT,
    message_text VARCHAR(255),
    time_posted_epoch BIGINT,
    FOREIGN KEY (posted_by) REFERENCES Account(account_id) 
);

INSERT INTO ACCOUNT (username, password) VALUES ('testuser1', 'password'); 
INSERT INTO MESSAGE (posted_by, message_text, time_posted_epoch) VALUES (1, 'test message 1', 1669947792); 