begin;

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


-- RETURNS TRUE, if has given auditor formal and legal requiremets valid
CREATE OR REPLACE FUNCTION formal_legal_requirements(auditor quasar_auditor) RETURNS boolean AS $$
BEGIN
	RETURN auditor.is_ecr_card_signed AND auditor.is_confidentiality_signed AND auditor.is_cv_delivered;
END;
$$ LANGUAGE plpgsql;



-- RETURNS TRUE, IF ARE General requirements COMPLIANT
-- IF education_type eq '1' - ACTIVE medical devices experience
-- IF education_type eq '2' - NON-ACTIVE medical devices experience
CREATE OR REPLACE FUNCTION product_spelicasit_experience(auditor quasar_auditor, education_type text) RETURNS boolean AS $$
DECLARE
	experience int;
	education_row quasar_education_level%ROWTYPE;
BEGIN
	SELECT SUM(ahexp.years) INTO experience
	FROM quasar_auditor_has_experience ahexp
		JOIN quasar_experience exp ON exp.id=ahexp.experience_id
	WHERE exp.is_md_exp=true AND ahexp.auditor_id=auditor.id;

	SELECT el.* INTO education_row
	FROM quasar_auditor_has_education ahe
		JOIN quasar_education_level el ON el.id=ahe.education_level_id
	WHERE ahe.auditor_id=auditor.id and ahe.education_key = education_type
	LIMIT 1;

    RETURN education_row IS NOT NULL 
    	AND education_row.id > 2 
    	AND (experience + education_row.yeas_substitution) > 3
    	AND (auditor.research_development_experience + education_row.research_development_years_substitution >= 2);
END;
$$ LANGUAGE plpgsql;


-- RETURNS TRUE if has given worker training hours in last recent year valied
CREATE OR REPLACE FUNCTION training_hours_valid(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
BEGIN
	-- IF initial defined value is greater than trashold return true
	IF auditor.training_hours_in_recent_year >= settings.min_training_hours_in_recent_year THEN
		RETURN true;
	END IF; 
	-- OTHERWISE execute query which returns number of training hours in last recent years counted form training logs
	RETURN (get_training_hours_in_recent_year(auditor, settings) >= settings.min_training_hours_in_recent_year);
END;
$$ LANGUAGE plpgsql;


-- RETURNS number of training hours which given auditor passed in the last recent year
CREATE OR REPLACE FUNCTION get_training_hours_in_recent_year(auditor quasar_auditor, settings quasar_settings) RETURNS int AS $$
BEGIN
	RETURN  
	COALESCE( 
		(SELECT 
			sum(
				l.aimd  + l.iso_13485 + l.ivd + l.mdd + l.nb1023_procedures +
				(SELECT  COALESCE(sum(cst.hours),0) FROM quasar_training_log_has_cst cst WHERE cst.training_log_id = l.id) 
			) as hours
		FROM quasar_training_log l
		INNER JOIN quasar_training_log_has_auditors auditors ON auditors.training_log_id=l.id 
		WHERE auditors.auditor_id =auditor.id  AND l.status = 4 AND l.training_date >= get_date_treashold(settings, '365'))
	,0);
END;
$$ LANGUAGE plpgsql;



-- RETURNS number of audit days which given auditor performed in the last recent year
CREATE OR REPLACE FUNCTION get_audit_days_in_recent_year(auditor quasar_auditor, settings quasar_settings) RETURNS int AS $$
BEGIN
	RETURN (SELECT COALESCE(sum(item.days), 0) 
			FROM quasar_audit_log_has_item item
			INNER JOIN quasar_audit_log l on item.audit_log_id = l.id
			WHERE l.auditor_id = auditor.id AND l.status = 4 AND item.audit_date >= get_date_treashold(settings, '365'));
END;
$$ LANGUAGE plpgsql;



-- RETURNS date minus given count of days, dependent of quasar settings 
-- 
-- eg. if current date is 01.08.2014, and days_treashold is '365', result will be 2013-08-01, 
-- if the value of quasar_settings.use_365_days_interval is set to TRUE.
--
-- eg. if current date is 01.08.2014, and days_treashold is '365', result will be 2013-01-01, 
-- if the value of quasar_settings.use_365_days_interval is set to FALSE.
CREATE OR REPLACE FUNCTION get_date_treashold(settings quasar_settings, days_treashold varchar) RETURNS DATE AS $$
BEGIN
	IF settings.use_365_days_interval THEN 
		RETURN CURRENT_DATE - (days_treashold || ' days')::interval;
	ELSE
		RETURN concat( date_part('year', CURRENT_DATE - (days_treashold || ' days')::interval), '-01-01' )::DATE;
	END IF;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION product_assessor_a_recent_acitivities_valid(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
BEGIN
	RETURN recent_acitivities_valid(auditor, settings, settings.min_product_assessor_a_audit_days_in_recent_year);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION qs_auditor_recent_acitivities_valid(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
BEGIN
	RETURN recent_acitivities_valid(auditor, settings, settings.min_qs_auditor_audit_days_in_recent_year);
END;
$$ LANGUAGE plpgsql;


-- CHECK if are recent acitivities in the most recent year valid for functions Qs Auditor and ProductAssessorA valid
CREATE OR REPLACE FUNCTION recent_acitivities_valid(auditor quasar_auditor, settings quasar_settings, threashold int) RETURNS boolean AS $$
DECLARE
	auditdays_valid boolean DEFAULT false;
BEGIN
	IF NOT training_hours_valid(auditor, settings) THEN
		RETURN FALSE;
	END IF;
	IF auditor.audit_days_in_recent_year >= threashold THEN
		auditdays_valid := true;
	END IF;
	RETURN (SELECT get_audit_days_in_recent_year(auditor, settings) >= threashold INTO auditdays_valid); 
END;
$$ LANGUAGE plpgsql;


-- RETURNS TRUE, if has given auditor recent activities done
CREATE OR REPLACE FUNCTION product_assessor_r_recent_activities(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
DECLARE
	tf_reviews_in_last_year int;
	tf_reviews_in_last_3_years int;
	dd_reviews_in_last_year int;
	dd_reviews_in_last_3_years int;
BEGIN
	IF NOT training_hours_valid(auditor, settings) THEN
		return FALSE;
	END IF;

	tf_reviews_in_last_year := get_count_of_dossiers(auditor, settings, true, '365');
	IF tf_reviews_in_last_year >= settings.min_tf_reviews_in_recent_year THEN
		RETURN TRUE;
	END IF;
	-- 1095 days == 3 years
	tf_reviews_in_last_3_years := get_count_of_dossiers(auditor, settings, true, '1095');
	IF tf_reviews_in_last_3_years >= settings.min_tf_reviews_in_recent_tree_years THEN
		RETURN TRUE;
	END IF;

	dd_reviews_in_last_year := get_count_of_dossiers(auditor, settings, false, '365');
	IF 
		((tf_reviews_in_last_year + dd_reviews_in_last_year)  >= settings.min_tf_reviews_in_recent_year) AND 
		(tf_reviews_in_last_year >= dd_reviews_in_last_year)
	THEN
		RETURN TRUE;
	END IF;
	dd_reviews_in_last_3_years := get_count_of_dossiers(auditor, settings, false, '1095');
	IF 
		((tf_reviews_in_last_3_years + dd_reviews_in_last_3_years)  >= settings.min_tf_reviews_in_recent_tree_years) AND 
		(tf_reviews_in_last_3_years >= dd_reviews_in_last_3_years)
	THEN
		RETURN TRUE;
	END IF;
	RETURN FALSE;
END;
$$ LANGUAGE plpgsql;


-- RETURNS TRUE, if has given auditor recent activities done
CREATE OR REPLACE FUNCTION product_specliast_recent_activities(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
DECLARE
	dd_reviews_in_last_year int;
	dd_reviews_in_last_3_years int;
BEGIN
	IF NOT training_hours_valid(auditor, settings) THEN
		return FALSE;
	END IF;
	dd_reviews_in_last_year := get_count_of_dossiers(auditor, settings, false, '365');
	IF dd_reviews_in_last_year >= settings.min_dd_reviews_in_recent_year THEN
		RETURN TRUE;
	END IF;
	-- 1095 days == 3 years
	dd_reviews_in_last_3_years := get_count_of_dossiers(auditor, settings, false, '1095');
	IF dd_reviews_in_last_3_years >= settings.min_dd_reviews_in_recent_tree_years THEN
		RETURN TRUE;
	END IF;
	RETURN FALSE;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION get_count_of_dossiers(auditor quasar_auditor, settings quasar_settings, technical_file boolean, for_days varchar) RETURNS int AS $$
BEGIN
	IF technical_file THEN 
		RETURN 	(SELECT count(item.id)
				FROM quasar_dossier_report l
				INNER JOIN quasar_dossier_report_has_item item ON item.dossier_report_id=l.id
				WHERE (l.auditor_id =59 AND l.status = 4 ) AND item.audit_date >= get_date_treashold(settings, for_days) AND (
						(not ((item.category = 'III'  AND item.certification_sufix='CN/NB') OR 
						      (item.category = 'LIST_A' AND item.certification_sufix='CN/NB'))))
			);
	ELSE
		RETURN (SELECT count(item.id) 
				FROM quasar_dossier_report l
				INNER JOIN quasar_dossier_report_has_item item ON item.dossier_report_id=l.id
				WHERE l.auditor_id =59 AND l.status = 4 AND item.audit_date >= get_date_treashold(settings, for_days)
				      AND (item.category = 'III' OR item.category = 'LIST_A') AND item.certification_sufix='CN/NB');
	END IF;
END;
$$ LANGUAGE plpgsql;





end;