begin;

	CREATE SEQUENCE tag_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 10000
	  START 1
	  CACHE 1;
	  
CREATE TABLE tag
(
  id bigint NOT NULL,
  name character varying(25),
  CONSTRAINT tag_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tag
  OWNER TO postgres;

	  
	  CREATE TABLE webpage_has_tag
(
  webpage_id bigint NOT NULL,
  tag_id bigint NOT NULL,
  CONSTRAINT webpage_has_tag_pkey PRIMARY KEY (webpage_id, tag_id),
  CONSTRAINT fk_erqf0aojl43yr4iqlg92naqka FOREIGN KEY (webpage_id)
      REFERENCES webpage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lffokiq4vllo8d9pm5rvdo2y3 FOREIGN KEY (tag_id)
      REFERENCES tag (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE webpage_has_tag
  OWNER TO postgres;

  
  
end;