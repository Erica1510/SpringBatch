drop table if exists sales_info;
CREATE TABLE sales_info (
                            id int PRIMARY KEY AUTO_INCREMENT,
                            product VARCHAR(255),
                            seller VARCHAR(255),
                            seller_id INT,
                            price DOUBLE,
                            category VARCHAR(255)
);
