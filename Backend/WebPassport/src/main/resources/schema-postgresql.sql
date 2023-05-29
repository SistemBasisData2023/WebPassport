CREATE TYPE Gender AS ENUM('MALE', 'FEMALE');
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
    account_id int NOT NULL,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    phone_number varchar(20) NOT NULL,
    password text NOT NULL
    );

CREATE TABLE IF NOT EXISTS Admin(
    admin_id int NOT NULL,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    phone_number varchar(20) NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS Address(
    address_id int NOT NULL,
    address_line text NOT NULL,
    sub_district text NOT NULL,
    city text NOT NULL,
    province text NOT NULL,
    postCode text NOT NULL
);

CREATE TABLE IF NOT EXISTS Person(
    person_id int NOT NULL,
    account_id int NOT NULL,
    address_id int NOT NULL,
    name varchar(100) NOT NULL,
    NIK varchar(20) NOT NULL,
    date_of_birth date NOT NULL,
    place_of_birth varchar(20) NOT NULL,
    gender Gender NOT NULL
    );

CREATE TABLE IF NOT EXISTS Office(
    office_id int NOT NULL,
    address_id int NOT NULL,
    name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Request(
    request_id int NOT NULL,
    document_id int,
    office_id int,
    person_id int,
    schedule date NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS Files(
    files_id uuid NOT NULL DEFAULT uuid_generate_v4(),
    content_type text,
    data bytea,
    name text,
    size bigint
);

CREATE TABLE IF NOT EXISTS Documents(
  document_id int NOT NULL,
  ktp_files_id uuid NOT NULL,
  kk_files_id uuid
);


ALTER SEQUENCE account_id_seq OWNED BY Account.account_id;
ALTER SEQUENCE admin_id_seq OWNED BY Admin.admin_id;
ALTER SEQUENCE address_id_seq OWNED BY Address.address_id;
ALTER SEQUENCE person_id_seq OWNED BY Person.person_id;
ALTER SEQUENCE office_id_seq OWNED BY Office.office_id;
ALTER SEQUENCE request_id_seq OWNED BY Request.request_id;
ALTER SEQUENCE documents_id_seq OWNED BY Documents.document_id;


ALTER TABLE account ALTER COLUMN account_id SET DEFAULT nextval('account_id_seq'::regclass);
ALTER TABLE admin ALTER COLUMN admin_id SET DEFAULT nextval('admin_id_seq'::regclass);
ALTER TABLE address ALTER COLUMN address_id SET DEFAULT nextval('address_id_seq'::regclass);
ALTER TABLE person ALTER COLUMN person_id SET DEFAULT nextval('person_id_seq'::regclass);
ALTER TABLE office ALTER COLUMN office_id SET DEFAULT nextval('office_id_seq'::regclass);
ALTER TABLE request ALTER COLUMN request_id SET DEFAULT nextval('request_id_seq'::regclass);
ALTER TABLE documents ALTER COLUMN document_id SET DEFAULT nextval('documents_id_seq'::regclass);

ALTER TABLE files ALTER COLUMN files_id SET DEFAULT uuid_generate_v4();