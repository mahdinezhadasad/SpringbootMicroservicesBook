-- Drop the book service database and users if they exist
DROP DATABASE IF EXISTS bookservice;
DROP USER IF EXISTS 'book_service'@'%';
DROP USER IF EXISTS 'book_admin'@'%';
DROP USER IF EXISTS 'book_readonly'@'%';

-- Create the book service database
CREATE DATABASE IF NOT EXISTS bookservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create the admin user (full privileges)
CREATE USER IF NOT EXISTS 'book_admin'@'%' IDENTIFIED WITH mysql_native_password BY 'admin_password';
GRANT ALL PRIVILEGES ON `bookservice`.* TO 'book_admin'@'%';

-- Create a regular service user with specific permissions
CREATE USER IF NOT EXISTS 'book_service'@'%' IDENTIFIED WITH mysql_native_password BY 'service_password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE VIEW, SHOW VIEW ON `bookservice`.* TO 'book_service'@'%';

-- Create a read-only user (only SELECT permission)
CREATE USER IF NOT EXISTS 'book_readonly'@'%' IDENTIFIED WITH mysql_native_password BY 'readonly_password';
GRANT SELECT ON `bookservice`.* TO 'book_readonly'@'%';

-- Apply the changes
FLUSH PRIVILEGES;