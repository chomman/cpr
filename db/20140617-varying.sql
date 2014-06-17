begin;
ALTER TABLE webpage_content ALTER COLUMN localized_key TYPE character varying(2);
ALTER TABLE country ALTER COLUMN code TYPE character varying(2);
ALTER TABLE quasar_auditor_has_education ALTER COLUMN education_key TYPE character varying(1);
end;