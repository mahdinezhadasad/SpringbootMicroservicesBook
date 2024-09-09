-- Drop the book inventory service database and users if they exist
DROP DATABASE IF EXISTS bookorderservice;
DROP USER IF EXISTS 'book_order_service'@'%';
DROP USER IF EXISTS 'book_order_admin'@'%';
DROP USER IF EXISTS 'book_order_readonly'@'%';

-- Create the book inventory service database
CREATE DATABASE IF NOT EXISTS bookorderservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create the admin user (full privileges)
CREATE USER IF NOT EXISTS 'book_order_admin'@'%' IDENTIFIED WITH mysql_native_password BY 'admin_password';
GRANT ALL PRIVILEGES ON `bookorderservice`.* TO 'book_order_admin'@'%';

-- Create a regular service user with specific permissions
CREATE USER IF NOT EXISTS 'book_order_service'@'%' IDENTIFIED WITH mysql_native_password BY 'service_password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE VIEW, SHOW VIEW ON `bookorderservice`.* TO 'book_order_service'@'%';

-- Create a read-only user (only SELECT permission)
CREATE USER IF NOT EXISTS 'book_order_readonly'@'%' IDENTIFIED WITH mysql_native_password BY 'readonly_password';
GRANT SELECT ON `bookorderservice`.* TO 'book_order_readonly'@'%';

-- Apply the changes
FLUSH PRIVILEGES;