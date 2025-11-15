CREATE TABLE carts
(
    id           UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    date_created DATE DEFAULT current_date      NOT NULL
);

CREATE TABLE cart_items
(
    id         bigserial NOT NULL PRIMARY KEY,
    cart_id    UUID      NOT NULL,
    product_id BIGINT    NOT NULL,
    quantity   INTEGER   NOT NULL DEFAULT 1,
    CONSTRAINT cart_items_cart_fk FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    CONSTRAINT cart_items_product_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT cart_items_unique_product_per_cart UNIQUE (cart_id, product_id)
);