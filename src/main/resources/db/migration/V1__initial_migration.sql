-- === PostgreSQL initial schema aligned to your JPA entities ===
-- Notes:
-- - SERIAL/IDENTITY used for @GeneratedValue(strategy = IDENTITY)
-- - TEXT for long strings
-- - Smallint for Category.id (Byte)
-- - FK cascades chosen to match JPA CascadeType.REMOVE/orphanRemoval where relevant

-- USERS (parent for addresses, profiles, wishlist)
CREATE TABLE users (
                       id       BIGSERIAL PRIMARY KEY,
                       name     VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- CATEGORIES (Byte id -> SMALLSERIAL)
CREATE TABLE categories (
                            id   SMALLSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- PRODUCTS (ManyToOne to Category, cascade = PERSIST only in JPA)
CREATE TABLE products (
                          id          BIGSERIAL PRIMARY KEY,
                          name        VARCHAR(255)   NOT NULL,
                          description TEXT           NOT NULL,
                          price       NUMERIC(10, 2) NOT NULL,
                          category_id SMALLINT       NULL,
                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION
);

-- PROFILES: one-to-one with USERS, shared PK via @MapsId
CREATE TABLE profiles (
                          id             BIGINT       PRIMARY KEY,
                          bio            TEXT         NULL,
                          phone_number   VARCHAR(15)  NULL,
                          date_of_birth  DATE         NULL,
                          loyalty_points INTEGER      NOT NULL DEFAULT 0,
                          CONSTRAINT ck_profiles_loyalty_points_nonneg CHECK (loyalty_points >= 0),
                          CONSTRAINT fk_profiles_user
                              FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
    -- ON DELETE CASCADE mirrors User -> Profile CascadeType.REMOVE
);

-- ADDRESSES: OneToMany from User with CascadeType.REMOVE + orphanRemoval = true
CREATE TABLE addresses (
                           id      BIGSERIAL PRIMARY KEY,
                           street  VARCHAR(255) NOT NULL,
                           city    VARCHAR(255) NOT NULL,
                           zip     VARCHAR(255) NOT NULL,
                           state   VARCHAR(255) NOT NULL,
                           user_id BIGINT       NOT NULL,
                           CONSTRAINT fk_addresses_user
                               FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    -- CASCADE so deleting a user also deletes addresses at DB level, matching JPA REMOVE/orphanRemoval
);

-- WISHLIST: @ManyToMany via @JoinTable(name="wishlist", joinColumns=user_id, inverseJoinColumns=product_id)
CREATE TABLE wishlist (
                          user_id    BIGINT NOT NULL,
                          product_id BIGINT NOT NULL,
                          CONSTRAINT pk_wishlist PRIMARY KEY (user_id, product_id),
                          CONSTRAINT fk_wishlist_user
                              FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT fk_wishlist_product
                              FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
    -- CASCADE on both sides so deleting a user or product cleans join rows
);

-- Helpful FK indexes (Postgres doesn't auto-create them)
CREATE INDEX idx_addresses_user_id     ON addresses (user_id);
CREATE INDEX idx_products_category_id  ON products (category_id);
CREATE INDEX idx_wishlist_user_id      ON wishlist (user_id);
CREATE INDEX idx_wishlist_product_id   ON wishlist (product_id);
