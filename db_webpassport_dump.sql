--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- Name: gender; Type: TYPE; Schema: public; Owner: farras2003
--

CREATE TYPE public.gender AS ENUM (
    'MALE',
    'FEMALE'
);


ALTER TYPE public.gender OWNER TO farras2003;

--
-- Name: status; Type: TYPE; Schema: public; Owner: farras2003
--

CREATE TYPE public.status AS ENUM (
    'ACCEPTED',
    'DECLINED',
    'PENDING'
);


ALTER TYPE public.status OWNER TO farras2003;

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO farras2003;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.account (
    account_id integer DEFAULT nextval('public.account_id_seq'::regclass) NOT NULL,
    username character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    phone_number character varying(20) NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.account OWNER TO farras2003;

--
-- Name: address_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.address_id_seq
    AS integer
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.address_id_seq OWNER TO farras2003;

--
-- Name: address; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.address (
    address_id integer DEFAULT nextval('public.address_id_seq'::regclass) NOT NULL,
    address_line text NOT NULL,
    sub_district text NOT NULL,
    city text NOT NULL,
    province text NOT NULL,
    postcode text NOT NULL
);


ALTER TABLE public.address OWNER TO farras2003;

--
-- Name: admin_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.admin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.admin_id_seq OWNER TO farras2003;

--
-- Name: admin; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.admin (
    admin_id integer DEFAULT nextval('public.admin_id_seq'::regclass) NOT NULL,
    username character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    phone_number character varying(20) NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.admin OWNER TO farras2003;

--
-- Name: documents_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.documents_id_seq
    AS integer
    START WITH 10000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.documents_id_seq OWNER TO farras2003;

--
-- Name: documents; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.documents (
    document_id integer DEFAULT nextval('public.documents_id_seq'::regclass) NOT NULL,
    ktp_files_id uuid,
    kk_files_id uuid
);


ALTER TABLE public.documents OWNER TO farras2003;

--
-- Name: files; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.files (
    files_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    content_type text,
    data bytea,
    name text,
    size bigint
);


ALTER TABLE public.files OWNER TO farras2003;

--
-- Name: office_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.office_id_seq
    AS integer
    START WITH 500
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.office_id_seq OWNER TO farras2003;

--
-- Name: office; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.office (
    office_id integer DEFAULT nextval('public.office_id_seq'::regclass) NOT NULL,
    address_id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.office OWNER TO farras2003;

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.person_id_seq
    AS integer
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO farras2003;

--
-- Name: person; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.person (
    person_id integer DEFAULT nextval('public.person_id_seq'::regclass) NOT NULL,
    account_id integer NOT NULL,
    address_id integer NOT NULL,
    name character varying(100) NOT NULL,
    nik character varying(20) NOT NULL,
    date_of_birth date NOT NULL,
    place_of_birth character varying(20) NOT NULL,
    gender public.gender NOT NULL
);


ALTER TABLE public.person OWNER TO farras2003;

--
-- Name: request_id_seq; Type: SEQUENCE; Schema: public; Owner: farras2003
--

CREATE SEQUENCE public.request_id_seq
    AS integer
    START WITH 5000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.request_id_seq OWNER TO farras2003;

--
-- Name: request; Type: TABLE; Schema: public; Owner: farras2003
--

CREATE TABLE public.request (
    request_id integer DEFAULT nextval('public.request_id_seq'::regclass) NOT NULL,
    document_id integer,
    office_id integer,
    person_id integer NOT NULL,
    schedule timestamp without time zone NOT NULL,
    "timestamp" timestamp without time zone DEFAULT now() NOT NULL,
    status public.status NOT NULL
);


ALTER TABLE public.request OWNER TO farras2003;

--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.account (account_id, username, email, phone_number, password) FROM stdin;
10	user0	user0@gmail.com	084738590000	0a53a794652a81e07b89b7a6d802ad8fae6c4832ad6351c44784e96c0b101aaa
11	user1	user1@gmail.com	084738591111	c10c8dad2b0647ff52c0c50ad066f3a766c317d77bdaeade327d2a30bd3e2b84
16	user6	user6@gmail.com	084738596666	79dbfec21bd2c34186cf1d787642e3fc0b8831da43d47c6ef54cad1565dd7079
18	user8	user8@gmail.com	084738598888	dc08f4ad3962abd83f0939f464f93182980713f670d0cbf311ce379a27623a0d
19	user9	user9@gmail.com	084738599999	6fe7d18cf71c768c2694d9577b3389bda4ab8ca9b5ea519a0bb6844bc9e79af7
20	user10	user10@gmail.com	081938291010	931108dbc9a208ac005561de5fccc8c715783d4254a65b0b2583fc6ccfb33d25
14	user4	user4@gmail.com	084738594444	fe08e4be63d7a7f0aa04fd3c618db3c9a1b5653175d9b62e0b4e17e3598a2491
12	user2	user2@gmail.com	084738592222	fd23c1a9054322c630559e88e9af991c73bcbf6aa7098e9de9f61cd9ab73ecac
13	user3	user3@gmail.com	084738593333	5324054ac199bc262e23792cd2701b0545103972da304d58a45e6f8ca53e1bd9
17	user7	user7@gmail.com	084738597777	4a7ef48fdba301ea06d2016d74cd91a46db4e6e27d2eeba3e2639a8b541f34b7
22	user11	user11@gmail.com	081936841010	9cf809156466c95e2cdd1deef0cce7dda9aa27979d8ae6ff73811491e2392383
15	user5	user5Edited@gmail.com	09583555555	223e723b322e0453053e1c4e9fe43ca37ee794c39fa6b878f2f0023a372d3e49
\.


--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.address (address_id, address_line, sub_district, city, province, postcode) FROM stdin;
1000	Jl. Engku Putri No.3, Tlk. Tering	Batam Kota	Kota Batam	Kepulauan Riau	29400
1001	Jalan Gatot Subroto KM. 6,2 No. 268A, Sei Sikambing C. II	Medan Helvetia	Kota Medan	Sumatera Utara	20123
1002	Jl. Raya Taman Jimbaran No.1, Jimbaran	Kuta Sel.	Badung	Bali	80235
1003	Jl. Soekarno-Hatta International Airport Komplek Perkantoran	Benda	Tangerang	Banten	19110
1004	Jl. Raya By pass Juanda No.KM.3-4, Manyar, Sedati Agung	Sedati	Kabupaten Sidoarjo	Jawa Timur	61253
1005	Jl. Pos Kota No.4, RT.9/RW.7, Pinangsia	Taman Sari	Kota Jakarta Barat	Daerah Khusus Ibukota Jakarta	11110
1006	Jl. Warung Buncit Raya No.207, RT.2/RW.1, Duren Tiga	Pancoran	Kota Jakarta Selatan	Daerah Khusus Ibukota Jakarta	12760
1007	Jl. Jenderal Sudirman No.1, Klandasan Ulu	Balikpapan Kota	Kota Balikpapan	Kalimantan Timur	75124
1008	Jl. Teuku Moh. Daud Beureueh No.82, Beurawe	Kuta Alam	Kota Banda Aceh	Aceh	24411
1009	Jl. Khatib Sulaiman No. 50, RT. 03 / RW. 07, Kel. Lolong Belanti, South Ulak Karang	Padang Utara	Padang	Sumatra Barat	25135
1010	Jl. Arif Rahman Hakim No.63, Simpang IV Sipin	Telanaipura	Kota Jambi	Jambi	36124
1011	Jl. Pembangunan No.23, Padang Harapan	Gading Cemp.	Kota Bengkulu	Bengkulu	38225
1012	Jl. Hj. Haniah No.mor 3, Gulak Galik	Tlk. Betung Utara	Kota Bandar Lampung	Lampung	35214
1013	Jl. Selindung Baru	Gabek	Pangkal Pinang	Bangka Belitung	33117
1014	Jl. Surapati No.82, Cihaur Geulis	Cibeunying Kaler	Kota Bandung	Jawa Barat	40122
1015	JL. D.I. PANJAITAN NO.3 KEL. DANGIN PURI KELOD	DENPASAR TIMUR	Kota Denpasar	Bali	80235
1016	Jl. Bekasi Tim. Raya No.169 Cipinang Besar Utara	Jatinegara	Kota Jakarta Timur	Daerah Khusus Ibukota Jakarta	13140
1017	Jl. Boulevard Artha Gading No.80, RT.18/RW.8 West Kelapa Gading	Kelapa Gading	North Jakarta City	Jakarta	14240
1018	Jl. Perintis Kemerdekaan No.KM.13 Kapasa	Tamalanrea	Kota Makassar	Sulawesi Selatan	90243
1019	Jl. Raden Panji Suroso No.4, Polowijen	Blimbing	Kota Malang	Jawa Timur	65126
1020	Jl. 17 Agustus, Manado Teling Atas	Wanea	Kota Manado	Sulawesi Utara	95119
1021	Jl. Udayana No.2, Monjok Barat	Selaparang	Kota Mataram	Nusa Tenggara Barat	83122
1023	Jl. A. Yani No.KM 22, Landasan Ulin Bar.	Liang Anggang	Kota Banjar Baru	Kalimantan Selatan	70249
1024	Jl. Pangeran Ratu No.1, Deapanulu	Kecamatan Seberang Ulu I	Kota Palembang	Sumatera Selatan	30252
1025	Jl. KH. Ahmad Dahlan, Pulau Karam	Sukajadi	Kota Pekanbaru	Riau	28127
1026	Jl. Perintis Kemerdekaan No.KM.13, Kapasa	Tamalanrea	Kota Makassar	Sulawesi Selatan	90243
1027	Jl. Letnan Jendral Sutoyo No.122, RT.01/RW.02, Parit Tokaya	Pontianak Selatan	Kota Pontianak	Kalimantan Barat	78121
1028	Jl. R.A. Kartini No.53, Lolu Selatan	Palu Timur	Kota Palu	Sulawesi Tengah	94111
1029	Jl. Jend. Ahmad Yani No.101, Bonggoeya	Wua-Wua	Kota Kendari	Sulawesi Tenggara	93117
1030	Jl. Brigjen Piola Isa No.214, Dulomo Selatan	Kota Utara	Kota Gorontalo	Gorontalo	96123
1031	Jl. DR. Kayadoe No.48A, Kel Kudamati	Nusaniwe	Kota Ambon	Maluku	97118
1032	Jl. Siliwangi No.514, Kembangarum	Semarang Barat	Kota Semarang	Jawa Tengah	50148
1033	Jl. Adi Sucipto No.8, Blukukan, Blulukan	Colomadu	Kabupaten Karanganyar	Jawa Tengah	57174
1034	Jl. Raya Darmo Indah No.21, Tandes Kidul	Tandes	Kota SBY	Jawa Timur	60186
1035	Jl. Melati No.124 A, RT.1/RW.12, Rawabadak Utara	Koja	Kota Jakarta Utara	Daerah Khusus Ibukota Jakarta	14230
1036	Jl. SKSD Palapa No.338, RT.001/RW.RW, Kalumpang	Ternate Tengah	Kota Ternate	Maluku Utara	97722
1037	Jl. Raya Solo - Yogyakarta KM.10, Karangploso	Maguwoharjo	Kabupaten Sleman	Daerah Istimewa Yogyakarta	55282
1038	Jl. Percetakan Negara No.15, Gurabesi	Jayapura Utara	Kota Jayapura	Papua	99111
1039	Jl. A. Yani No.19, RT.03/RW.02	Tanah Sereal	Kota Bogor	Jawa Barat	16161
1040	Jl. Merpati No.3, RW.10, Gn. Sahari Utara	Kemayoran	Kota Jakarta Pusat	Daerah Khusus Ibukota Jakarta	10720
1041	Jl. G. Obos No.10, Menteng	Jekan Raya	Kota Palangka Raya	Kalimantan Tengah	73111
1042	Jl. Taman Makam Pahlawan Taruna No.10, RT.006/RW.008	Sukasari	Tangerang	Banten	15111
1043	Jl. Perjuangan No.100, RT.002/RW.001, Tlk. Pucung	Bekasi Utara	Kota Bekasi	Jawa Barat	13410
1044	Jl. Boulevard Grand Depok City, Kalimulya	Cilodong	Kota Depok	Jawa Barat	16414
1045	Jl. Abdul Malik Pattana Endeng, Rangas	Simboro Dan Kepulauan	Kabupaten Mamuju	Sulawesi Barat	91511
1046	Jl. C. Heatubun, Kwamki	Mimika Baru	Mimika	Papua	99910
1047	Jalan Logpond Arfai, Manokwari Sel., Papua Bar., Anday	Distrik Manokwari	Kabupaten Manokwari	Papua Barat	98315
1048	Jl. Ahmad Yani No.31, Sei Jang	Tanjung Pinang Kota	Kota Tanjung Pinang	Kepulauan Riau	29124
1049	Jl. Ir. H. Juanda No.45, Sidodadi	Samarinda Ulu	Kota Samarinda	Kalimantan Timur	75124
1050	Jl. Warung Jaud No. 82 RT/ RW 03/11, Kaligandu	Serang	Kota Serang	Banten	42151
1051	Jl. Dr Sam Ratulangi, Bitung Barat Dua	Maesa	Bitung	Sulawesi Utara	95511
1052	Jl.Somewhere	Somewhere	Depok	Jawa Barat	10382
1054	J. Somewhere	Dunno	Depok	Jawa Barat	37291
1053	Jl. Somewhere	Dunno	Ngawi	Jawa Barat	21293
1055	Jl Sudirman	Rumbai	Pekanbaru	Riau	26812
1056	Jl. Somewhere Else	Somewhere	Pekanbaru	Riau	22888
\.


--
-- Data for Name: admin; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.admin (admin_id, username, email, phone_number, password) FROM stdin;
1	Admin1	admin1@gmail.com	084738591111	bfd5f47fbc9d6a5388a42c8cf863556c14bf622e9a19dff4d6c99b23cf6665ad
2	Admin2	admin2@gmail.com	084738592222	61a71eb3683042d08423b546d798eddaca029dd56e67ed0a23d14ec2a366193e
3	Admin3	admin3@gmail.com	084738593333	4b79fe13cafc6b240459c91c515f63df7d6da51b549e52af04dce9c6a02e4f68
\.


--
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.documents (document_id, ktp_files_id, kk_files_id) FROM stdin;
10000	2b44a082-957e-4547-9819-9107f5944c88	61df2fa0-1427-4775-8176-be16078def78
10001	03dce5aa-925f-46eb-a6f2-62b93e4f4846	b908e4ef-0632-4ff0-8d89-114905f102da
10002	b794eae1-bf39-4830-98b3-584d1c0ad500	4212a53b-29c3-4851-bbde-9eaa966a3687
10003	dd4fbf7f-2a92-48ea-8886-d8fd411dc71d	f347c7a6-282d-4ce8-94ec-af6604069fff
10004	69eff326-4f34-4909-9cc7-42726c14bdd5	77d361a0-3556-4f0e-b793-a49284cc450d
10005	7d676b98-3d5b-4ca8-b4a2-501c6672344b	74ef09ab-3fd9-4749-a6e7-62097cfeea79
10006	6ceb7e72-54db-4546-bad0-0b6ced7fb79d	625d1e48-f34a-45c7-aeae-cc3a05d8f31c
10007	74a417c9-9e32-4f1f-8f49-7f6528edb2e9	6d71e1ee-adad-461e-a4dd-ad2e558ff890
10008	adb5b889-f69c-4d5d-9aef-06cc3b6567f8	373f9f28-6c44-4d39-859f-5c48dab162c2
10009	e37f8ef1-4c7d-4f54-8036-c2b2135263de	efbf580c-7441-4903-9c5b-c052b7808c9c
10010	91644586-f140-4595-ba5b-fbf886126206	f1962dae-a15d-4fdf-acf9-8313bd478458
10011	90f6d037-d9f9-4c89-aa41-b9546dd1e8fd	07164aee-406c-40aa-b0b4-250abe995504
10012	175a686e-8a40-417a-a8a8-7b9704796ea5	731a9935-013b-4b35-a630-7540fcc28bca
\.


--
-- Data for Name: office; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.office (office_id, address_id, name) FROM stdin;
500	1000	Kantor Imigrasi Kelas I Khusus TPI Batam
501	1001	Kantor Imigrasi Kelas I Khusus TPI Medan
502	1002	Kantor Imigrasi Kelas I Khusus TPI Ngurah Rai
503	1003	Kantor Imigrasi Kelas I Khusus TPI Soekarno Hatta
504	1004	Kantor Imigrasi Kelas I Khusus TPI Surabaya
505	1005	Kantor Imigrasi Kelas I Khusus Non TPI Jakarta Barat
506	1006	Kantor Imigrasi Kelas I Khusus Non TPI Jakarta Selatan
507	1007	Kantor Imigrasi Kelas I TPI Balikpapan
508	1008	Kantor Imigrasi Kelas I TPI Banda Aceh
509	1009	Kantor Imigrasi Kelas I TPI Padang
510	1010	Kantor Imigrasi Kelas I TPI Jambi
511	1011	Kantor Imigrasi Kelas I TPI Bengkulu
512	1012	Kantor Imigrasi Kelas I TPI Bandar Lampung
513	1013	Kantor Imigrasi Kelas I TPI Pangkal Pinang
514	1014	Kantor Imigrasi Kelas I TPI Bandung
515	1015	Kantor Imigrasi Kelas I TPI Denpasar
516	1016	Kantor Imigrasi Kelas I TPI Jakarta Timur
517	1017	Kantor Imigrasi Kelas I TPI Jakarta Utara
518	1018	Kantor Imigrasi Kelas I TPI Makassar
519	1019	Kantor Imigrasi Kelas I TPI Malang
520	1020	Kantor Imigrasi Kelas I TPI Manado
521	1021	Kantor Imigrasi Kelas I TPI Mataram
523	1023	Kantor Imigrasi Kelas I TPI Banjarmasin
524	1024	Kantor Imigrasi Kelas I TPI Palembang
525	1025	Kantor Imigrasi Kelas I TPI Pekanbaru
526	1026	Kantor Imigrasi Kelas I TPI Polonia
527	1027	Kantor Imigrasi Kelas I TPI Pontianak
528	1028	Kantor Imigrasi Kelas I TPI Palu
529	1029	Kantor Imigrasi Kelas I TPI Kendari
530	1030	Kantor Imigrasi Kelas I TPI Gorontalo
531	1031	Kantor Imigrasi Kelas I TPI Ambon
532	1032	Kantor Imigrasi Kelas I TPI Semarang
533	1033	Kantor Imigrasi Kelas I TPI Surakarta
534	1034	Kantor Imigrasi Kelas I TPI Tanjung Perak
535	1035	Kantor Imigrasi Kelas I TPI Tanjung Priok
536	1036	Kantor Imigrasi Kelas I TPI Ternate
537	1037	Kantor Imigrasi Kelas I TPI Yogyakarta
538	1038	Kantor Imigrasi Kelas I TPI Jayapura
539	1039	Kantor Imigrasi Kelas I Non TPI Bogor
540	1040	Kantor Imigrasi Kelas I Non TPI Jakarta Pusat
541	1041	Kantor Imigrasi Kelas I Non TPI Palangka Raya
542	1042	Kantor Imigrasi Kelas I Non TPI Tangerang
543	1043	Kantor Imigrasi Kelas I Non TPI Bekasi
544	1044	Kantor Imigrasi Kelas II Non TPI Depok
545	1045	Kantor Imigrasi Kelas II Mamuju
546	1046	Kantor Imigrasi Kelas II TPI Timika
547	1047	Kantor Imigrasi Kelas II TPI Manokwari
548	1048	Kantor Imigrasi Kelas I TPI Tanjung Pinang
549	1049	Kantor Imigrasi Kelas I TPI Samarinda
550	1050	Kantor Imigrasi Kelas I TPI Serang
551	1051	Kantor Imigrasi Kelas II TPI Bitung
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.person (person_id, account_id, address_id, name, nik, date_of_birth, place_of_birth, gender) FROM stdin;
100	12	1052	User Dua	203824837482222	1989-02-12	Depok	MALE
102	13	1054	User Tiga	20382483729173333	2001-05-27	Depok	FEMALE
103	17	1055	User Tujuh	2937491738471777	2002-03-12	Pekanbaru	MALE
104	10	1056	User Nol	201928493310000	1998-08-08	Pekanbaru	MALE
101	14	1053	User Empat Edit	201928493314141	1990-06-12	Jakarta	MALE
\.


--
-- Data for Name: request; Type: TABLE DATA; Schema: public; Owner: farras2003
--

COPY public.request (request_id, document_id, office_id, person_id, schedule, "timestamp", status) FROM stdin;
5000	10000	544	100	2023-06-15 13:00:00	2023-06-07 14:24:43.904995	DECLINED
5004	10004	544	102	2023-06-29 08:30:00	2023-06-09 01:45:45.678261	ACCEPTED
5005	10005	544	102	2023-06-14 09:00:00	2023-06-09 01:46:23.353177	ACCEPTED
5003	10003	503	101	2023-06-22 10:00:00	2023-06-09 01:40:08.35281	ACCEPTED
5002	10002	544	100	2023-06-09 21:30:00	2023-06-07 15:02:18.77212	DECLINED
5007	10007	544	100	2023-06-15 12:00:00	2023-06-09 14:37:11.118626	ACCEPTED
5008	10008	544	100	2023-06-14 11:00:00	2023-06-10 04:44:33.800192	ACCEPTED
5006	10006	511	100	2023-06-15 13:30:00	2023-06-09 07:06:50.464358	ACCEPTED
5009	10009	514	101	2023-06-15 14:30:00	2023-06-10 11:20:06.157606	ACCEPTED
5010	10010	525	103	2023-06-21 13:00:00	2023-06-10 12:18:40.375733	DECLINED
5011	10011	544	100	2023-06-16 21:00:00	2023-06-10 16:19:16.631683	PENDING
5001	10001	544	100	2023-06-15 10:00:00	2023-06-07 14:42:59.817352	DECLINED
5012	10012	500	102	2023-06-10 00:00:00	2023-06-10 16:38:21.047368	PENDING
\.


--
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.account_id_seq', 22, true);


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.address_id_seq', 1057, true);


--
-- Name: admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.admin_id_seq', 3, true);


--
-- Name: documents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.documents_id_seq', 10012, true);


--
-- Name: office_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.office_id_seq', 552, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.person_id_seq', 104, true);


--
-- Name: request_id_seq; Type: SEQUENCE SET; Schema: public; Owner: farras2003
--

SELECT pg_catalog.setval('public.request_id_seq', 5012, true);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_id);


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);


--
-- Name: admin admin_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (admin_id);


--
-- Name: documents documents_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (document_id);


--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (files_id);


--
-- Name: office office_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.office
    ADD CONSTRAINT office_pkey PRIMARY KEY (office_id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- Name: request request_pkey; Type: CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pkey PRIMARY KEY (request_id);


--
-- Name: documents documents_kk_files_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_kk_files_id_fkey FOREIGN KEY (kk_files_id) REFERENCES public.files(files_id) ON DELETE CASCADE;


--
-- Name: documents documents_ktp_files_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_ktp_files_id_fkey FOREIGN KEY (ktp_files_id) REFERENCES public.files(files_id) ON DELETE CASCADE;


--
-- Name: office office_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.office
    ADD CONSTRAINT office_address_id_fkey FOREIGN KEY (address_id) REFERENCES public.address(address_id) ON DELETE CASCADE;


--
-- Name: person person_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE;


--
-- Name: person person_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_address_id_fkey FOREIGN KEY (address_id) REFERENCES public.address(address_id) ON DELETE CASCADE;


--
-- Name: request request_document_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_document_id_fkey FOREIGN KEY (document_id) REFERENCES public.documents(document_id) ON DELETE CASCADE;


--
-- Name: request request_office_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_office_id_fkey FOREIGN KEY (office_id) REFERENCES public.office(office_id) ON DELETE CASCADE;


--
-- Name: request request_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: farras2003
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.person(person_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

