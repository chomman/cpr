begin;
	CREATE SEQUENCE report_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	
	  -- Table: report
	CREATE TABLE report
	(
	  id bigint NOT NULL,
	  changed timestamp without time zone,
	  code character varying(255),
	  created timestamp without time zone,
	  enabled boolean,
	  content_czech text,
	  content_english text,
	  date_from date,
	  date_to date,
	  id_user_changed_by bigint,
	  id_user_created_by bigint,
	  CONSTRAINT report_pkey PRIMARY KEY (id),
	  CONSTRAINT fk_b1ohit61ufo3b2oq4btbvh35x FOREIGN KEY (id_user_changed_by)
	      REFERENCES users (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_mpap9pjx2xb9qbwuh0scnsvw2 FOREIGN KEY (id_user_created_by)
	      REFERENCES users (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
end;