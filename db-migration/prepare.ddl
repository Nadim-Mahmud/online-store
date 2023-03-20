CREATE TABLE IF NOT EXISTS online_store.store_user
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
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE IF NOT EXISTS online_store.address
(
    id            INT         NOT NULL PRIMARY KEY,
    title         VARCHAR(45) NOT NULL,
    address       VARCHAR(45) NOT NULL,
    note          VARCHAR(200),
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE IF NOT EXISTS online_store.category
(
    id            INT         NOT NULL PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE IF NOT EXISTS online_store.tag
(
    id            INT         NOT NULL PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE
);

CREATE TABLE IF NOT EXISTS online_store.item
(
    id            INT           NOT NULL PRIMARY KEY,
    name          VARCHAR(45)   NOT NULL,
    price         DOUBLE PRECISION,
    availability  VARCHAR(45)   NOT NULL,
    category_id   INT           NOT NULL,
    description   VARCHAR(3000) NOT NULL,
    image_path   VARCHAR(100) NOT NULL,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE,
    FOREIGN KEY (category_id) REFERENCES online_store.category (id)
);

CREATE TABLE IF NOT EXISTS online_store.tag_item
(
    tag_id  INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (tag_id, item_id)
);

CREATE TABLE IF NOT EXISTS online_store.order_table
(
    id            INT NOT NULL PRIMARY KEY,
    status        VARCHAR(45),
    address_id    INT,
    user_id       INT,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE,
    FOREIGN KEY (address_id) REFERENCES online_store.address (id),
    FOREIGN KEY (user_id) REFERENCES online_store.store_user (id)
);

CREATE TABLE IF NOT EXISTS online_store.order_item
(
    id            INT NOT NULL PRIMARY KEY,
    quantity      INT,
    accepted_at   DATE,
    item_id       INT,
    order_id      INT,
    access_status VARCHAR(45) DEFAULT 'ACTIVE',
    version       INT         DEFAULT 0,
    created_at    DATE,
    updated_at    DATE,
    FOREIGN KEY (item_id) REFERENCES online_store.item (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES online_store.order_table (id) ON DELETE CASCADE
);