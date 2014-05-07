begin;
	ALTER TABLE exception_log ADD COLUMN query_params text;
	ALTER TABLE exception_log ADD COLUMN request_headers text;
	ALTER TABLE exception_log ADD COLUMN request_params text;
	ALTER TABLE users ADD COLUMN sgp_password character varying(48);
	ALTER TABLE user_info ADD COLUMN sgp_sync_ok boolean;
end;