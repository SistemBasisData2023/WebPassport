CREATE TYPE Gender AS ENUM('MALE', 'FEMALE');
CREATE TYPE Status AS ENUM('ACCEPTED', 'DECLINED', 'PENDING');
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE SEQUENCE IF NOT EXISTS account_id_seq AS integer
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS admin_id_seq AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS person_id_seq AS integer
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS office_id_seq AS integer
    START WITH 500
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS address_id_seq AS integer
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS request_id_seq AS integer
    START WITH 5000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS documents_id_seq AS integer
    START WITH 10000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE IF NOT EXISTS Account(
    account_id int DEFAULT nextval('account_id_seq') PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    phone_number varchar(20) NOT NULL,
    password text NOT NULL
    );

CREATE TABLE IF NOT EXISTS Admin(
    admin_id int DEFAULT nextval('admin_id_seq') PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    phone_number varchar(20) NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS Address(
    address_id int DEFAULT nextval('address_id_seq') PRIMARY KEY,
    address_line text NOT NULL,
    sub_district text NOT NULL,
    city text NOT NULL,
    province text NOT NULL,
    postCode text NOT NULL
);

CREATE TABLE IF NOT EXISTS Person(
    person_id int DEFAULT nextval('person_id_seq') PRIMARY KEY,
    account_id int NOT NULL REFERENCES Account(account_id) ON DELETE CASCADE,
    address_id int NOT NULL REFERENCES Address(address_id) ON DELETE CASCADE,
    name varchar(100) NOT NULL,
    NIK varchar(20) NOT NULL,
    date_of_birth date NOT NULL,
    place_of_birth varchar(20) NOT NULL,
    gender Gender NOT NULL
    );

CREATE TABLE IF NOT EXISTS Office(
    office_id int DEFAULT nextval('office_id_seq') PRIMARY KEY,
    address_id int NOT NULL REFERENCES Address(address_id) ON DELETE CASCADE,
    name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Files(
    files_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content_type text,
    data bytea,
    name text,
    size bigint
);

CREATE TABLE IF NOT EXISTS Documents(
  document_id int DEFAULT nextval('documents_id_seq') PRIMARY KEY,
  ktp_files_id uuid REFERENCES Files(files_id) ON DELETE CASCADE,
  kk_files_id uuid REFERENCES Files(files_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Request(
    request_id int DEFAULT nextval('request_id_seq') PRIMARY KEY,
    document_id int REFERENCES Documents(document_id) ON DELETE CASCADE,
    office_id int REFERENCES Office(office_id) ON DELETE CASCADE,
    person_id int NOT NULL REFERENCES Person(person_id) ON DELETE CASCADE,
    schedule date NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT now(),
    status Status NOT NULL
);

INSERT INTO Account (account_id, username, email, phone_number, password) VALUES
    (DEFAULT, 'user0', 'user0@gmail.com', '084738590000', '0a53a794652a81e07b89b7a6d802ad8fae6c4832ad6351c44784e96c0b101aaa'),
    (DEFAULT, 'user1', 'user1@gmail.com', '084738591111', 'c10c8dad2b0647ff52c0c50ad066f3a766c317d77bdaeade327d2a30bd3e2b84'),
    (DEFAULT, 'user2', 'user2@gmail.com', '084738592222', 'fd23c1a9054322c630559e88e9af991c73bcbf6aa7098e9de9f61cd9ab73ecac'),
    (DEFAULT, 'user3', 'user3@gmail.com', '084738593333', '5324054ac199bc262e23792cd2701b0545103972da304d58a45e6f8ca53e1bd9'),
    (DEFAULT, 'user4', 'user4@gmail.com', '084738594444', 'fe08e4be63d7a7f0aa04fd3c618db3c9a1b5653175d9b62e0b4e17e3598a2491'),
    (DEFAULT, 'user5', 'user5@gmail.com', '084738595555', '223e723b322e0453053e1c4e9fe43ca37ee794c39fa6b878f2f0023a372d3e49'),
    (DEFAULT, 'user6', 'user6@gmail.com', '084738596666', '79dbfec21bd2c34186cf1d787642e3fc0b8831da43d47c6ef54cad1565dd7079'),
    (DEFAULT, 'user7', 'user7@gmail.com', '084738597777', '4a7ef48fdba301ea06d2016d74cd91a46db4e6e27d2eeba3e2639a8b541f34b7'),
    (DEFAULT, 'user8', 'user8@gmail.com', '084738598888', 'dc08f4ad3962abd83f0939f464f93182980713f670d0cbf311ce379a27623a0d'),
    (DEFAULT, 'user9', 'user9@gmail.com', '084738599999', '6fe7d18cf71c768c2694d9577b3389bda4ab8ca9b5ea519a0bb6844bc9e79af7');


