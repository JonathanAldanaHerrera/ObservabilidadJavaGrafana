package com.axity.formatter.service;

import java.util.List;

import com.axity.formatter.commons.dto.CountryDto;
import com.axity.formatter.commons.request.PaginatedRequestDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.commons.response.PaginatedResponseDto;

/**
 * Interface CountryService
 * 
 * @author username@axity.com
 */
public interface CountryService
{

  /**
   * Method to get Countrys
   * 
   * @param request
   * @return
   */
  PaginatedResponseDto<CountryDto> findCountrys( PaginatedRequestDto request );

  /**
   * Method to get Country by id
   * 
   * @param id
   * @return
   */
  GenericResponseDto<CountryDto> find( Integer id );

  /**
   * Method to create a Country
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<CountryDto> create( CountryDto dto );

  /**
   * Method to update a Country
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<Boolean> update( CountryDto dto );

  /**
   * Method to delete a Country
   * 
   * @param id
   * @return
   */
  GenericResponseDto<Boolean> delete( Integer id );
}
