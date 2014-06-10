begin;
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 6, 'ROLE_PORTAL_ADMIN', 'Správa informačního portálu', 'Spravuje uživatelské účty, aktivuje služby,...', true);
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 7, 'ROLE_AUDITOR', 'Auditor', 'Možnost vytvářet auditlog a training log', true);
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 8, 'ROLE_AUDITOR_ADMIN', 'Auditor správce', 'Je mu umožněno schvalovat Audit logy auditorů, spravovať výnimky,...', true);	update authority  set  name = 'Super administrátor', short_description='Nemozený přístup'  where id = 4;
	ALTER TABLE users ADD COLUMN discriminator character varying(10);
	ALTER TABLE users ALTER COLUMN discriminator SET NOT NULL;
	update authority  set  name = 'Super administrátor', short_description='Nemozený přístup'  where id = 4;
end;