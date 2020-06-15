package com.alanco.url.dbutility;

import com.alanco.url.entity.URLCounter;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface URLMapper {

	@DaoFactory
	URLDAO urlDao();
	
	@DaoFactory
	URLCounter urlCounter();
}