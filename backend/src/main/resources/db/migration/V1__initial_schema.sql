-----------------------------------
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-----------------------------------------------
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE INDEX idx_created_at ON users (created_at);
CREATE INDEX idx_is_deleted ON users (is_deleted);

-----------------------------------------------
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    account_id VARCHAR(10) NOT NULL UNIQUE,
    
    user_id BIGINT NOT NULL UNIQUE REFERENCES users (id)
);

CREATE INDEX idx_created_at_accounts ON accounts (created_at);
CREATE INDEX idx_is_deleted_accounts ON accounts (is_deleted);

-----------------------------------------------
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

---------------------------------------------
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    
    amount NUMERIC(19, 2) NOT NULL,
    
    source_account_id BIGINT NOT NULL REFERENCES accounts (id),
    destination_account_id BIGINT NOT NULL REFERENCES accounts (id)
);

CREATE INDEX idx_created_at_transactions ON transactions (created_at);
CREATE INDEX idx_is_deleted_transactions ON transactions (is_deleted);

-----------------------------------------------
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');