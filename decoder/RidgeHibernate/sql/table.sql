-- Table: radar_time_index

-- DROP TABLE radar_time_index;

CREATE TABLE radar_time_index
(
  id bigserial NOT NULL,
  radar character(3),
  layer character(3),
  radar_path character varying,
  datetime timestamp with time zone,
  the_geom geometry,
  CONSTRAINT radar_time_index_pkey PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE radar_time_index OWNER TO ridge;