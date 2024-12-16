package com.axity.formatter.persistence;

import com.axity.formatter.model.LogsDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<LogsDO, Long> {
	@Query(value = """
	        SELECT 
	            JSON_UNQUOTE(JSON_EXTRACT(l.Message, '$.RequestPath')) AS path,
	            JSON_UNQUOTE(JSON_EXTRACT(l.Message, '$.Method')) AS method,
	            COUNT(CASE 
	                    WHEN JSON_UNQUOTE(JSON_EXTRACT(l.Message, '$.StatusCode')) LIKE '2%' THEN 1
	                    ELSE NULL
	                END) AS count_2xx,
	            COUNT(CASE 
	                    WHEN JSON_UNQUOTE(JSON_EXTRACT(l.Message, '$.StatusCode')) NOT LIKE '2%' THEN 1
	                    ELSE NULL
	                END) AS count_other_codes
	        FROM logs l
	        GROUP BY path, method
	    """, nativeQuery = true)
	    List<Object[]> findStatusCodeCountByPathAndMethod();
}