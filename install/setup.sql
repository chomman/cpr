begin;
	CREATE TEXT SEARCH DICTIONARY cspell (template=ispell, dictfile = czech, afffile=czech, stopwords=czech);
	CREATE TEXT SEARCH DICTIONARY czech_synonym ( TEMPLATE = synonym, SYNONYMS = czech );
	CREATE TEXT SEARCH CONFIGURATION cs (copy=english);
	ALTER TEXT SEARCH CONFIGURATION cs   ALTER MAPPING FOR word, asciiword WITH cspell, simple, czech_synonym;
	SET default_text_search_config = 'cs';
	ALTER TEXT SEARCH CONFIGURATION english RENAME TO en;
	CREATE EXTENSION unaccent;
end;