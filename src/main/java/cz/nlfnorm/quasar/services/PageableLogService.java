package cz.nlfnorm.quasar.services;

import java.util.Map;

import cz.nlfnorm.dto.PageDto;
import cz.nlfnorm.services.IdentifiableByLongService;

public interface PageableLogService<T> extends IdentifiableByLongService<T>{
	
	PageDto getPage(Map<String, Object> criteria, int pageNumber);
	
}
