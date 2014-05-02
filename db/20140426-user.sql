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
	  
	CREATE SEQUENCE porta_order_item_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;	 
	
	CREATE SEQUENCE user_has_online_publication_seq
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
  currency character varying(3) NOT NULL,
  date_of_activation date,
  dic character varying(15),
  email character varying(50),
  email_sent boolean,
  first_name character varying(50) NOT NULL,
  ico character varying(8),
  ip_address character varying(45),
  last_name character varying(50) NOT NULL,
  order_status character varying(25),
  phone character varying(25),
  order_source character varying(15),
  street character varying(50),
  vat numeric(3,2),
  zip character varying(6),
  id_user_changed_by bigint,
  id_user_created_by bigint,
  user_id bigint NOT NULL,
  referer character varying(250),
  user_agent character varying(150),
  CONSTRAINT portal_order_pkey PRIMARY KEY (id),
  CONSTRAINT fk_8rb6uy7cjjq6rg8kb0a6f9q9c FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_j5p7na06bgelejw91af8x1x35 FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tl09nadh1dd4q0qn014e7yo16 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
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
  description_czech text,
  description_english text,
  english_name character varying(150),
  interval_value integer,
  online_publication character varying(20),
  interval_type character varying(10),
  portal_product_type character varying(12),
  price_czk numeric(6,0) NOT NULL,
  price_eur numeric(6,0) NOT NULL,
  id_user_changed_by bigint,
  id_user_created_by bigint,
  CONSTRAINT portal_product_pkey PRIMARY KEY (id),
  CONSTRAINT fk_4w9x2vs83e5fwykw0vhow8frc FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_dbisqq23p4gj38mlscmsfuhj3 FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT portal_product_interval_value_check CHECK (interval_value >= 1 AND interval_value <= 100),
  CONSTRAINT portal_product_price_czk_check CHECK (price_czk <= 100000::numeric AND price_czk >= 0::numeric),
  CONSTRAINT portal_product_price_eur_check CHECK (price_eur <= 100000::numeric AND price_eur >= 0::numeric)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE portal_product
  OWNER TO postgres;

  
CREATE TABLE portal_order_has_items
(
  id bigint NOT NULL,
  price numeric(6,2) NOT NULL,
  portal_order_id bigint NOT NULL,
  portal_product_id bigint NOT NULL,
  CONSTRAINT portal_order_has_items_pkey PRIMARY KEY (id),
  CONSTRAINT fk_6ph7ep7r2xgpq9nrah0fogtet FOREIGN KEY (portal_order_id)
      REFERENCES portal_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_pylea48c2myf1l9sln72m7k70 FOREIGN KEY (portal_product_id)
      REFERENCES portal_product (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT portal_order_has_items_price_check CHECK (price >= 0::numeric AND price <= 100000::numeric)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE portal_order_has_items
  OWNER TO postgres;

CREATE TABLE user_has_online_publication
(
  id bigint NOT NULL,
  online_publication character varying(20) NOT NULL,
  validity date NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT user_has_online_publication_pkey PRIMARY KEY (id),
  CONSTRAINT fk_9tu3mlf5j9oecnkpypw0eyyxg FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_has_online_publication
  OWNER TO postgres;

  
 ALTER TABLE authority ALTER COLUMN "name" TYPE varchar(45);
insert into authority (id, changed, code, created, enabled, long_description, name, short_description)
values (5, NOW(), 'ROLE_PORTAL_USER', NOW(), true, 'Je mu umožněno zobrazovat jednotlivé sekce v informačním portálu', 'Registrovaný uživatel portálu', 'Je mu umožněno zobrazovat jednotlivé sekce v informačním portálu');


DROP TABLE essential_characteristic;
DROP TABLE declaration_of_performance;
DROP TABLE tag;

end;