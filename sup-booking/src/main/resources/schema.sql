CREATE TABLE IF NOT EXISTS sup_types (
  id bigserial,
  name VARCHAR(255),
  price_per_hour INTEGER,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS sups (
  id bigserial,
  sup_type_id bigserial references sup_types(id) on delete cascade,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS instructors (
  id bigserial,
  full_name VARCHAR(255),
  phone VARCHAR(12),
  price_per_hour INTEGER,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS clients (
  id bigserial,
  name VARCHAR(255),
  phone_number VARCHAR(12),
  primary key (id)
);

CREATE TABLE IF NOT EXISTS bookings (
  id bigserial,
  sup_id bigserial references sups(id),
  client_id bigserial references clients(id),
  instructor_id bigint,
  started_at TIMESTAMP,
  finished_at TIMESTAMP,
  revenue bigint,
  primary key (id)
);

ALTER TABLE bookings
ADD CONSTRAINT fk_bookings_instructor
FOREIGN KEY (instructor_id) REFERENCES instructors(id);

create table users (
    id bigserial,
    username varchar(50),
    password_hash varchar(500),
    role varchar(50),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS tours (
  id bigserial,
  event_date DATE,
  start_time TIME,
  duration INTEGER,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS tours_clients (
  tour_id bigserial references tours(id) on delete cascade,
  client_id bigserial references clients(id) on delete cascade,
  primary key (tour_id, client_id)
);

CREATE TABLE IF NOT EXISTS tours_instructors (
  tour_id bigserial references tours(id) on delete cascade,
  instructor_id bigserial references instructors(id) on delete cascade,
  primary key (tour_id, instructor_id)
);

CREATE TABLE IF NOT EXISTS tours_sups (
  tour_id bigserial references tours(id) on delete cascade,
  sup_id bigserial references sups(id) on delete cascade,
  primary key (tour_id, sup_id)
);
