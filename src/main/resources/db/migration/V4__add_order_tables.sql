CREATE TABLE orders
(
    id          BIGSERIAL NOT NULL CONSTRAINT orders_pk PRIMARY KEY,
    customer_id BIGINT NOT NULL CONSTRAINT orders_users_id_fk REFERENCES users (id),
    status      VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    total_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE order_items
(
    id          BIGSERIAL NOT NULL CONSTRAINT order_items_pk PRIMARY KEY,
    order_id    BIGINT NOT NULL CONSTRAINT order_items_orders_id_fk REFERENCES orders (id),
    product_id  BIGINT NOT NULL CONSTRAINT order_items_products_id_fk REFERENCES products (id),
    unit_price  DECIMAL(10, 2) NOT NULL,
    quantity    INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL
);