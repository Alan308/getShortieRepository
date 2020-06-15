package com.alanco.url.dbutility;

import com.alanco.url.entity.URL;
import com.alanco.url.entity.URLCounter;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.annotations.*;


@Dao
public interface URLDAO {

	@Query("SELECT id shortcode FROM url Where longname = :longname ")
	Row findShortCodeByLongName(String longname);
	
	@Query("UPDATE url SET lastusedate = :lastusedate WHERE shortcode = :shortcode")
	void updateLastUseDate(String lastusedate, String shortcode);
	
	@Select(customWhereClause = "longname = :searchString")
	PagingIterable<URL> findByLongName(String searchString);

	@Select
	URL findByShortCode(String shortcode);
	
	@Query("SELECT * FROM ${tableId}")
	PagingIterable<URL> findAll1();
	
	@Insert
	 void save(URL url);
	
	@Update(customWhereClause = "id = :id")
	void updateIfIDMatches(URL url, int id);

	@Delete
	  void delete(URL url);
	
	@Select
	URL findById(int id);
	
	@Query("SELECT * FROM ${tableId}")
	PagingIterable<URLCounter>findAll();
	
	@Insert
	void save(URLCounter urlcounter);
	
	@Query("UPDATE urlcounter SET usage = usage + 1 WHERE id = :id")
	void updateCouter(int id);
	
	@Update(customWhereClause = "id = :id")
	void updateIfCounterIDMatches(URLCounter urlcounter, int id);

	@Delete
	  void delete(URLCounter urlcounter);
}