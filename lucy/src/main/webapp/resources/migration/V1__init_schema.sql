-- Create database if not exists
CREATE DATABASE IF NOT EXISTS tech_equations;
USE tech_equations;

-- Users table
CREATE TABLE users (
                       id CHAR(36) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
                       reset_token VARCHAR(255) UNIQUE,
                       reset_token_expiry TIMESTAMP NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Services table
CREATE TABLE services (
                          id CHAR(36) PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT NOT NULL,
                          img VARCHAR(255) Null,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Testimonials table
CREATE TABLE testimonials (
                              id CHAR(36) PRIMARY KEY,
                              author_name VARCHAR(255) NOT NULL,
                              author_title VARCHAR(255),
                              quote_text TEXT NOT NULL,
                              is_published BOOLEAN NOT NULL DEFAULT FALSE,
                              display_order INTEGER DEFAULT 0,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              avatar_url VARCHAR(2048)
);

-- Pricing Plans table
CREATE TABLE pricing_plans (
                               id CHAR(36) PRIMARY KEY,
                               name VARCHAR(255) NOT NULL UNIQUE,
                               original_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
                               price DECIMAL(10, 2) NOT NULL,
                               billing_frequency VARCHAR(50) NOT NULL,
                               features TEXT NOT NULL,
                               is_highlighted BOOLEAN NOT NULL DEFAULT FALSE,
                               display_order INTEGER DEFAULT 0,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- FAQ table
CREATE TABLE faq (
                     id CHAR(36) PRIMARY KEY,
                     question VARCHAR(1000) NOT NULL,
                     answer TEXT NOT NULL,
                     category VARCHAR(100),
                     display_order INTEGER DEFAULT 0,
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Partners table
CREATE TABLE partners (
                          id CHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          logo_url VARCHAR(512) NOT NULL,
                          website_url VARCHAR(512),
                          display_order INTEGER DEFAULT 0,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Contacts table
CREATE TABLE contacts (
                          id CHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          subject VARCHAR(255),
                          message TEXT NOT NULL,
                          is_read BOOLEAN NOT NULL DEFAULT FALSE,
                          submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
);

-- Content Blocks table (optional CMS)
CREATE TABLE content_blocks (
                                id VARCHAR(100) PRIMARY KEY,
                                content TEXT NOT NULL,
                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- team members table
CREATE TABLE team_members (
                              id CHAR(36) PRIMARY KEY,
                              name VARCHAR(255),
                              role VARCHAR(255),
                              image_url VARCHAR(255),
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- impact statistics table
CREATE TABLE impact_stats (
                              id CHAR(36) PRIMARY KEY,
                              label VARCHAR(255),
                              value INT,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE blog (
                      id CHAR(36) PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      summary TEXT,
                      content TEXT NOT NULL,
                      category VARCHAR(100),
                      image_url VARCHAR(255),
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          image_url VARCHAR(500),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE micro_products (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                product_id INT NOT NULL,
                                name VARCHAR(255) NOT NULL,
                                description TEXT,
                                price DECIMAL(10,2),
                                image_url VARCHAR(500),
                                stock INT DEFAULT 0,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE sub_micro_products (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    micro_product_id INT NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    description TEXT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    FOREIGN KEY (micro_product_id) REFERENCES micro_products(id) ON DELETE CASCADE
);

-- Indices
-- Users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_reset_token ON users(reset_token);

-- Contacts
CREATE INDEX idx_contacts_submitted_at ON contacts(submitted_at);
CREATE INDEX idx_contacts_user_id ON contacts(user_id);

-- FAQ
CREATE INDEX idx_faq_category ON faq(category);
CREATE INDEX idx_faq_created_at ON faq(created_at);
CREATE INDEX idx_faq_display_order ON faq(display_order);

-- Testimonials
CREATE INDEX idx_testimonials_created_at ON testimonials(created_at);
CREATE INDEX idx_testimonials_display_order ON testimonials(display_order);

-- Pricing Plans
CREATE INDEX idx_pricing_plans_created_at ON pricing_plans(created_at);

-- Partners
CREATE INDEX idx_partners_created_at ON partners(created_at);

-- Services
CREATE INDEX idx_services_name ON services(name);

-- Team Members
CREATE INDEX idx_team_members_name ON team_members(name);

-- Impact Stats
CREATE INDEX idx_impact_stats_label ON impact_stats(label);

-- Products
CREATE INDEX idx_products_name ON products(name);

-- Micro Products
CREATE INDEX idx_micro_products_name ON micro_products(name);
CREATE INDEX idx_micro_products_product_id ON micro_products(product_id);

-- Sub Micro Products
CREATE INDEX idx_sub_micro_products_micro_product_id ON sub_micro_products(micro_product_id);

-- Optional: Full-text indexes for search
ALTER TABLE blog ADD FULLTEXT(title, summary, content);
ALTER TABLE faq ADD FULLTEXT(question, answer);