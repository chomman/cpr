begin;

-- FUNCTION FOR QS AUDITOR 
--
-- RETURNS TRUE, IF ARE General requirements COMPLIANT
CREATE OR REPLACE FUNCTION qs_auditor_exp(aid bigint) RETURNS boolean AS $$
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
	WHERE ahe.auditor_id=aid and ahe.education_key = '1'
	LIMIT 1;

    RETURN education_row.id > 2 AND (experience + education_row.yeas_substitution) > 3;
END;
$$ LANGUAGE plpgsql;

end;