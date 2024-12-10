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
    (18, 'DELETE_MEAL_PLANNER');

INSERT INTO role (id, name)
VALUES
    (0, 'ADMIN'),
    (1, 'DEFAULT_USER');

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

INSERT INTO users (username, email, full_name, password, photo_url, biography, social_media, register_date, is_email_verified, user_status, country, last_connection)
VALUES
    ('jhondoe', 'john.doe@example.com', 'User USER', 'password123', 'https://example.com/johndoe.jpg', 'A passionate chef and food lover.', '{}', '2024-08-22 10:00:00', true,0, 'USA', '2024-08-22 12:00:00'),
    ('janesmith', 'jane.smith@example.com', 'User OTHER', 'securepass456', 'https://example.com/janesmith.jpg', 'Loves to bake and share recipes.','{}', '2024-08-21 09:30:00', true,0, 'Canada', '2024-08-22 11:00:00');

INSERT INTO user_role (user_id, role_id)
VALUES
    ('jhondoe', 1),
    ('janesmith', 1),
    ('janesmith', 0);

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

INSERT INTO recipe (id, title, description, photo_url, publish_date, difficulty, country, estimated_time, recipe_owner)
VALUES
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'Spaghetti Carbonara', 'Classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.', 'https://example.com/spaghetti.jpg', '2024-08-22 15:00:00', 'INTERMEDIATE', 'Italy', '9000', 'jhondoe'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'Chicken Tikka Masala', 'A flavorful Indian dish made with roasted marinated chicken in a spiced curry sauce.', 'https://example.com/tikka.jpg', '2024-08-21 12:00:00', 'ADVANCED', 'India', '16000', 'jhondoe'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'Avocado Toast', 'Healthy and easy breakfast option with mashed avocado on toast, topped with various ingredients.', 'https://example.com/avocado.jpg', '2024-08-20 08:00:00', 'BASIC', 'USA', '10000', 'jhondoe'),
    ('8e199ac3-ae11-4902-a356-386abb33a93d', 'Beef Wellington', 'A gourmet dish made with fillet steak, coated with pâté and duxelles, and wrapped in puff pastry.', 'https://example.com/wellington.jpg', '2024-08-19 18:30:00', 'ADVANCED', 'UK', '9000', 'jhondoe'),
    ('e7979614-ec42-47d1-86f0-53d580992a52', 'Sushi Rolls', 'Japanese dish consisting of vinegared rice, seafood, and vegetables, rolled in seaweed.', 'https://example.com/sushi.jpg', '2024-08-18 19:00:00', 'INTERMEDIATE', 'Japan', '12000', 'janesmith');

INSERT INTO comment (id, recipe_id, user_id, content, creation_date)
VALUES
    ('539b8afa-ec2d-4d29-8376-fa121894101e', '6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'janesmith', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('15dfc7cd-2371-4991-9cd0-3cacacc7c50b', '3759071a-444d-40fe-9a2d-ef8c04c57814', 'janesmith', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('4caa48e6-3685-4327-a533-cdaf9ba46469', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'janesmith', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('15f4f808-68be-4bc7-8462-e44a60b4d118', '8e199ac3-ae11-4902-a356-386abb33a93d', 'janesmith', 'This comment is for testing purposes only', '2024-09-25 18:30:00'),
    ('b6c51ad1-59ac-458d-bea0-f7c2e10c8a11', 'e7979614-ec42-47d1-86f0-53d580992a52', 'janesmith', 'This comment is for testing purposes only', '2024-09-25 18:30:00');

INSERT INTO meal_planner (id, is_public, description, name)
VALUES
    ('a40ac843-fe53-4980-a64c-f9148aecdb83', false, 'Meal test 1 description olny for testing', 'MealTest 1'),
    ('d8dbb102-463e-401f-ad29-b29bd1cdbd55', false, 'Meal test number two description olny for testing', 'MealTest 2');

INSERT INTO meal_day (id, day, planner_id, description)
VALUES
    ('06d0c9c9-6224-4c23-82bd-321935c567cf', 1, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 1 for planner 1'),
    ('47e84f16-cb73-4582-8866-05d646741119', 2, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 2 for planner 1'),
    ('79a0f006-ca68-469e-a768-2da9d4879f4f', 3, 'a40ac843-fe53-4980-a64c-f9148aecdb83', 'Meal day 3 for planner 1'),
    ('163a1a11-b7c6-4382-ac33-c4c82fc4488c', 1, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 1 for planner 2'),
    ('8e5d54e6-140f-437b-8a79-1c0afc10cf6d', 2 ,'d8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 2 for planner 2'),
    ('32c4d7ad-9951-4f66-a1ab-0857771fc69e', 3, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 3 for planner 2'),
    ('b3c24e24-5b37-4c4e-908d-90a76d644c95', 4, 'd8dbb102-463e-401f-ad29-b29bd1cdbd55', 'Meal day 4 for planner 2');

INSERT INTO meal_day_recipes (meal_day_id, recipe_id)
VALUES
    ('06d0c9c9-6224-4c23-82bd-321935c567cf', '6a5f043c-852e-4aeb-ba8d-50ab5fff3420'),
    ('47e84f16-cb73-4582-8866-05d646741119', '3759071a-444d-40fe-9a2d-ef8c04c57814'),
    ('79a0f006-ca68-469e-a768-2da9d4879f4f', '3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b'),
    ('163a1a11-b7c6-4382-ac33-c4c82fc4488c', '8e199ac3-ae11-4902-a356-386abb33a93d'),
    ('8e5d54e6-140f-437b-8a79-1c0afc10cf6d', 'e7979614-ec42-47d1-86f0-53d580992a52'),
    ('32c4d7ad-9951-4f66-a1ab-0857771fc69e', '6a5f043c-852e-4aeb-ba8d-50ab5fff3420');

INSERT INTO notification (id, is_read, creation_date, notification_owner, content, url)
VALUES
    ('32f77351-8381-4855-8bb5-4e351bf55453', false, '2024-10-25 18:30:00', 'jhondoe', 'This is a notification example only for testing issues', 'url.com'),
    ('f485408a-d235-498b-9528-d40ca16d916c', false, '2024-11-25 18:30:00', 'jhondoe', 'This is a notification example only for testing issues', 'url.com');

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
    ('6a5f043c-852e-4aeb-ba8d-50ab5fff3420', 'janesmith'),
    ('3759071a-444d-40fe-9a2d-ef8c04c57814', 'janesmith'),
    ('3ce3b8c6-6e64-48b4-a9cd-0a325f18b76b', 'janesmith');

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