begin;
	ALTER TABLE standard_csn_has_change RENAME COLUMN csnonline_id TO csn_online_id;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_date TO standard_status;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_note TO note;
	ALTER TABLE standard_csn_has_change RENAME COLUMN change_code TO csn_name;
end;