begin;
	ALTER TABLE webpage ADD COLUMN full_page_width boolean;
	update webpage set full_page_width = false;
	ALTER TABLE webpage ADD COLUMN hit bigint;
	update webpage set hit = 0;
begin;