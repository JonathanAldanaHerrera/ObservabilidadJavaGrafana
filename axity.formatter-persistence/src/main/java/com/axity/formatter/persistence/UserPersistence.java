package com.axity.formatter.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axity.formatter.model.UserDO;

/**
 * Persistence interface of  de {@link com.axity.formatter.model.UserDO}
 * 
 * @author username@axity.com
 */
@Repository
public interface UserPersistence extends JpaRepository<UserDO, Integer>
{
  // Agregar consultas personalizadas
}
