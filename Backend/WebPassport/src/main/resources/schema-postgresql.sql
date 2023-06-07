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
    schedule timestamp NOT NULL,
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

INSERT INTO Admin (admin_id, username, email, phone_number, password) VALUES
                                                                          (DEFAULT, 'Admin1', 'admin1@gmail.com', '084738591111','bfd5f47fbc9d6a5388a42c8cf863556c14bf622e9a19dff4d6c99b23cf6665ad'),
                                                                          (DEFAULT, 'Admin2', 'admin2@gmail.com', '084738592222','61a71eb3683042d08423b546d798eddaca029dd56e67ed0a23d14ec2a366193e'),
                                                                          (DEFAULT, 'Admin3', 'admin3@gmail.com', '084738593333','4b79fe13cafc6b240459c91c515f63df7d6da51b549e52af04dce9c6a02e4f68');

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Engku Putri No.3, Tlk. Tering', 'Batam Kota', 'Kota Batam', 'Kepulauan Riau', '29400')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus TPI Batam', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jalan Gatot Subroto KM. 6,2 No. 268A, Sei Sikambing C. II', 'Medan Helvetia', 'Kota Medan', 'Sumatera Utara', '20123')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus TPI Medan', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Raya Taman Jimbaran No.1, Jimbaran', 'Kuta Sel.', 'Badung', 'Bali', '80235')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus TPI Ngurah Rai', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Soekarno-Hatta International Airport Komplek Perkantoran', 'Benda', 'Tangerang', 'Banten', '19110')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus TPI Soekarno Hatta', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Raya By pass Juanda No.KM.3-4, Manyar, Sedati Agung', 'Sedati', 'Kabupaten Sidoarjo', 'Jawa Timur', '61253')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus TPI Surabaya', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Pos Kota No.4, RT.9/RW.7, Pinangsia', 'Taman Sari', 'Kota Jakarta Barat', 'Daerah Khusus Ibukota Jakarta', '11110')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus Non TPI Jakarta Barat', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Warung Buncit Raya No.207, RT.2/RW.1, Duren Tiga', 'Pancoran', 'Kota Jakarta Selatan', 'Daerah Khusus Ibukota Jakarta', '12760')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Khusus Non TPI Jakarta Selatan', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Jenderal Sudirman No.1, Klandasan Ulu', 'Balikpapan Kota', 'Kota Balikpapan', 'Kalimantan Timur', '75124')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Balikpapan', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Teuku Moh. Daud Beureueh No.82, Beurawe', 'Kuta Alam', 'Kota Banda Aceh', 'Aceh', '24411')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Banda Aceh', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Khatib Sulaiman No. 50, RT. 03 / RW. 07, Kel. Lolong Belanti, South Ulak Karang', 'Padang Utara', 'Padang', 'Sumatra Barat', '25135')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Padang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Arif Rahman Hakim No.63, Simpang IV Sipin', 'Telanaipura', 'Kota Jambi', 'Jambi', '36124')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Jambi', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Pembangunan No.23, Padang Harapan', 'Gading Cemp.', 'Kota Bengkulu', 'Bengkulu', '38225')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Bengkulu', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Hj. Haniah No.mor 3, Gulak Galik', 'Tlk. Betung Utara', 'Kota Bandar Lampung', 'Lampung', '35214')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Bandar Lampung', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Selindung Baru', 'Gabek', 'Pangkal Pinang', 'Bangka Belitung', '33117')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Pangkal Pinang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Surapati No.82, Cihaur Geulis', 'Cibeunying Kaler', 'Kota Bandung', 'Jawa Barat', '40122')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Bandung', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'JL. D.I. PANJAITAN NO.3 KEL. DANGIN PURI KELOD', 'DENPASAR TIMUR', 'Kota Denpasar', 'Bali', '80235')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Denpasar', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Bekasi Tim. Raya No.169 Cipinang Besar Utara', 'Jatinegara', 'Kota Jakarta Timur', 'Daerah Khusus Ibukota Jakarta', '13140')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Jakarta Timur', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Boulevard Artha Gading No.80, RT.18/RW.8 West Kelapa Gading', 'Kelapa Gading', 'North Jakarta City', 'Jakarta', '14240')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Jakarta Utara', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Perintis Kemerdekaan No.KM.13 Kapasa', 'Tamalanrea', 'Kota Makassar', 'Sulawesi Selatan', '90243')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Makassar', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Raden Panji Suroso No.4, Polowijen', 'Blimbing', 'Kota Malang', 'Jawa Timur', '65126')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Malang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. 17 Agustus, Manado Teling Atas', 'Wanea', 'Kota Manado', 'Sulawesi Utara', '95119')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Manado', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Udayana No.2, Monjok Barat', 'Selaparang', 'Kota Mataram', 'Nusa Tenggara Barat', '83122')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Mataram', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jalan Bumi III, Penfui Oesapa Selatan', 'Lima', 'Kota Kupang', 'Nusa Tenggara Timur', '85119')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Kupang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. A. Yani No.KM 22, Landasan Ulin Bar.', 'Liang Anggang', 'Kota Banjar Baru', 'Kalimantan Selatan', '70249')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Banjarmasin', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Pangeran Ratu No.1, Deapanulu', 'Kecamatan Seberang Ulu I', 'Kota Palembang', 'Sumatera Selatan', '30252')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Palembang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. KH. Ahmad Dahlan, Pulau Karam', 'Sukajadi', 'Kota Pekanbaru', 'Riau', '28127')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Pekanbaru', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Perintis Kemerdekaan No.KM.13, Kapasa', 'Tamalanrea', 'Kota Makassar', 'Sulawesi Selatan', '90243')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Polonia', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Letnan Jendral Sutoyo No.122, RT.01/RW.02, Parit Tokaya', 'Pontianak Selatan', 'Kota Pontianak', 'Kalimantan Barat', '78121')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Pontianak', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. R.A. Kartini No.53, Lolu Selatan', 'Palu Timur', 'Kota Palu', 'Sulawesi Tengah', '94111')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Palu', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Jend. Ahmad Yani No.101, Bonggoeya', 'Wua-Wua', 'Kota Kendari', 'Sulawesi Tenggara', '93117')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Kendari', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Brigjen Piola Isa No.214, Dulomo Selatan', 'Kota Utara', 'Kota Gorontalo', 'Gorontalo', '96123')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Gorontalo', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. DR. Kayadoe No.48A, Kel Kudamati', 'Nusaniwe', 'Kota Ambon', 'Maluku', '97118')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Ambon', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Siliwangi No.514, Kembangarum', 'Semarang Barat', 'Kota Semarang', 'Jawa Tengah', '50148')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Semarang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Adi Sucipto No.8, Blukukan, Blulukan', 'Colomadu', 'Kabupaten Karanganyar', 'Jawa Tengah', '57174')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Surakarta', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Raya Darmo Indah No.21, Tandes Kidul', 'Tandes', 'Kota SBY', 'Jawa Timur', '60186')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Tanjung Perak', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Melati No.124 A, RT.1/RW.12, Rawabadak Utara', 'Koja', 'Kota Jakarta Utara', 'Daerah Khusus Ibukota Jakarta', '14230')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Tanjung Priok', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. SKSD Palapa No.338, RT.001/RW.RW, Kalumpang', 'Ternate Tengah', 'Kota Ternate', 'Maluku Utara', '97722')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Ternate', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Raya Solo - Yogyakarta KM.10, Karangploso', 'Maguwoharjo', 'Kabupaten Sleman', 'Daerah Istimewa Yogyakarta', '55282')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Yogyakarta', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Percetakan Negara No.15, Gurabesi', 'Jayapura Utara', 'Kota Jayapura', 'Papua', '99111')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Jayapura', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. A. Yani No.19, RT.03/RW.02', 'Tanah Sereal', 'Kota Bogor', 'Jawa Barat', '16161')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Non TPI Bogor', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Merpati No.3, RW.10, Gn. Sahari Utara', 'Kemayoran', 'Kota Jakarta Pusat', 'Daerah Khusus Ibukota Jakarta', '10720')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Non TPI Jakarta Pusat', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. G. Obos No.10, Menteng', 'Jekan Raya', 'Kota Palangka Raya', 'Kalimantan Tengah', '73111')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Non TPI Palangka Raya', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Taman Makam Pahlawan Taruna No.10, RT.006/RW.008', 'Sukasari', 'Tangerang', 'Banten', '15111')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Non TPI Tangerang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Perjuangan No.100, RT.002/RW.001, Tlk. Pucung', 'Bekasi Utara', 'Kota Bekasi', 'Jawa Barat', '13410')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I Non TPI Bekasi', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Boulevard Grand Depok City, Kalimulya', 'Cilodong', 'Kota Depok', 'Jawa Barat', '16414')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas II Non TPI Depok', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Abdul Malik Pattana Endeng, Rangas', 'Simboro Dan Kepulauan', 'Kabupaten Mamuju', 'Sulawesi Barat', '91511')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas II Mamuju', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. C. Heatubun, Kwamki', 'Mimika Baru', 'Mimika', 'Papua', '99910')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas II TPI Timika', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jalan Logpond Arfai, Manokwari Sel., Papua Bar., Anday', 'Distrik Manokwari', 'Kabupaten Manokwari', 'Papua Barat', '98315')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas II TPI Manokwari', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Ahmad Yani No.31, Sei Jang', 'Tanjung Pinang Kota', 'Kota Tanjung Pinang', 'Kepulauan Riau', '29124')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Tanjung Pinang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Ir. H. Juanda No.45, Sidodadi', 'Samarinda Ulu', 'Kota Samarinda', 'Kalimantan Timur', '75124')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Samarinda', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Warung Jaud No. 82 RT/ RW 03/11, Kaligandu', 'Serang', 'Kota Serang', 'Banten', '42151')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas I TPI Serang', address_id
FROM i;

WITH i AS (
INSERT INTO Address (address_id, address_line, sub_district, city, province, postcode)
VALUES (DEFAULT, 'Jl. Dr Sam Ratulangi, Bitung Barat Dua', 'Maesa', 'Bitung', 'Sulawesi Utara', '95511')
    RETURNING address_id
    )
INSERT INTO Office (name, address_id)
SELECT 'Kantor Imigrasi Kelas II TPI Bitung', address_id
FROM i;


