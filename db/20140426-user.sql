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
	
	CREATE SEQUENCE portal_order_id_seq
	  INCREMENT 1
	  MINVALUE 11000
	  MAXVALUE 9223372036854775807
	  START 11050
	  CACHE 1;
	
	  CREATE SEQUENCE portal_service_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	
	  
	  CREATE TABLE portal_order
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  code character varying(255),
  created timestamp without time zone,
  enabled boolean,
  city character varying(50),
  company_name character varying(50),
  date_of_activation date,
  dic character varying(15),
  email character varying(50),
  email_sent boolean,
  first_name character varying(50) NOT NULL,
  ico character varying(8),
  ip_address character varying(45),
  last_name character varying(50) NOT NULL,
  note character varying(300),
  order_status character varying(25),
  phone character varying(25),
  price numeric(19,2) NOT NULL,
  street character varying(50),
  vat numeric(19,2),
  zip character varying(6),
  id_user_changed_by bigint,
  id_user_created_by bigint,
  portal_service bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT portal_order_pkey PRIMARY KEY (id),
  CONSTRAINT fk_8rb6uy7cjjq6rg8kb0a6f9q9c FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_go7832hhood3iqaqs3gqwkpr7 FOREIGN KEY (portal_service)
      REFERENCES portal_service (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_j5p7na06bgelejw91af8x1x35 FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tl09nadh1dd4q0qn014e7yo16 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT portal_order_price_check CHECK (price <= 100000::numeric AND price >= 0::numeric)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE portal_order
  OWNER TO postgres;



  
CREATE TABLE portal_product
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  code character varying(255),
  created timestamp without time zone,
  enabled boolean,
  czech_name character varying(150),
  deleted boolean NOT NULL,
  description text,
  english_name character varying(150),
  price numeric(19,2) NOT NULL,
  id_user_changed_by bigint,
  id_user_created_by bigint,
  CONSTRAINT portal_product_pkey PRIMARY KEY (id),
  CONSTRAINT fk_4w9x2vs83e5fwykw0vhow8frc FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_dbisqq23p4gj38mlscmsfuhj3 FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT portal_product_price_check CHECK (price <= 100000::numeric AND price >= 0::numeric)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE portal_product
  OWNER TO postgres;




  
 ALTER TABLE authority ALTER COLUMN "name" TYPE varchar(45);
insert into authority (id, changed, code, created, enabled, long_description, name, short_description)
values (5, NOW(), 'ROLE_PORTAL_USER', NOW(), true, 'Je mu umožněno zobrazovat jednotlivé sekce v informačním portálu', 'Registrovaný uživatel portálu', 'Je mu umožněno zobrazovat jednotlivé sekce v informačním portálu');


DROP TABLE essential_characteristic;
DROP TABLE declaration_of_performance;


end;