begin;
	INSERT INTO authority (id, code, name, short_description) VALUES ( 6, 'ROLE_PORTAL_ADMIN', 'Správa informačního portálu', 'Spravuje uživatelské účty, aktivuje služby,...');
	INSERT INTO authority (id, code, name, short_description) VALUES ( 7, 'ROLE_AUDITOR', 'Auditor', 'Možnost vytvářet auditlog a training log');
	INSERT INTO authority (id, code, name, short_description) VALUES ( 8, 'ROLE_AUDITOR_ADMIN', 'Auditor správce', 'Je mu umožněno schvalovat Audit logy auditorů, spravovať výnimky,...');
	update authority  set  name = 'Super administrátor', short_description='Nemozený přístup'  where id = 4;
end;