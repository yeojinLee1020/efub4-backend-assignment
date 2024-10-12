/* data.sql */
CREATE TABLE IF NOT EXISTS account (
account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(60) NOT NULL,
encode_password VARCHAR(255) NOT NULL,
nickname VARCHAR(16) NOT NULL,
bio VARCHAR(255),
status VARCHAR(255) NOT NULL
);

INSERT INTO member (email, encode_password, nickname, student_id, university,  status) VALUES
('test@example.com', 'encodedPassword123', 'TestNickname','2112123', 'OOcollege', 'REGISTERED');