CREATE TABLE IF NOT EXISTS  asinfo (
	id INT PRIMARY KEY AUTO_INCREMENT,
	number INT NOT NULL,
	name VARCHAR(255),
	description VARCHAR(max),
	country VARCHAR(3)
);

CREATE TABLE IF NOT EXISTS  asproperties (
	id INT PRIMARY KEY AUTO_INCREMENT,
	asinfo INT,
	name VARCHAR(255),
	description VARCHAR(max),
	FOREIGN KEY (asinfo) REFERENCES asinfo(id)
);
