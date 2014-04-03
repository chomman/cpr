begin;
	  
	DROP SEQUENCE webpage_category_id_seq;
	DROP SEQUENCE webpage_content_id_seq;
	DROP TABLE webpage CASCADE;
	DROP TABLE webpage_category CASCADE;
	DROP TABLE webpage_content CASCADE;
	DROP TABLE tag;
	
	delete from user_has_authority where authority_id = 2;
	delete from authority where id = 2;
	
	
end;