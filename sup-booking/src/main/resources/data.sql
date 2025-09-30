INSERT INTO sup_types (name, price_per_hour)
VALUES ('Gladiator', 1000),
       ('Australia GP', 1250),
       ('Starboard', 1500);

INSERT INTO sups (sup_type_id) 
VALUES (1), (1), (1), (2), (2), (3);

INSERT INTO instructors (full_name, phone, price_per_hour)
VALUES ('Ivan Ivanov', '81233212346', 2000),
       ('Petr Petrov', '81233211235', 2500);

INSERT INTO clients (name, phone_number)
VALUES ('Gennadii', '88005553535');

INSERT INTO bookings (sup_id, client_id, instructor_id, started_at, finished_at, revenue)
VALUES (1, 1, 1, '2024-01-15 10:00:00', '2024-01-15 12:00:00', 2000);

insert into users(username, password_hash, role)
values ('user', '$2a$10$Qw/0TtHZE0sQT.ajt3aad.Vt81y4GIQp0Oo547PkbBtNv7K7wYlXa', 'USER'),
       ('admin', '$2a$10$0bXHB95eByBeTReALSUqeOMjysJx65GChZOEs6q1.7FPIiGf.D6k2', 'ADMIN');
