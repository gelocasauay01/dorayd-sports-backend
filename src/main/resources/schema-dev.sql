CREATE TABLE IF NOT EXISTS leagues (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_auth (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    role VARCHAR(10) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_team_memberships (
    user_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    PRIMARY KEY(user_id, team_id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_team FOREIGN KEY(team_id) REFERENCES teams(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS team_league_memberships (
    team_id INTEGER NOT NULL,
    league_id INTEGER NOT NULL,
    PRIMARY KEY(team_id, league_id),
    CONSTRAINT fk_team FOREIGN KEY(team_id) REFERENCES teams(id) ON DELETE CASCADE,
    CONSTRAINT fk_league FOREIGN KEY(league_id) REFERENCES leagues(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS games (
    id SERIAL PRIMARY KEY,
    league_id INTEGER NOT NULL,
    team_a_id INTEGER NOT NULL,
    team_b_id INTEGER NOT NULL,
    schedule TIMESTAMP NOT NULL,
    CONSTRAINT fk_league FOREIGN KEY(league_id) REFERENCES leagues(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_a FOREIGN KEY(team_a_id) REFERENCES teams(id) ON DELETE CASCADE,
    CONSTRAINT fk_team_b FOREIGN KEY(team_b_id) REFERENCES teams(id) ON DELETE CASCADE
);