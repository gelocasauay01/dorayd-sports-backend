INSERT INTO leagues(title) VALUES('Greenpark league');
INSERT INTO leagues(title) VALUES('Pokemon league');
INSERT INTO leagues(title) VALUES('Karate Tournament');
INSERT INTO leagues(title) VALUES('Vista league');
INSERT INTO leagues(title) VALUES('Creators league');
INSERT INTO leagues(title) VALUES('Taekwondo Tournament');

INSERT INTO teams(name) VALUES('Team Rocket');
INSERT INTO teams(name) VALUES('OG');
INSERT INTO teams(name) VALUES('My Chemical Romance');
INSERT INTO teams(name) VALUES('Team Magma');
INSERT INTO teams(name) VALUES('EG');
INSERT INTO teams(name) VALUES('Red Jumpsuit Apparatus');

INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('Joseph', 'Mardo', 'Casauay', '1999-08-01', 'MALE');
INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('Jiro', 'Kyle', 'Reyes', '1999-08-21', 'FEMALE');
INSERT INTO users(first_name, last_name, birth_date, gender) VALUES('Hayley', 'Williams', '1985-01-11', 'NON_BINARY');
INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('Hayley', 'Mark', 'Jones', '1985-01-11', 'FEMALE');
INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('asdasd', 'sdsdfdo', 'Casdffdgdfgauay', '1999-08-01', 'MALE');
INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('Jidfgro', 'Kydfgle', 'Rdfgdfgs', '1999-08-21', 'FEMALE');
INSERT INTO users(first_name, last_name, birth_date, gender) VALUES('Hayley', 'Willidfgams', '1985-01-11', 'NON_BINARY');
INSERT INTO users(first_name, middle_name, last_name, birth_date, gender) VALUES('Hadfgyley', 'Mdfgark', 'Jones', '1985-01-11', 'FEMALE');

INSERT INTO user_auth(email, password, role, user_id) VALUES('abc123@yahoo.com', '$2a$10$N35fUCHQ7/OwM4Dcw6LH8uwL8yFIJ/PnoxgAuVDJEUuNlXGANmu1G', 'USER', 1); -- decrypted password: password1
INSERT INTO user_auth(email, password, role, user_id) VALUES('abc456@yahoo.com', '$2a$10$8UQdI5Cx/AWe5MbIcQSQue4gB7LIgW42wrGm.6iZiG7JbAEGBJzzy', 'USER', 2); -- decrypted password: password2
INSERT INTO user_auth(email, password, role, user_id) VALUES('abc789@yahoo.com', '$2a$10$mhT5RqxHGDCwO2ls3ClI0eNj82GUWTHOk3ERiUXEnSFRyPd1/Ebeu', 'ADMIN', 3); -- decryted password: password3

INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(1, 1, 2, '2024-05-02');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(2, 6, 3, '1985-01-11');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(4, 5, 2, '1985-01-11');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(4, 1, 4, '1985-01-11');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(4, 2, 3, '1985-01-11');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(4, 5, 3, '1985-01-11');
INSERT INTO games(league_id, team_a_id, team_b_id, schedule) VALUES(5, 5, 3, '1985-01-11');

INSERT INTO addresses(address_line_one, address_line_two, municipality, province) VALUES('49 Dollar St. Greenpark Village', 'Brgy. San Isidro', 'Cainta', 'Rizal');
INSERT INTO addresses(address_line_one, address_line_two, municipality, province) VALUES('45 Green St. Vista Verde Village', 'Brgy. San Isidro', 'Cainta', 'Rizal');
INSERT INTO addresses(address_line_one, address_line_two, municipality, province) VALUES('9 America St. Konoha Village', 'Brgy. Mabitch', 'Jahong', 'Carl');

INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(1, 1, 10, 2, 2);
INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(1, 4, 10, 5, 2);
INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(1, 3, 10, 6, 3);
INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(1, 2, 13, 2, 2);
INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(2, 2, 13, 2, 2);
INSERT INTO game_stats(user_id, game_id, points, assists, rebounds) VALUES(2, 4, 13, 2, 2);


