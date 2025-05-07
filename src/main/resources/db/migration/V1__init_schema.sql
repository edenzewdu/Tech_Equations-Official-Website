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
                              avatar_url VARCHAR(255)
);

-- Pricing Plans table
CREATE TABLE pricing_plans (
                               id CHAR(36) PRIMARY KEY,
                               name VARCHAR(255) NOT NULL UNIQUE,
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
                          user_id CHAR(36),
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Content Blocks table (optional CMS)
CREATE TABLE content_blocks (
                                id VARCHAR(100) PRIMARY KEY,
                                content TEXT NOT NULL,
                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indices
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_contacts_submitted_at ON contacts (submitted_at);
CREATE INDEX idx_faq_category ON faq (category);
CREATE INDEX idx_testimonials_created_at ON testimonials (created_at);
CREATE INDEX idx_pricing_plans_created_at ON pricing_plans (created_at);
CREATE INDEX idx_partners_created_at ON partners (created_at);
CREATE INDEX idx_faq_created_at ON faq (created_at);
