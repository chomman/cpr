begin;

	update standard set standard_status = 'HARMONIZED' where standard_status='NORMAL' or standard_status is NULL;
	update standard_csn set standard_status = 'HARMONIZED' where standard_status='NORMAL' or standard_status is NULL;
	update standard_csn_has_change set standard_status = 'HARMONIZED' where standard_status='NORMAL' or standard_status is NULL;
	
	update standard set standard_status = 'CANCELED_HARMONIZED' where standard_status='CANCELED';
	update standard_csn set standard_status = 'CANCELED_HARMONIZED' where standard_status='CANCELED';
	update standard_csn_has_change set standard_status = 'CANCELED_HARMONIZED' where standard_status='CANCELED';
	
end;