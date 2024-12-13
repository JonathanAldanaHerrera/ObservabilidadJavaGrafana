package com.axity.formatter.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axity.formatter.model.CountryDO;

/**
 * Persistence interface of  de {@link com.axity.formatter.model.CountryDO}
 * 
 * @author username@axity.com
 */
@Repository
public interface CountryPersistence extends JpaRepository<CountryDO, Integer>
{
  // Agregar consultas personalizadas
}
