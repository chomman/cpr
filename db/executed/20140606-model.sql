begin;
	
	
	CREATE SEQUENCE quasar_nando_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 100000
	  START 100
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_eac_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 100000
	  START 40
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_partner_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	CREATE SEQUENCE quasar_field_of_education_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 29
	  CACHE 1;
	  
	 CREATE SEQUENCE quasar_special_training_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_auditor_has_eac_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_auditor_has_nando_code_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 221
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_auditor_has_experience_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_log_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	   
	  CREATE SEQUENCE quasar_log_item_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_comment_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_company_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 23
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_education_level_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	  
	  CREATE SEQUENCE quasar_experience_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;

	
	


CREATE TABLE quasar_settings
(
  id bigint NOT NULL,
  product_assessor_a_ivd_training integer,
  product_assessor_a_md_training integer,
  product_assessor_a_nb1023_procedures integer,
  product_assessor_r_ivd_training integer,
  product_assessor_r_md_training integer,
  product_assessor_r_tf_total integer,
  product_assessor_r_tf_training_review integer,
  product_specialist_ivd_training integer,
  product_specialist_md_training integer,
  product_specialist_dd_total integer,
  product_specialist_dd_training_review integer,
  qs_auditor_class_room_training integer,
  qs_auditor_iso13485_training integer,
  qs_auditor_md_training integer,
  qs_auditor_nb1023_procedures integer,
  product_assessor_a_no_audits integer,
  qs_auditor_no_audits integer,
  notification_email character varying(50),
  min_training_hours_in_recent_year integer,
  use_365_days_interval boolean,
  min_product_assessor_a_audit_days_in_recent_year integer,
  min_qs_auditor_audit_days_in_recent_year integer,
  min_dd_reviews_in_recent_tree_years integer,
  min_dd_reviews_in_recent_year integer,
  min_tf_reviews_in_recent_tree_years integer,
  min_tf_reviews_in_recent_year integer,
  CONSTRAINT quasar_settings_pkey PRIMARY KEY (id),
  CONSTRAINT quasar_settings_min_dd_reviews_in_recent_tree_years_check CHECK (min_dd_reviews_in_recent_tree_years >= 0),
  CONSTRAINT quasar_settings_min_dd_reviews_in_recent_year_check CHECK (min_dd_reviews_in_recent_year >= 0),
  CONSTRAINT quasar_settings_min_product_assessor_a_audit_days_in_rece_check CHECK (min_product_assessor_a_audit_days_in_recent_year >= 0),
  CONSTRAINT quasar_settings_min_qs_auditor_audit_days_in_recent_year_check CHECK (min_qs_auditor_audit_days_in_recent_year >= 0),
  CONSTRAINT quasar_settings_min_tf_reviews_in_recent_tree_years_check CHECK (min_tf_reviews_in_recent_tree_years >= 0),
  CONSTRAINT quasar_settings_min_tf_reviews_in_recent_year_check CHECK (min_tf_reviews_in_recent_year >= 0),
  CONSTRAINT quasar_settings_min_training_hours_in_recent_year_check CHECK (min_training_hours_in_recent_year >= 0),
  CONSTRAINT quasar_settings_product_assessor_a_ivd_training_check CHECK (product_assessor_a_ivd_training >= 0),
  CONSTRAINT quasar_settings_product_assessor_a_md_training_check CHECK (product_assessor_a_md_training >= 0),
  CONSTRAINT quasar_settings_product_assessor_a_nb1023_procedures_check CHECK (product_assessor_a_nb1023_procedures >= 0),
  CONSTRAINT quasar_settings_product_assessor_a_no_audits_check CHECK (product_assessor_a_no_audits >= 0),
  CONSTRAINT quasar_settings_product_assessor_r_ivd_training_check CHECK (product_assessor_r_ivd_training >= 0),
  CONSTRAINT quasar_settings_product_assessor_r_md_training_check CHECK (product_assessor_r_md_training >= 0),
  CONSTRAINT quasar_settings_product_assessor_r_tf_total_check CHECK (product_assessor_r_tf_total >= 0),
  CONSTRAINT quasar_settings_product_assessor_r_tf_training_review_check CHECK (product_assessor_r_tf_training_review >= 0),
  CONSTRAINT quasar_settings_product_specialist_ivd_training_check CHECK (product_specialist_ivd_training >= 0),
  CONSTRAINT quasar_settings_product_specialist_md_training_check CHECK (product_specialist_md_training >= 0),
  CONSTRAINT quasar_settings_product_specialist_tf_total_check CHECK (product_specialist_dd_total >= 0),
  CONSTRAINT quasar_settings_product_specialist_tf_training_review_check CHECK (product_specialist_dd_training_review >= 0),
  CONSTRAINT quasar_settings_qs_auditor_class_room_training_check CHECK (qs_auditor_class_room_training >= 0),
  CONSTRAINT quasar_settings_qs_auditor_iso13485_training_check CHECK (qs_auditor_iso13485_training >= 0),
  CONSTRAINT quasar_settings_qs_auditor_md_training_check CHECK (qs_auditor_md_training >= 0),
  CONSTRAINT quasar_settings_qs_auditor_nb1023_procedures_check CHECK (qs_auditor_nb1023_procedures >= 0),
  CONSTRAINT quasar_settings_qs_auditor_no_audits_check CHECK (qs_auditor_no_audits >= 0)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_nando_code
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  enabled boolean NOT NULL,
  threashold_assesor_a_iso_adusits integer,
  threashold_assesor_a_nb_adusits integer,
  threashold_assesor_a_training integer,
  threashold_assesor_r_tf_reviews integer,
  threashold_assesor_r_tf_reviews_for_training integer,
  threashold_assesor_r_training integer,
  code character varying(9),
  is_for_product_assesor_a boolean,
  is_for_product_assesor_r boolean,
  is_for_product_specialist boolean,
  nando_order integer,
  threashold_specialist_dd_reviews integer,
  threashold_specialist_dd_reviews_for_training integer,
  threashold_specialist_training integer,
  specification character varying(200),
  id_user_changed_by bigint,
  parent_id bigint,
  audits_in_training_treshold integer,
  CONSTRAINT quasar_nando_code_pkey PRIMARY KEY (id),
  CONSTRAINT fk_32ux8dcuedu7qg2lg215y3602 FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_excqiihu975cy6ku24jq52eyw FOREIGN KEY (parent_id)
      REFERENCES quasar_nando_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_nando_code_audits_in_training_treshold_check CHECK (audits_in_training_treshold >= 0)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_partner
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  enabled boolean NOT NULL,
  name character varying(50),
  id_user_changed_by bigint,
  id_manager bigint,
  CONSTRAINT quasar_partner_pkey PRIMARY KEY (id),
  CONSTRAINT fk_2d3fu543emnw2m1wyj9n1jxtm FOREIGN KEY (id_manager)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_m81tudtqnyvbgkryexfu8rvi5 FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_eac_code
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  enabled boolean NOT NULL,
  code character varying(6),
  is_for_qs_auditor boolean,
  name character varying(120),
  id_user_changed_by bigint,
  nace_code character varying(100),
  audit_threashold integer NOT NULL,
  audits_in_training_treshold integer,
  CONSTRAINT quasar_eac_code_pkey PRIMARY KEY (id),
  CONSTRAINT fk_ftno5ll55bphdd88qct8ynxif FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_eac_code_audits_in_training_treshold_check CHECK (audits_in_training_treshold >= 0)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_education_level
(
  id bigserial NOT NULL,
  name character varying(30),
  yeas_substitution integer NOT NULL,
  research_development_years_substitution double precision,
  CONSTRAINT quasar_education_level_pkey PRIMARY KEY (id),
  CONSTRAINT quasar_education_level_yeas_substitution_check CHECK (yeas_substitution >= 0)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_experience
(
  id bigserial NOT NULL,
  is_md_exp boolean NOT NULL,
  name character varying(30) NOT NULL,
  CONSTRAINT quasar_experience_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_field_of_education
(
  id bigint NOT NULL,
  is_for_active_md boolean,
  is_for_non_active_md boolean,
  name character varying(50),
  id_user_changed_by bigint,
  is_specific_or_course boolean,
  CONSTRAINT quasar_field_of_education_pkey PRIMARY KEY (id),
  CONSTRAINT fk_hk8kshe62qmcccv3wtkin1fn6 FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_company
(
  id bigint NOT NULL,
  name character varying(60) NOT NULL,
  CONSTRAINT quasar_company_pkey PRIMARY KEY (id),
  CONSTRAINT uk_qf9puqfq1ykykdlyk353v8q8a UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_certification_body
(
  id bigint NOT NULL,
  name character varying(60) NOT NULL,
  CONSTRAINT quasar_certification_body_pkey PRIMARY KEY (id),
  CONSTRAINT uk_8c49a82lqypu686crn7qaobpi UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_auditor
(
  is_aproved_for_iso13485 boolean,
  is_aproved_for_iso9001 boolean,
  is_confidentiality_signed boolean NOT NULL,
  is_cv_delivered boolean NOT NULL,
  dd_training_in_hours integer,
  degrees character varying(20),
  is_ecr_card_signed boolean NOT NULL,
  is_in_training boolean,
  iso13485_hours integer,
  itc_id integer NOT NULL,
  ivd_hours integer,
  mdd_hours integer,
  nb1023_procedures_hours integer,
  reassessment_date date,
  tf_training_in_hours integer,
  total_of_auditdayes integer,
  total_of_audits integer,
  total_dd_reviews integer,
  total_tf_reviews integer,
  iso9001_hours integer,
  id bigint NOT NULL,
  country_id bigint,
  partner_id bigint,
  research_development_experience double precision,
  no_dd_reviews_for_sterile_md integer,
  no_tf_reviews_for_sterile_md integer,
  ra_approved_for_product_assessor_a boolean,
  ra_approved_for_product_assessor_r boolean,
  ra_approved_for_product_specialist boolean,
  ra_approved_for_qs_auditor boolean,
  other_emails character varying(100),
  rating double precision,
  audit_days_in_recent_year integer,
  training_hours_in_recent_year integer,
  aimd_hours integer,
  CONSTRAINT quasar_auditor_pkey PRIMARY KEY (id),
  CONSTRAINT fk_276totugws84un84xweyfv3py FOREIGN KEY (id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_f6rnjcnttps6q7spe0w194nrm FOREIGN KEY (country_id)
      REFERENCES country (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_rdnbdkmxohd5y2ces9cj13mw9 FOREIGN KEY (partner_id)
      REFERENCES quasar_partner (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_o7v8bm2l7vcexx23rn4ujv62p UNIQUE (itc_id),
  CONSTRAINT quasar_auditor_aimd_hours_check CHECK (aimd_hours >= 0),
  CONSTRAINT quasar_auditor_audit_days_in_recent_year_check CHECK (audit_days_in_recent_year >= 0),
  CONSTRAINT quasar_auditor_dd_training_in_hours_check CHECK (dd_training_in_hours >= 0),
  CONSTRAINT quasar_auditor_iso13485_hours_check CHECK (iso13485_hours >= 0),
  CONSTRAINT quasar_auditor_iso9001_hours_check CHECK (iso9001_hours >= 0),
  CONSTRAINT quasar_auditor_ivd_hours_check CHECK (ivd_hours >= 0),
  CONSTRAINT quasar_auditor_mdd_hours_check CHECK (mdd_hours >= 0),
  CONSTRAINT quasar_auditor_nb1023_procedures_hours_check CHECK (nb1023_procedures_hours >= 0),
  CONSTRAINT quasar_auditor_no_dd_reviews_for_sterile_md_check CHECK (no_dd_reviews_for_sterile_md >= 0),
  CONSTRAINT quasar_auditor_no_tf_reviews_for_sterile_md_check CHECK (no_tf_reviews_for_sterile_md >= 0),
  CONSTRAINT quasar_auditor_tf_training_in_hours_check CHECK (tf_training_in_hours >= 0),
  CONSTRAINT quasar_auditor_total_dd_reviews_check CHECK (total_dd_reviews >= 0),
  CONSTRAINT quasar_auditor_total_of_auditdayes_check CHECK (total_of_auditdayes >= 0),
  CONSTRAINT quasar_auditor_total_of_audits_check CHECK (total_of_audits >= 0),
  CONSTRAINT quasar_auditor_total_tf_reviews_check CHECK (total_tf_reviews >= 0),
  CONSTRAINT quasar_auditor_training_hours_in_recent_year_check CHECK (training_hours_in_recent_year >= 0)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_auditor_has_education
(
  auditor_id bigint NOT NULL,
  education1_id bigint,
  education2_id bigint,
  education_level_id bigint,
  postgreduate_study_id bigint,
  education_key character varying(255) NOT NULL,
  CONSTRAINT quasar_auditor_has_education_pkey PRIMARY KEY (auditor_id, education_key),
  CONSTRAINT fk_kyyy3yyb28pr37ni8bukaekdg FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ne5ash98te2hm2n0qhgjhkjam FOREIGN KEY (postgreduate_study_id)
      REFERENCES quasar_field_of_education (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ppi0meat0olgv73vkpveqdqad FOREIGN KEY (education1_id)
      REFERENCES quasar_field_of_education (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_r4836xo52yyrehbr9wgmunu7n FOREIGN KEY (education2_id)
      REFERENCES quasar_field_of_education (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tq4y4qhvkgaofsurst2dkogoi FOREIGN KEY (education_level_id)
      REFERENCES quasar_education_level (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_auditor_has_nando_code
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  number_of_dd_reviews integer NOT NULL,
  number_of_iso13485_audits integer,
  number_of_nb_audits integer,
  number_of_tf_reviews integer NOT NULL,
  product_assessor_a_approved boolean,
  product_assessor_a_reason character varying(255),
  is_product_assessor_a_refused boolean,
  product_assessor_a_training integer,
  product_assessor_r_approved boolean,
  product_assessor_r_reason character varying(255),
  is_product_assessor_r_refused boolean,
  category_specific_training integer,
  product_specialist_approved boolean,
  product_specialist_reason character varying(255),
  is_product_specialist_refused boolean,
  auditor_id bigint,
  id_user_changed_by bigint,
  nando_code_id bigint NOT NULL,
  product_assessor_a_notified_body_id bigint,
  product_assessor_r_notified_body_id bigint,
  product_specialist_notified_body_id bigint,
  CONSTRAINT quasar_auditor_has_nando_code_pkey PRIMARY KEY (id),
  CONSTRAINT fk_1cao9nglljra6nwmn6s6s8m5x FOREIGN KEY (product_assessor_r_notified_body_id)
      REFERENCES notified_body (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_6un0e2vwb5kfs8ppd6wpjlqy4 FOREIGN KEY (product_specialist_notified_body_id)
      REFERENCES notified_body (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_7o1lbwkbs10t884tmv3vm4mab FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_jsexwhvigha0gis02ia88ci3h FOREIGN KEY (product_assessor_a_notified_body_id)
      REFERENCES notified_body (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_me45ym73y7l7syg3pw3l56h86 FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_rxnme3usj754gysa5gl6k56go FOREIGN KEY (nando_code_id)
      REFERENCES quasar_nando_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_ae6cjof5thv4gskr0mwv01far UNIQUE (nando_code_id, auditor_id),
  CONSTRAINT quasar_auditor_has_nando_code_number_of_dd_reviews_check CHECK (number_of_dd_reviews >= 0),
  CONSTRAINT quasar_auditor_has_nando_code_number_of_iso13485_audits_check CHECK (number_of_iso13485_audits >= 0),
  CONSTRAINT quasar_auditor_has_nando_code_number_of_nb_audits_check CHECK (number_of_nb_audits >= 0),
  CONSTRAINT quasar_auditor_has_nando_code_number_of_tf_reviews_check CHECK (number_of_tf_reviews >= 0),
  CONSTRAINT quasar_auditor_has_nando_code_product_assessor_a_training_check CHECK (product_assessor_a_training >= 0),
  CONSTRAINT quasar_auditor_has_nando_code_product_assessor_r_training_check CHECK (category_specific_training >= 0)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_auditor_has_eac_code
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  is_itc_approved boolean,
  number_of_iso13485_audits integer,
  number_of_nb_audits integer,
  refused boolean,
  auditor_id bigint,
  id_user_changed_by bigint,
  eac_code_id bigint NOT NULL,
  notified_body_id bigint,
  reason_of_refusal character varying(255),
  CONSTRAINT quasar_auditor_has_eac_code_pkey PRIMARY KEY (id),
  CONSTRAINT fk_3p6ou1dtt5xpo82n0n6i0bcyo FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_4xrupwb5q0xr5t92v2p95gj0v FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_8ughstib2gkfk9u1jxxe8ym45 FOREIGN KEY (eac_code_id)
      REFERENCES quasar_eac_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_qyd3wdkf63wrrnfwt48vvylv5 FOREIGN KEY (notified_body_id)
      REFERENCES notified_body (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_qgdglnc3krl0mg8e4yujke8hb UNIQUE (eac_code_id, auditor_id),
  CONSTRAINT quasar_auditor_has_eac_code_number_of_iso13485_audits_check CHECK (number_of_iso13485_audits >= 0),
  CONSTRAINT quasar_auditor_has_eac_code_number_of_nb_audits_check CHECK (number_of_nb_audits >= 0)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_auditor_has_experience
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  years integer NOT NULL,
  auditor_id bigint NOT NULL,
  id_user_changed_by bigint,
  experience_id bigint NOT NULL,
  CONSTRAINT quasar_auditor_has_experience_pkey PRIMARY KEY (id),
  CONSTRAINT fk_1q8i61kt30yc3u88rr3cod976 FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_8yn1wptwauik6wqgthe68h8ad FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_l24v6bkxogjfw7qgx576991np FOREIGN KEY (experience_id)
      REFERENCES quasar_experience (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_auditor_has_experience_years_check CHECK (years >= 0 AND years <= 99)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE quasar_auditor_has_special_training
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  enabled boolean NOT NULL,
  hours integer NOT NULL,
  name character varying(150) NOT NULL,
  id_user_changed_by bigint,
  auditor_id bigint,
  CONSTRAINT quasar_auditor_has_special_training_pkey PRIMARY KEY (id),
  CONSTRAINT fk_b8pnvbiu8pgiybaqej20lka7y FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_mp8m2isekka86irr312pvbtrr FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_auditor_has_specialities
(
  auditor_id bigint NOT NULL,
  is_for_active_mdd boolean,
  is_for_invitro_diagnostic boolean,
  is_for_non_active_mdd boolean,
  specialist_key integer NOT NULL,
  CONSTRAINT quasar_auditor_has_specialities_pkey PRIMARY KEY (auditor_id, specialist_key),
  CONSTRAINT fk_2yt67g767d3ubax2nmvhu618a FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_audit_log
  (
    id bigint NOT NULL,
    changed timestamp without time zone,
    created timestamp without time zone,
    revision integer NOT NULL,
    status smallint,
    auditor_id bigint,
    id_user_changed_by bigint,
    id_user_created_by bigint,
    rating double precision,
    CONSTRAINT quasar_audit_log_pkey PRIMARY KEY (id),
    CONSTRAINT fk_a66e78dcglj6qis8d02yswh6s FOREIGN KEY (id_user_changed_by)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_j1h71i24yvo2p5l6hq7a5i7ch FOREIGN KEY (auditor_id)
        REFERENCES quasar_auditor (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_rjl2duv9nei76vkpv9ggsy39y FOREIGN KEY (id_user_created_by)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
  )
  WITH (
    OIDS=FALSE
  );   
  
  CREATE TABLE quasar_audit_log_has_item
(
  id bigint NOT NULL,
  audit_date date NOT NULL,
  cerfied_product character varying(255),
  days integer NOT NULL,
  order_no character varying(9),
  item_type character varying(8) NOT NULL,
  audit_log_id bigint NOT NULL,
  company_id bigint,
  certification_body_id bigint,
  CONSTRAINT quasar_audit_log_has_item_pkey PRIMARY KEY (id),
  CONSTRAINT fk_3ogofy8mjup48djl51cplsiii FOREIGN KEY (certification_body_id)
      REFERENCES quasar_certification_body (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_8gqxwqk7o8a4ewy2667w6qvbv FOREIGN KEY (company_id)
      REFERENCES quasar_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_rs6a4l0dnny2ssiok3raam690 FOREIGN KEY (audit_log_id)
      REFERENCES quasar_audit_log (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_audit_log_has_item_days_check CHECK (days >= 1)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_audit_log_item_has_eac_code
(
  audit_log_item_id bigint NOT NULL,
  eac_code_id bigint NOT NULL,
  CONSTRAINT quasar_audit_log_item_has_eac_code_pkey PRIMARY KEY (audit_log_item_id, eac_code_id),
  CONSTRAINT fk_7g016q0wpl8rfii75qbuw9xww FOREIGN KEY (eac_code_id)
      REFERENCES quasar_eac_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ry72r4xp0rg9mtjunjdk97p3y FOREIGN KEY (audit_log_item_id)
      REFERENCES quasar_audit_log_has_item (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_audit_log_item_has_nando_code
(
  audit_log_item_id bigint NOT NULL,
  nando_code_id bigint NOT NULL,
  CONSTRAINT quasar_audit_log_item_has_nando_code_pkey PRIMARY KEY (audit_log_item_id, nando_code_id),
  CONSTRAINT fk_3qlioh6838l63xvbasjx63fmf FOREIGN KEY (audit_log_item_id)
      REFERENCES quasar_audit_log_has_item (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_oar81id8x6tky76eg7owt8aks FOREIGN KEY (nando_code_id)
      REFERENCES quasar_nando_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_dossier_report
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  created timestamp without time zone,
  revision integer NOT NULL,
  status smallint,
  auditor_id bigint,
  id_user_changed_by bigint,
  id_user_created_by bigint,
  CONSTRAINT quasar_dossier_report_pkey PRIMARY KEY (id),
  CONSTRAINT fk_5g6ivqyvc326tneavwjt18hbt FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_dooa6ksk4f04oyvvj58eesyxh FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ivguddrhgk8tcr7v8f054uura FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_dossier_report_has_item
(
  id bigint NOT NULL,
  audit_date date NOT NULL,
  cerfied_product character varying(255),
  order_no character varying(9),
  category character varying(6) NOT NULL,
  certification_no character varying(6) NOT NULL,
  certification_sufix character varying(5) NOT NULL,
  company_id bigint,
  dossier_report_id bigint NOT NULL,
  CONSTRAINT quasar_dossier_report_has_item_pkey PRIMARY KEY (id),
  CONSTRAINT fk_g4o1d9c4w379lvuflyqomrpfw FOREIGN KEY (dossier_report_id)
      REFERENCES quasar_dossier_report (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_kg4vtfsl3xc3qv3k5ki814br9 FOREIGN KEY (company_id)
      REFERENCES quasar_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE quasar_dossier_report_item_has_nando_code
(
  dossier_report_item_id bigint NOT NULL,
  nando_code_id bigint NOT NULL,
  CONSTRAINT quasar_dossier_report_item_has_nando_code_pkey PRIMARY KEY (dossier_report_item_id, nando_code_id),
  CONSTRAINT fk_3rb19t1y42vw0lhoijsn1mym3 FOREIGN KEY (dossier_report_item_id)
      REFERENCES quasar_dossier_report_has_item (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_h8rbkq1xw2m4hq1ms85of92nx FOREIGN KEY (nando_code_id)
      REFERENCES quasar_nando_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);




CREATE TABLE quasar_training_log
(
  id bigint NOT NULL,
  changed timestamp without time zone,
  created timestamp without time zone,
  revision integer NOT NULL,
  status smallint,
  id_user_changed_by bigint,
  id_user_created_by bigint,
  aimd smallint,
  iso_13485 smallint,
  iso_9001 smallint,
  ivd smallint,
  mdd smallint,
  nb1023_procedures smallint,
  attachment character varying(32),
  training_date date,
  subject character varying(255),
  description text,
  CONSTRAINT quasar_training_log_pkey PRIMARY KEY (id),
  CONSTRAINT fk_e21131427lq9iye4p2qrixsdx FOREIGN KEY (id_user_changed_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_owcquad0ytolg3lkov3ut64hh FOREIGN KEY (id_user_created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_training_log_aimd_check CHECK (aimd >= 0),
  CONSTRAINT quasar_training_log_iso_13485_check CHECK (iso_13485 >= 0),
  CONSTRAINT quasar_training_log_iso_9001_check CHECK (iso_9001 >= 0),
  CONSTRAINT quasar_training_log_ivd_check CHECK (ivd >= 0),
  CONSTRAINT quasar_training_log_mdd_check CHECK (mdd >= 0),
  CONSTRAINT quasar_training_log_nb1023_procedures_check CHECK (nb1023_procedures >= 0)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_training_log_has_auditors
(
  training_log_id bigint NOT NULL,
  auditor_id bigint NOT NULL,
  CONSTRAINT quasar_training_log_has_auditors_pkey PRIMARY KEY (training_log_id, auditor_id),
  CONSTRAINT fk_5cgejrkletksiwcu0gbag9dtb FOREIGN KEY (auditor_id)
      REFERENCES quasar_auditor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_gfqtk7p9sbc4hiqhopxi9ot62 FOREIGN KEY (training_log_id)
      REFERENCES quasar_training_log (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_training_log_has_cst
(
  id bigint NOT NULL,
  hours smallint NOT NULL,
  nando_code_id bigint NOT NULL,
  training_log_id bigint NOT NULL,
  CONSTRAINT quasar_training_log_has_cst_pkey PRIMARY KEY (id),
  CONSTRAINT fk_2eorahfd0paer3k1srx1rxtli FOREIGN KEY (nando_code_id)
      REFERENCES quasar_nando_code (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_775uh5kdunf3yu8y8hrqfhwe FOREIGN KEY (training_log_id)
      REFERENCES quasar_training_log (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT quasar_training_log_has_cst_hours_check CHECK (hours >= 1)
)
WITH (
  OIDS=FALSE
);
CREATE TABLE quasar_comment
(
  id bigint NOT NULL,
  comment text,
  created timestamp without time zone,
  audit_log_id bigint NOT NULL,
  id_user bigint,
  CONSTRAINT quasar_comment_pkey PRIMARY KEY (id),
  CONSTRAINT fk_b0ocbd2bewfwj05gtmwlko8uc FOREIGN KEY (id_user)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE country ADD COLUMN is_in_eu boolean;
update country set is_in_eu=true;

CREATE INDEX auditor_eac_index
  ON quasar_auditor_has_eac_code
  USING hash
  (auditor_id);


CREATE INDEX auditor_nanocode_index
  ON quasar_auditor_has_nando_code
  USING hash (auditor_id);


CREATE INDEX category_index
  ON quasar_dossier_report_has_item
  USING hash (category);

CREATE INDEX cert_prefix_index
  ON quasar_dossier_report_has_item
  USING hash (certification_sufix);
  
 end;
