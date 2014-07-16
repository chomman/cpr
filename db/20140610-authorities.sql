begin;
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 6, 'ROLE_PORTAL_ADMIN', 'Správa informačního portálu', 'Spravuje uživatelské účty, aktivuje služby,...', true);
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 7, 'ROLE_AUDITOR', 'Auditor', 'Možnost vytvářet auditlog a training log', true);
	INSERT INTO authority (id, code, name, short_description, enabled) VALUES ( 8, 'ROLE_AUDITOR_ADMIN', 'Auditor správce', 'Je mu umožněno schvalovat Audit logy auditorů, spravovať výnimky,...', true);	update authority  set  name = 'Super administrátor', short_description='Nemozený přístup'  where id = 4;
	update authority set  name = 'Super administrátor', short_description='Nemozený přístup'  where id = 4;
	update authority set  name = 'Přístup do administrace', short_description='Uživateli bude umožněno přihlášení do administrace systému nlfnorm.cz'  
	where id = 1;
end;