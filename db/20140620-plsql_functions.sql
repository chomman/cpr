begin;

-- FUNCTION FOR QS AUDITOR 
--
-- RETURNS TRUE, IF ARE General requirements COMPLIANT
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
end;