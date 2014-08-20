begin;
	ALTER TABLE portal_order ADD COLUMN is_deleted boolean;
	ALTER TABLE portal_order ADD COLUMN paymen_tdate character varying(10);
	update portal_order set is_deleted = false;
end;