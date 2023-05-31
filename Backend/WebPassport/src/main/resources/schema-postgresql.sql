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
    account_id int NOT NULL REFERENCES Account(account_id),
    address_id int NOT NULL REFERENCES Address(address_id),
    name varchar(100) NOT NULL,
    NIK varchar(20) NOT NULL,
    date_of_birth date NOT NULL,
    place_of_birth varchar(20) NOT NULL,
    gender Gender NOT NULL
    );

CREATE TABLE IF NOT EXISTS Office(
    office_id int DEFAULT nextval('office_id_seq') PRIMARY KEY,
    address_id int NOT NULL REFERENCES Address(address_id),
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
  ktp_files_id uuid REFERENCES Files(files_id),
  kk_files_id uuid REFERENCES Files(files_id)
);

CREATE TABLE IF NOT EXISTS Request(
    request_id int DEFAULT nextval('request_id_seq') PRIMARY KEY,
    document_id int REFERENCES Documents(document_id),
    office_id int REFERENCES Office(office_id),
    person_id int NOT NULL REFERENCES Person(person_id),
    schedule date NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT now(),
    status Status NOT NULL
);
