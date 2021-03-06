CREATE TABLE IF NOT EXISTS  asinfo (
	id INT PRIMARY KEY AUTO_INCREMENT,
	number INT NOT NULL,
	country VARCHAR(3),
	rir VARCHAR(3)
);

CREATE TABLE IF NOT EXISTS  asproperties (
	id INT PRIMARY KEY AUTO_INCREMENT,
	asinfo INT,
	name VARCHAR(255),
	description VARCHAR(max),
	FOREIGN KEY (asinfo) REFERENCES asinfo(id)
);

CREATE TABLE IF NOT EXISTS  asconnection (
	id INT PRIMARY KEY AUTO_INCREMENT,
	asfrom INT,
	asto INT,
	power INT,
	conntype VARCHAR(255)
	--,
	--FOREIGN KEY (asfrom) REFERENCES asinfo(number),
	--FOREIGN KEY (asto) REFERENCES asinfo(number)
);
