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


-- CREATE TABLE IF NOT EXISTS events_athletic
-- (    
--      id uuid NOT NULL,
--      id_events uuid NOT NULL,
--      id_athletics uuid NOT NULL,
--      FOREIGN KEY (id_events) REFERENCES events (id)
--      FOREIGN KEY (id_athletics) REFERENCES athletics (id)
--      PRIMARY KEY (id)
-- );

-- CREATE TABLE IF NOT EXISTS events_attraction
-- (
--      id uuid NOT NULL,
--      id_events uuid NOT NULL,
--      id_attraction uuid NOT NULL,
--      PRIMARY KEY (id)
--      FOREIGN KEY (id_events) REFERENCES events (id)
--      FOREIGN KEY (id_attraction) REFERENCES attractions (id)
-- );

-- CREATE TABLE IF NOT EXISTS events_imagen
-- (
--      id uuid NOT NULL,
--      id_events uuid NOT NULL,
--      id_img uuid NOT NULL,
--      PRIMARY KEY (id)
--      FOREIGN KEY (id_events) REFERENCES events (id)
--      FOREIGN KEY (id_img) REFERENCES images (id)
-- );


-- CREATE TABLE IF NOT EXISTS participants
-- (
--      id uuid NOT NULL,
--      event_id uuid NOT NULL,
--      user_id uuid NOT NULL,
--      PRIMARY KEY (id)
-- );
