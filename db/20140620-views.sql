begin;

-- ********************************************
-- VIEW QS AUDITOR
--
CREATE VIEW quasar_qs_auditor AS SELECT 
	a.id,
	a.itc_id,
	a.is_in_training,  
	formal_legal_requirements(a) AS formal_legal_requirements,
	experience(a.id, '1') AS general_requirements,
	a.ra_approved_for_qs_auditor or qs_auditor_recent_acitivities_valid(a, s) as recent_acitivities,
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

    
    
    
-- ********************************************
-- VIEW PRODUCT ASSESSOR-A
--
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
	a.ra_approved_for_product_assessor_a or product_assessor_a_recent_acitivities_valid(a, s) as recent_acitivities
FROM quasar_auditor a
	INNER JOIN  quasar_auditor_has_specialities as ahs on ahs.auditor_id=a.id
	CROSS JOIN
    quasar_settings s
WHERE ahs.specialist_key=1;



-- ********************************************
-- VIEW PRODUCT ASSESSOR-R
--
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
	a.ra_approved_for_product_assessor_r or product_assessor_r_recent_activities(a, s) as recent_acitivities
FROM quasar_auditor a
	INNER JOIN  quasar_auditor_has_specialities as ahs on ahs.auditor_id=a.id
	CROSS JOIN
    quasar_settings s
WHERE ahs.specialist_key=2;



-- ********************************************
-- VIEW PRODUCT SPECIALIST
--
CREATE VIEW quasar_product_specialist AS SELECT 
	a.id,
	-- FORMAL AND LEGAL REQUIREMENTS
	formal_legal_requirements(a) AS formal_legal_requirements,
	-- Education & Work experience (Active MD)
	(product_spelicasit_experience(a, '1') and ahs.is_for_active_mdd) AS gen_req_active_md,
	-- Education & Work experience (NON Active MD)
	(product_spelicasit_experience(a, '2') and ahs.is_for_non_active_mdd) AS gen_req_non_active_md,
	-- Education & Work experience (IVD)
	((product_spelicasit_experience(a, '1') OR product_spelicasit_experience(a, '2')) AND ahs.is_for_invitro_diagnostic) AS gen_req_ivd,
	 -- Training (Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_specialist_md_training AND
		a.tf_training_in_hours >= s.product_specialist_dd_training_review AND
		a.total_tf_reviews >= s.product_specialist_dd_total
	) AS training_active_md,
	-- Training (NON Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_specialist_md_training AND
		a.tf_training_in_hours >= s.product_specialist_dd_training_review AND
		a.total_tf_reviews >= s.product_specialist_dd_total
	) AS training_non_active_md,
	-- Training (NON Active MD)
	(
		a.iso13485_hours + a.mdd_hours >= s.product_specialist_md_training AND
		a.ivd_hours >= s.product_specialist_ivd_training AND
		a.tf_training_in_hours >= s.product_specialist_dd_training_review AND
		a.total_tf_reviews >= s.product_specialist_dd_total
	) as training_ivd,
	-- RECENT ACTIVITIES
	a.ra_approved_for_product_specialist or product_specliast_recent_activities(a, s) as recent_acitivities
FROM quasar_auditor a
	INNER JOIN  quasar_auditor_has_specialities as ahs on ahs.auditor_id=a.id
	CROSS JOIN
    quasar_settings s
WHERE ahs.specialist_key=3;

end;