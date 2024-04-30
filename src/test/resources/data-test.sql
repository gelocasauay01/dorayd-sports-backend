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


INSERT INTO user_auth(username, password, role, user_id) VALUES('abc123', '$2a$10$N35fUCHQ7/OwM4Dcw6LH8uwL8yFIJ/PnoxgAuVDJEUuNlXGANmu1G', 'USER', 1); -- decrypted password: password1
INSERT INTO user_auth(username, password, role, user_id) VALUES('abc456', '$2a$10$8UQdI5Cx/AWe5MbIcQSQue4gB7LIgW42wrGm.6iZiG7JbAEGBJzzy', 'USER', 2); -- decrypted password: password2
INSERT INTO user_auth(username, password, role, user_id) VALUES('abc789', '"$2a$10$mhT5RqxHGDCwO2ls3ClI0eNj82GUWTHOk3ERiUXEnSFRyPd1/Ebeu"', 'ADMIN', 3); --decryted password: password3