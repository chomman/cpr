begin;
	ALTER TABLE standard_csn RENAME COLUMN standard_csn_status TO standard_status;
end;