DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(50)
);

CREATE TABLE item (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    astring VARCHAR(50), 
    anumber INT,
    adate DATE,
    atime TIME,
    aboolean BOOLEAN,
    aprice DECIMAL(10,2),
    adouble DOUBLE,
    category INT,

    FOREIGN KEY (category)
          REFERENCES category(id)
          ON UPDATE CASCADE ON DELETE SET NULL
);

