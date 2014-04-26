begin;
	CREATE TABLE user_info
	(
	  city character varying(50),
	  company_name character varying(50),
	  dic character varying(15),
	  ico character varying(15),
	  phone character varying(25),
	  street character varying(50),
	  zip character varying(6),
	  user_id bigint NOT NULL,
	  CONSTRAINT user_info_pkey PRIMARY KEY (user_id),
	  CONSTRAINT fk_hixwjgx0ynne0cq4tqvoawoda FOREIGN KEY (user_id)
	      REFERENCES users (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE user_info
	  OWNER TO postgres;

	ALTER TABLE users ADD COLUMN change_pass_req_date timestamp without time zone;
	ALTER TABLE users ADD COLUMN change_pass_req_token character varying(60);
	ALTER TABLE users ADD COLUMN registration_validity date;
end;