INSERT INTO country (code, name)
VALUES
    ('UK', 'United Kingdom'),
    ('US', 'United States'),
    ('FR', 'France'),
    ('ES', 'Spain'),
    ('IT', 'Italy'),
    ('DE', 'Germany'),
    ('CA', 'Canada'),
    ('AU', 'Australia'),
    ('JP', 'Japan'),
    ('BR', 'Brazil'),
    ('MX', 'Mexico'),
    ('AR', 'Argentina'),
    ('RU', 'Russia'),
    ('CN', 'China'),
    ('IN', 'India'),
    ('SA', 'South Africa'),
    ('EG', 'Egypt'),
    ('NG', 'Nigeria'),
    ('KE', 'Kenya'),
    ('NZ', 'New Zealand'),
    ('ID', 'Indonesia'),
    ('TH', 'Thailand'),
    ('VN', 'Vietnam'),
    ('KR', 'South Korea'),
    ('TR', 'Turkey'),
    ('IR', 'Iran'),
    ('IQ', 'Iraq'),
    ('SAR', 'Saudi Arabia'),
    ('AE', 'United Arab Emirates'),
    ('QA', 'Qatar'),
    ('KW', 'Kuwait'),
    ('OM', 'Oman'),
    ('BH', 'Bahrain'),
    ('SY', 'Syria'),
    ('LB', 'Lebanon'),
    ('JO', 'Jordan'),
    ('PS', 'Palestine'),
    ('CO', 'Colombia'),
    ('PE', 'Peru'),
    ('CL', 'Chile'),
    ('VE', 'Venezuela'),
    ('EC', 'Ecuador'),
    ('BO', 'Bolivia'),
    ('PY', 'Paraguay'),
    ('UY', 'Uruguay'),
    ('CR', 'Costa Rica'),
    ('PA', 'Panama'),
    ('CU', 'Cuba'),
    ('DO', 'Dominican Republic'),
    ('GT', 'Guatemala'),
    ('HN', 'Honduras'),
    ('SV', 'El Salvador'),
    ('NI', 'Nicaragua');

INSERT INTO permission (id, name)
VALUES
    (1, 'CREATE_RECIPE'),
    (2, 'UPDATE_RECIPE'),
    (3, 'DELETE_RECIPE'),

    (4, 'CREATE_COMMENT'),
    (5, 'UPDATE_COMMENT'),
    (6, 'DELETE_COMMENT'),

    (7, 'CREATE_USER'),
    (8, 'UPDATE_USER'),
    (9, 'DELETE_USER'),

    (10, 'CREATE_TAG'),
    (11, 'UPDATE_TAG'),
    (12, 'DELETE_TAG'),

    (13, 'CREATE_INGREDIENT'),
    (14, 'UPDATE_INGREDIENT'),
    (15, 'DELETE_INGREDIENT'),

    (16, 'CREATE_MEAL_PLANNER'),
    (17, 'UPDATE_MEAL_PLANNER'),
    (18, 'DELETE_MEAL_PLANNER'),

    (19, 'ADMIN_USERS'),
    (20, 'ADMIN_USERS_ROLES'),
    (21, 'ADMIN_ROLES'),
    (22, 'ADMIN_PANEL');


INSERT INTO role (id, name)
VALUES
    (0, 'ADMIN'),
    (1, 'DEFAULT');

INSERT INTO role_permission (role_id, permission_id)
VALUES
    (0, '1'),
    (0, '2'),
    (0, '3'),
    (0, '4'),
    (0, '5'),
    (0, '6'),
    (0, '7'),
    (0, '8'),
    (0, '9'),
    (0, '10'),
    (0, '11'),
    (0, '12'),
    (0, '13'),
    (0, '14'),
    (0, '15'),
    (0, '16'),
    (0, '17'),
    (0, '18'),
    (0, '19'),
    (0, '20'),
    (0, '21'),
    (0, '22'),

    (1, '1'),
    (1, '2'),
    (1, '3'),
    (1, '4'),
    (1, '5'),
    (1, '6'),
    (1, '16'),
    (1, '17'),
    (1, '18');

INSERT INTO ingredient (id, name, photo_url,nutritional_info,unit)
VALUES
    ('d290f1ee-6c54-4b01-90e6-d701748f0851', 'Chicken Breast', 'https://example.com/chicken.jpg',  '{}','GRAMS'),
    ('58af4e1a-6c54-4b01-90e6-d701748f0851', 'Broccoli', 'https://example.com/broccoli.jpg', '{}','GRAMS'),
    ('f5d5f1d0-6c54-4b01-90e6-d701748f0851', 'Olive Oil', 'https://example.com/oliveoil.jpg', '{}','MILLILITERS'),
    ('4d5a6e2f-6c54-4b01-90e6-d701748f0851', 'Almonds', 'https://example.com/almonds.jpg', '{}','GRAMS'),
    ('1234abcd-6c54-4b01-90e6-d701748f0851', 'Quinoa', 'https://example.com/quinoa.jpg',  '{}','GRAMS');

INSERT INTO users (username, email, full_name, password, photo_url, biography, social_media, register_date, is_email_verified, user_status, country_code, last_connection)
VALUES
    ('user', 'john.doe@example.com', 'John Doe', '$2a$10$PJkrBGzwNDG0MvoMSunqs.0ltj1dOk5nIZXhrN.5n.Jkn1pxyiwPW', 'https://media.licdn.com/dms/image/v2/C4E03AQHJDFO3QUUwNg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1595511102288?e=1736380800&v=beta&t=XOUFAFReTn2VzrWv7gI5OsUQ3KkuiujRXBU8bWR9ATs', 'A passionate chef and food lover.', '{}', '2024-08-22 10:00:00', true,0, 'UK', '2024-08-22 12:00:00'),
    ('admin', 'jane.smith@example.com', 'Jane Smith', '$2a$10$PJkrBGzwNDG0MvoMSunqs.0ltj1dOk5nIZXhrN.5n.Jkn1pxyiwPW', 'https://media.licdn.com/dms/image/v2/D4E03AQFCuGUrG1_wzw/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1681838820525?e=1736380800&v=beta&t=4K4gPLi14aCO502eeimiu2gc07QuA_izg9O2Yugbmzw', 'Loves to bake and share recipes.','{}', '2024-08-21 09:30:00', true,0, 'UK', '2024-08-22 11:00:00');

INSERT INTO user_role (user_id, role_id)
VALUES
    ('user', 1),
    ('admin', 1),
    ('admin', 0);

INSERT INTO tag (name)
VALUES
    ('Vegetarian'),
    ('Gluten-Free'),
    ('Dairy-Free'),
    ('Vegan'),
    ('Low-Carb'),
    ('Keto'),
    ('Paleo'),
    ('High-Protein'),
    ('Nut-Free'),
    ('Sugar-Free');

INSERT INTO recipe (id, title, description, photo_url, publish_date, difficulty, country_code, estimated_time, recipe_owner)
VALUES
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'Spaghetti Carbonara', 'Classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.', 'https://example.com/spaghetti.jpg', '2024-08-22 15:00:00', 'INTERMEDIATE', 'UK', 90000000000000, 'user'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'Chicken Tikka Masala', 'A flavorful Indian dish made with roasted marinated chicken in a spiced curry sauce.', 'https://example.com/tikka.jpg', '2024-08-21 12:00:00', 'ADVANCED', 'UK', 16000000000000, 'user'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'Avocado Toast', 'Healthy and easy breakfast option with mashed avocado on toast, topped with various ingredients.', 'https://example.com/avocado.jpg', '2024-08-20 08:00:00', 'BASIC', 'UK', 100000000000, 'user'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Beef Wellington', 'A gourmet dish made with fillet steak, coated with pâté and duxelles, and wrapped in puff pastry.', 'https://example.com/wellington.jpg', '2024-08-19 18:30:00', 'ADVANCED', 'UK', 900000000000, 'user'),
    ('e7979614-ec42-47d1-86f0-53d580992a52', 'Sushi Rolls', 'Japanese dish consisting of vinegared rice, seafood, and vegetables, rolled in seaweed.', 'https://example.com/sushi.jpg', '2024-08-18 19:00:00', 'INTERMEDIATE', 'UK', 1200000000000, 'admin');

INSERT INTO comment (id, recipe_id, user_id, content, creation_date)
VALUES
    ('539b8afa-ec2d-4d29-8376-fa121894101e', '6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'admin', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('15dfc7cd-2371-4991-9cd0-3cacacc7c50b', '3759071a-444d-40fe-9a2d-ef8c04c57814', 'admin', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('4caa48e6-3685-4327-a533-cdaf9ba46469', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'admin', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('15f4f808-68be-4bc7-8462-e44a60b4d118', '8e199ac3-ae11-4902-a356-386abb33a93d', 'admin', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('b6c51ad1-59ac-458d-bea0-f7c2e10c8a11', 'e7979614-ec42-47d1-86f0-53d580992a52', 'admin', 'This comment is for testing purposes only', '2024-09-25 18:30:00');

INSERT INTO meal_planner (id, is_public, description, name)
VALUES
    ('a40ac843-fe53-4980-a64c-f9148aecdb83', true, 'Meal test 1 description olny for testing', 'MealTest 1'),
    ('d8dbb102-463e-401f-ad29-b29bd1cdbd55', false, 'Meal test number two description olny for testing', 'MealTest 2');

INSERT INTO user_meal_planner (is_owner, id, meal_planner_id, user_id)
VALUES
    (true, 'edcaf3bf-0f8e-4159-a61c-a9d73356b3f3', 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'user'),
    (true, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'admin');

INSERT INTO meal_day (day, planner_id, description)
VALUES
    (1, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 1 for planner 1'),
    (2, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 2 for planner 1'),
    (3, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 3 for planner 1'),
    (1, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 1 for planner 2'),
    (2 ,'d8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 2 for planner 2'),
    (3, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 3 for planner 2'),
    (4, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 4 for planner 2');

INSERT INTO meal_day_recipes (day, planner_id, recipe_id)
VALUES
    (1, 'a40ac843-fe53-4980-a64c-f9148aecdb83','6a5f043c-852e-4aeb-ba8d-50ab5fff3420'),
    (2, 'a40ac843-fe53-4980-a64c-f9148aecdb83','3759071a-444d-40fe-9a2d-ef8c04c57814'),
    (3, 'a40ac843-fe53-4980-a64c-f9148aecdb83','3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b'),
    (1, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55','8e199ac3-ae11-4902-a356-386abb33a93d'),
    (2 ,'d8dbb102-463e-401f-ad29-b29bd1cdbd55','e7979614-ec42-47d1-86f0-53d580992a52'),
    (3, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55','6a5f043c-852e-4aeb-ba8d-50ab5fff3420');

INSERT INTO notification (id, is_read, creation_date, notification_owner, content, url)
VALUES
    ('32f77351-8381-4855-8bb5-4e351bf55453', false, '2024-10-25 18:30:00', 'user', 'This is a notification example only for testing issues', 'url.com'),
    ('f485408a-d235-498b-9528-d40ca16d916c', false, '2024-11-25 18:30:00', 'user', 'This is a notification example only for testing issues', 'url.com');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, quantity)
VALUES
    ('32f77351-8381-4855-8bb5-4e351bf55451', '6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'd290f1ee-6c54-4b01-90e6-d701748f0851',200),
    ('32f77351-8381-4855-8bb5-4e351bf55452', '3759071a-444d-40fe-9a2d-ef8c04c57814', '4d5a6e2f-6c54-4b01-90e6-d701748f0851',30),
    ('32f77351-8381-4855-8bb5-4e351bf55453', '3759071a-444d-40fe-9a2d-ef8c04c57814', 'f5d5f1d0-6c54-4b01-90e6-d701748f0851',10),
    ('32f77351-8381-4855-8bb5-4e351bf55454', '3759071a-444d-40fe-9a2d-ef8c04c57814', '58af4e1a-6c54-4b01-90e6-d701748f0851',10),
    ('32f77351-8381-4855-8bb5-4e351bf55455', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'd290f1ee-6c54-4b01-90e6-d701748f0851',50),
    ('32f77351-8381-4855-8bb5-4e351bf55456', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', '4d5a6e2f-6c54-4b01-90e6-d701748f0851',100),
    ('32f77351-8381-4855-8bb5-4e351bf55457', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', '1234abcd-6c54-4b01-90e6-d701748f0851',100),
    ('32f77351-8381-4855-8bb5-4e351bf55458', '8e199ac3-ae11-4902-a356-386abb33a93d', '58af4e1a-6c54-4b01-90e6-d701748f0851',200),
    ('32f77351-8381-4855-8bb5-4e351bf55459', '8e199ac3-ae11-4902-a356-386abb33a93d', 'f5d5f1d0-6c54-4b01-90e6-d701748f0851',30),
    ('32f77351-8381-4855-8bb5-4e351bf55460', '8e199ac3-ae11-4902-a356-386abb33a93d', '4d5a6e2f-6c54-4b01-90e6-d701748f0851',100),
    ('32f77351-8381-4855-8bb5-4e351bf55461', '8e199ac3-ae11-4902-a356-386abb33a93d', '1234abcd-6c54-4b01-90e6-d701748f0851',50),
    ('32f77351-8381-4855-8bb5-4e351bf55462', 'e7979614-ec42-47d1-86f0-53d580992a52', 'd290f1ee-6c54-4b01-90e6-d701748f0851',10),
    ('32f77351-8381-4855-8bb5-4e351bf55463', 'e7979614-ec42-47d1-86f0-53d580992a52', '58af4e1a-6c54-4b01-90e6-d701748f0851',20);

INSERT INTO recipe_likes (recipe_id, user_id)
VALUES
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'admin'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'admin'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'admin');

INSERT INTO recipe_tags (recipe_id, tag_id)
VALUES
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'Vegetarian'),
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'Gluten-Free'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'Gluten-Free'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'Keto'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'High-Protein'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'Nut-Free'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'High-Protein'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'Paleo'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Vegetarian'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Paleo'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Sugar-Free'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Nut-Free'),
    ('e7979614-ec42-47d1-86f0-53d580992a52', 'Gluten-Free'),
    ('e7979614-ec42-47d1-86f0-53d580992a52', 'Vegan');

INSERT INTO users_follow (creation_date, id, followed_id, follower_id)
VALUES
    ('2024-09-25 18:30:00', '036dabb2-5129-4d57-b6eb-da9d88457180', 'admin', 'user');