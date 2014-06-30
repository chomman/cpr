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
	WHERE eac.enabled = true AND ahc.auditor_id = aid AND ahc.refused = false AND
		  (
		  	ahc.is_itc_approved = true OR ahc.notified_body_id IS NOT NULL OR
		  	(ahc.number_of_iso13485_audits + ahc.number_of_nb_audits) >= eac.audit_threashold
		  );
	RETURN granted_count > 0;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION recent_acitivities(aid bigint) RETURNS boolean AS $$
BEGIN
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


CREATE VIEW quasar_qs_auditor AS SELECT 
	a.id,
	a.itc_id,
	a.is_in_training,  
	formal_legal_requirements(a) AS formal_legal_requirements,
	experience(a.id, '1') AS general_requirements,
	recent_acitivities(a.id) as recent_acitivities,
	has_any_eac_code_granted(a.id) AS has_any_eac_code_granted,
	(
	    a.nb1023_procedures_hours >= s.qs_auditor_nb1023_procedures and
	    -- md training
	    a.mdd_hours + a.ivd_hours >= s.qs_auditor_md_training and
	     -- iso 9001 trainig
	    (
		a.is_aproved_for_iso13485 or
		(
		    a.is_aproved_for_iso9001 and
		    a.iso13485_hours >= s.qs_auditor_iso13485_training
		) or
		(a.iso13485_hours + a.iso9001_hours >= s.qs_auditor_class_room_training)
	    )
   ) and a.total_of_audits >= s.qs_auditor_no_audits as training_auditing
FROM quasar_auditor a
	CROSS JOIN
    quasar_settings s;


CREATE VIEW quasar_product_assessor_a AS SELECT 
	a.id,
	-- FORMAL AND LEGAL REQUIREMENTS
	formal_legal_requirements(a) AS formal_legal_requirements,
	-- Education & Work experience (Active MD)
	(experience(a.id, '1') and ahs.is_for_active_mdd) AS gen_req_active_md,
	-- Education & Work experience (NON Active MD)
	(experience(a.id, '2') and ahs.is_for_non_active_mdd) AS gen_req_non_active_md,
	-- Education & Work experience (IVD)
	((experience(a.id, '1') OR experience(a.id, '2')) AND ahs.is_for_invitro_diagnostic) AS gen_req_ivd,
	 -- Training (Active MD)
	(
		a.iso9001_hours + a.mdd_hours >= s.product_assessor_a_md_training AND 
		a.nb1023_procedures_hours >= s.product_assessor_a_nb1023_procedures
	) AS training_active_md,
	-- Training (NON Active MD)
	(
		a.iso9001_hours + a.mdd_hours >= s.product_assessor_a_md_training AND 
		a.nb1023_procedures_hours >= s.product_assessor_a_nb1023_procedures 
	) AS training_non_active_md,
	-- Training (NON Active MD)
	(
		a.iso9001_hours + a.mdd_hours >= s.product_assessor_a_md_training AND 
		a.nb1023_procedures_hours >= s.product_assessor_a_nb1023_procedures AND 
		a.ivd_hours >= s.product_assessor_a_ivd_training
	) as training_ivd,
	-- Auditing requirements (Any MD)
	a.total_of_audits >= s.product_assessor_a_no_audits as min_audits,
	-- RECENT ACTIVITIES
	recent_acitivities(a.id) as recent_acitivities
FROM quasar_auditor a
	INNER JOIN  quasar_auditor_has_specialities as ahs on ahs.auditor_id=a.id
	CROSS JOIN
    quasar_settings s
WHERE ahs.specialist_key=1;


-- RETURNS TRUE, if has given auditor recent activities done
CREATE OR REPLACE FUNCTION product_assessor_r_recent_activities(auditor quasar_auditor, settings quasar_settings) RETURNS boolean AS $$
BEGIN
	-- TODO implementation
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


CREATE VIEW quasar_product_assessor_r AS SELECT 
	a.id,
	-- FORMAL AND LEGAL REQUIREMENTS
	formal_legal_requirements(a) AS formal_legal_requirements,
	-- Education & Work experience (Active MD)
	(experience(a.id, '1') and ahs.is_for_active_mdd) AS gen_req_active_md,
	-- Education & Work experience (NON Active MD)
	(experience(a.id, '2') and ahs.is_for_non_active_mdd) AS gen_req_non_active_md,
	-- Education & Work experience (IVD)
	((experience(a.id, '1') OR experience(a.id, '2')) AND ahs.is_for_invitro_diagnostic) AS gen_req_ivd,
	 -- Training (Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_assessor_r_md_training AND
		a.tf_training_in_hours >= s.product_assessor_r_tf_training_review AND
		a.total_tf_reviews >= s.product_assessor_r_tf_total
	) AS training_active_md,
	-- Training (NON Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_assessor_r_md_training AND
		a.tf_training_in_hours >= s.product_assessor_r_tf_training_review AND
		a.total_tf_reviews >= s.product_assessor_r_tf_total
	) AS training_non_active_md,
	-- Training (NON Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_assessor_r_md_training AND
		a.ivd_hours >= s.product_assessor_r_ivd_training AND
		a.tf_training_in_hours >= s.product_assessor_r_tf_training_review AND
		a.total_tf_reviews >= s.product_assessor_r_tf_total
	) as training_ivd,
	-- RECENT ACTIVITIES
	product_assessor_r_recent_activities(a, s) as recent_acitivities
FROM quasar_auditor a
	INNER JOIN  quasar_auditor_has_specialities as ahs on ahs.auditor_id=a.id
	CROSS JOIN
    quasar_settings s
WHERE ahs.specialist_key=2;

end;