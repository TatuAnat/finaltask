--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-12-28 12:17:43

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4861 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 25069)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id integer NOT NULL,
    balance double precision NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 25068)
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.accounts ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 25074)
-- Name: operations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operations (
    id integer NOT NULL,
    amount double precision NOT NULL,
    "timestamp" timestamp(6) without time zone NOT NULL,
    type character varying(255) NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT operations_type_check CHECK (((type)::text = ANY ((ARRAY['PUT'::character varying, 'TAKE'::character varying])::text[])))
);


ALTER TABLE public.operations OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 25067)
-- Name: operations_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.operations_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.operations_seq OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 25082)
-- Name: transfers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transfers (
    id integer NOT NULL,
    amount double precision NOT NULL,
    from_user_id integer NOT NULL,
    "timestamp" timestamp(6) without time zone NOT NULL,
    to_user_id integer NOT NULL
);


ALTER TABLE public.transfers OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 25087)
-- Name: transfers_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transfers_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transfers_seq OWNER TO postgres;

--
-- TOC entry 4852 (class 0 OID 25069)
-- Dependencies: 217
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (id, balance, user_id) FROM stdin;
2	60	777
1	360	123456
\.


--
-- TOC entry 4853 (class 0 OID 25074)
-- Dependencies: 218
-- Data for Name: operations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.operations (id, amount, "timestamp", type, user_id) FROM stdin;
102	512	2024-12-23 20:57:49.861428	PUT	123456
103	5	2024-12-23 20:57:52.326688	PUT	123456
104	3	2024-12-23 20:57:54.934706	PUT	123456
152	100	2024-12-24 10:16:05.776949	TAKE	123456
202	20	2024-12-28 11:47:13.173674	TAKE	123456
203	20	2024-12-28 11:47:13.175763	PUT	777
204	20	2024-12-28 11:51:06.809164	TAKE	123456
205	20	2024-12-28 11:51:06.811201	PUT	777
252	20	2024-12-28 11:53:22.323692	TAKE	123456
253	20	2024-12-28 11:53:22.326807	PUT	777
254	100	2024-12-28 11:56:41.303584	TAKE	123456
302	100	2024-12-28 11:59:36.259039	PUT	123456
\.


--
-- TOC entry 4854 (class 0 OID 25082)
-- Dependencies: 219
-- Data for Name: transfers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transfers (id, amount, from_user_id, "timestamp", to_user_id) FROM stdin;
1	20	123456	2024-12-28 11:47:13.124893	777
2	20	123456	2024-12-28 11:51:06.799419	777
52	20	123456	2024-12-28 11:53:22.273651	777
\.


--
-- TOC entry 4862 (class 0 OID 0)
-- Dependencies: 216
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_id_seq', 1, true);


--
-- TOC entry 4863 (class 0 OID 0)
-- Dependencies: 215
-- Name: operations_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.operations_seq', 351, true);


--
-- TOC entry 4864 (class 0 OID 0)
-- Dependencies: 220
-- Name: transfers_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transfers_seq', 101, true);


--
-- TOC entry 4700 (class 2606 OID 25073)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 25079)
-- Name: operations operations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_pkey PRIMARY KEY (id);


--
-- TOC entry 4706 (class 2606 OID 25086)
-- Name: transfers transfers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfers
    ADD CONSTRAINT transfers_pkey PRIMARY KEY (id);


--
-- TOC entry 4702 (class 2606 OID 25081)
-- Name: accounts uke4w4av1wrhanry7t6mxt42nou; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT uke4w4av1wrhanry7t6mxt42nou UNIQUE (user_id);


-- Completed on 2024-12-28 12:17:43

--
-- PostgreSQL database dump complete
--

