begin;

-- FUNCTION FOR QS AUDITOR 

-- RETURNS TRUE, IF ARE General requirements COMPLIANT
-- IF education_type eq '1' - ACTIVE medical devices experience
-- IF education_type eq '2' - NON-ACTIVE medical devices experience
CREATE OR REPLACE FUNCTION experience(aid bigint, education_type text) RETURNS boolean AS $$
DECLARE
	experience int;
	education_row quasar_education_level%ROWTYPE;
BEGIN
	SELECT SUM(ahexp.years) INTO experience
	FROM quasar_auditor_has_experience ahexp
		JOIN quasar_experience exp ON exp.id=ahexp.experience_id
	WHERE exp.is_md_exp=true AND ahexp.auditor_id=aid;

	
	SELECT el.* INTO education_row
	FROM quasar_auditor_has_education ahe
		JOIN quasar_education_level el ON el.id=ahe.education_level_id
	WHERE ahe.auditor_id=aid and ahe.education_key = education_type
	LIMIT 1;

    RETURN education_row IS NOT NULL AND education_row.id > 2 AND (experience + education_row.yeas_substitution) > 3;
END;
$$ LANGUAGE plpgsql;



-- RETURNS TRUE, if has given auditor Training including Auditing experience complained
CREATE OR REPLACE FUNCTION qs_auditor_training_auditing(auditor quasar_auditor) RETURNS boolean AS $$
BEGIN
			-- NB 1023 procedures
	RETURN auditor.nb1023_procedures_hours >= 16 AND
			-- MD Training
		   auditor.mdd_hours + 	auditor.ivd_hours >= 32 AND
		    -- ISO 9001 Trainig
		   (
		   	auditor.is_aproved_for_iso13485 OR
		   	(auditor.is_aproved_for_iso9001 AND auditor.iso13485_hours >= 8) OR
		   	(auditor.iso13485_hours + auditor.iso9001_hours >= 40)
		   );
END;
$$ LANGUAGE plpgsql;


-- RETURNS TRUE, if has given auditor formal and legal requiremets valid
CREATE OR REPLACE FUNCTION formal_legal_requirements(auditor quasar_auditor) RETURNS boolean AS $$
BEGIN
	RETURN auditor.is_ecr_card_signed AND auditor.is_confidentiality_signed AND auditor.is_cv_delivered;
END;
$$ LANGUAGE plpgsql;





-- RETURNS TRUE, if auditor with given ID has any valid EAC code.
CREATE OR REPLACE FUNCTION has_any_eac_code_granted(aid bigint) RETURNS boolean AS $$
DECLARE
	granted_count int;
BEGIN
	SELECT count(ahc.*) INTO granted_count
	FROM quasar_auditor_has_eac_code ahc
		INNER JOIN quasar_eac_code eac ON eac.id = ahc.eac_code_id
	WHERE eac.enabled = true AND ahc.auditor_id = aid AND
		  (
		  	ahc.is_itc_approved = true OR ahc.notified_body_id IS NOT NULL OR
		  	(ahc.number_of_iso13485_audits + ahc.number_of_nb_audits) >= eac.audit_threashold
		  );
	RETURN granted_count > 0;
END;
$$ LANGUAGE plpgsql;


CREATE VIEW quasar_qs_auditor AS SELECT 
	u.id,
	u.first_name,
	u.last_name,
	u.email,
	u.enabled,
	a.itc_id,
	a.degrees,
	a.is_in_training,  
	formal_legal_requirements(a) AS formal_legal_requirements,
	experience(a.id, '1') AS general_requirements,
	qs_auditor_training_auditing(a) AS training_auditing,
	has_any_eac_code_granted(a.id) AS has_any_eac_code_granted 
FROM quasar_auditor a
INNER JOIN users u ON u.id = a.id
ORDER BY a.itc_id;

end;