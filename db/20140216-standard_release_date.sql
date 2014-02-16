begin;
	ALTER TABLE standard ADD COLUMN released_date date;
	ALTER TABLE standard_csn ADD COLUMN released_date date;
end;