INSERT INTO categories (name)
VALUES ('Electronics'),    -- id = 1
       ('Books'),          -- id = 2
       ('Clothing'),       -- id = 3
       ('Home & Kitchen'), -- id = 4
       ('Sports & Outdoors'); -- id = 5

INSERT INTO products (name, description, price, category_id)
VALUES
-- Electronics
('Apple AirPods Pro (2nd Generation)',
 'Premium noise-cancelling wireless earbuds with adaptive transparency and spatial audio.',
 249.00, 1),

('Samsung Galaxy S24',
 '6.2-inch flagship smartphone with Dynamic AMOLED 2X display, triple camera setup, and Snapdragon chipset.',
 799.00, 1),

-- Books
('Atomic Habits',
 'Bestselling self-improvement book by James Clear focused on building good habits through small changes.',
 21.99, 2),

('Dune (Frank Herbert)',
 'Science fiction classic set in a far-future interstellar empire dealing with politics, ecology, and prophecy.',
 12.49, 2),

-- Clothing
('Nike Air Force 1 ''07',
 'Classic leather sneakers known for their comfort, durability, and iconic silhouette.',
 110.00, 3),

('Uniqlo Ultra Light Down Jacket',
 'Lightweight packable down jacket with great insulation and minimalistic design.',
 79.90, 3),

-- Home & Kitchen
('Philips Airfryer XL',
 'Large-capacity digital air fryer that uses rapid air technology to fry foods with little or no oil.',
 149.99, 4),

('IKEA Markus Office Chair',
 'Ergonomic office chair with mesh back, adjustable height, and built-in lumbar support.',
 229.00, 4),

-- Sports & Outdoors
('Wilson Evolution Indoor Basketball',
 'High-quality indoor basketball with composite leather cover, preferred by athletes and high school leagues.',
 69.95, 5),

('Garmin Forerunner 255',
 'GPS running watch with advanced training metrics, heart rate monitoring, and multi-sport tracking.',
 349.99, 5);
