CREATE SCHEMA IF NOT EXISTS onlinestore;

CREATE TABLE onlinestore.user_table
(
    id            INT                NOT NULL PRIMARY KEY,
    first_name    VARCHAR(45)        NOT NULL,
    last_name     VARCHAR(45)        NOT NULL,
    email         VARCHAR(45) UNIQUE NOT NULL,
    cell          VARCHAR(15) UNIQUE NOT NULL,
    password      VARCHAR(45)        NOT NULL,
    type          VARCHAR(45)        NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE onlinestore.address
(
    id            INT         NOT NULL PRIMARY KEY,
    address       VARCHAR(45) NOT NULL,
    note          VARCHAR(200),
    user_id       INT,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES onlinestore.user_table(id)
);

CREATE TABLE onlinestore.category
(
    id            INT         NOT NULL PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE onlinestore.tag
(
    id            INT         NOT NULL PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE onlinestore.item
(
    id            INT           NOT NULL PRIMARY KEY,
    name          VARCHAR(45)   NOT NULL,
    price         DOUBLE PRECISION,
    availability  VARCHAR(45)   NOT NULL,
    category_id   INT           NOT NULL,
    description   VARCHAR(3000) NOT NULL,
    image_path    VARCHAR(100)  NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES onlinestore.category (id)
);

CREATE TABLE onlinestore.tag_item
(
    tag_id  INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (tag_id, item_id)
);

CREATE TABLE onlinestore.order_table
(
    id            INT NOT NULL PRIMARY KEY,
    status        VARCHAR(45),
    address_id    INT,
    user_id       INT,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (address_id) REFERENCES onlinestore.address (id),
    FOREIGN KEY (user_id) REFERENCES onlinestore.user_table (id)
);

CREATE TABLE onlinestore.order_item
(
    id            INT NOT NULL PRIMARY KEY,
    quantity      INT,
    accepted_at   TIMESTAMP,
    item_id       INT,
    order_id      INT,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES onlinestore.item (id),
    FOREIGN KEY (order_id) REFERENCES onlinestore.order_table (id)
);

CREATE SEQUENCE onlinestore.address_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.category_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.item_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.order_item_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.order_table_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.tag_seq INCREMENT 1 START 1;
CREATE SEQUENCE onlinestore.user_seq INCREMENT 1 START 1;