-- 1. Brands Table
CREATE TABLE Brands (
    brand_id NUMBER PRIMARY KEY,
    brand_name VARCHAR2(50) NOT NULL
);

-- 2. Laptops Table (Main Inventory)
CREATE TABLE Laptops (
    laptop_id NUMBER PRIMARY KEY,
    model_name VARCHAR2(100) NOT NULL,
    price NUMBER(10, 2) NOT NULL,
    brand_id NUMBER,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brands(brand_id)
);

-- 3. Customers Table
CREATE TABLE Customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100) NOT NULL,
    phone_number VARCHAR2(15)
);

-- Insert default brands to match the Java JComboBox
INSERT INTO Brands (brand_id, brand_name) VALUES (1, 'HP');
INSERT INTO Brands (brand_id, brand_name) VALUES (2, 'Dell');
INSERT INTO Brands (brand_id, brand_name) VALUES (3, 'Lenovo');
INSERT INTO Brands (brand_id, brand_name) VALUES (4, 'Asus');
COMMIT;