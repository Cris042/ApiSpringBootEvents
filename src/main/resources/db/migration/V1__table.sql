CREATE TABLE IF NOT EXISTS events
(
     id uuid NOT NULL,
     name character varying(64) NOT NULL,
     description character varying(255) NOT NULL,
     date character varying(64) NOT NULL,
     creation_date timestamp without time zone NOT NULL,   
     payment character varying(64) NOT NULL,
     id_place uuid NOT NULL,
     id_category uuid NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS places
(
     id uuid NOT NULL,
     name character varying(64) NOT NULL,
     capacity character varying(64) NOT NULL,
     latitude character varying(512) NOT NULL,
     longitude character varying(512) NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categorys
(
     id uuid NOT NULL,
     name character varying(64) NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS athletics
(
     id uuid NOT NULL,
     name character varying(64) NOT NULL,
     description character varying(255) NOT NULL,
     course character varying(64) NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS images
(
     id uuid NOT NULL,
     name character varying(255) UNIQUE NOT NULL,
     url character varying(512) UNIQUE NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS attractions
(
     id uuid NOT NULL,
     name character varying(64) NOT NULL,
     description character varying(255) NOT NULL,
     PRIMARY KEY (id)
);


