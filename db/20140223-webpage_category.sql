begin;
	
	insert into webpage_category (id, changed, created, enabled, name) VALUES (4, NOW(), NOW(), true, 'V submenu: Databáze harmonizovaných norem' );
	update webpage_category set name = 'V submenu: CPR' WHERE id = 3; 

end;