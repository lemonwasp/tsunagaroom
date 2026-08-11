-- =========================================
-- connect_room DB initialization
-- WARNING: existing connect_room database will be dropped.
-- =========================================

DROP DATABASE IF EXISTS connect_room;

CREATE DATABASE connect_room
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE connect_room;

-- user
CREATE TABLE `user` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(50)
);

-- profile_type: 1 = senior, 2 = family
CREATE TABLE profile (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  name VARCHAR(50),
  profile_type INT,
  FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- video_type: 1 = normal video, 2 = reaction video
-- is_read: 0 = unread, 1 = read
CREATE TABLE video (
  id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT NOT NULL,
  video_type INT NOT NULL,
  created_at DATETIME,
  is_read INT DEFAULT 0,
  file_path VARCHAR(255),
  FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE startup_sound (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  file_path VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `user`(id)
);

CREATE TABLE location (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  latitude DOUBLE,
  longitude DOUBLE,
  address VARCHAR(255),
  created_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES `user`(id)
);

-- notification_type: 1 = startup notification, 2 = video notification
CREATE TABLE notification (
  id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT NOT NULL,
  notification_type INT,
  FOREIGN KEY (profile_id) REFERENCES profile(id)
);

-- Minimal demo data. Replace or remove for your own environment.
INSERT INTO `user` (email, password, name)
VALUES ('demo@example.com', 'demo-only', 'Demo User');

INSERT INTO profile (user_id, name, profile_type)
VALUES
  (1, 'Senior Demo', 1),
  (1, 'Family Demo', 2);

SELECT * FROM `user`;
SELECT * FROM profile;
SELECT * FROM video;
SELECT * FROM startup_sound;
SELECT * FROM location;
SELECT * FROM notification;
