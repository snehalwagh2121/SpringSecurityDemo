DROP TABLE if EXISTS Users;
DROP TABLE if EXISTS Otp;
CREATE TABLE Users (`username` varchar(20) PRIMARY KEY,`password` varchar(20) NOT NULL, `role` varchar(10) NOT NULL);
CREATE TABLE Otp (`username` varchar(20) PRIMARY KEY, `otp` varchar(6));