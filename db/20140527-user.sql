begin;
	ALTER TABLE user_has_online_publication ADD COLUMN alert_email_sent boolean;
	UPDATE user_has_online_publication SET alert_email_sent = false;
end;