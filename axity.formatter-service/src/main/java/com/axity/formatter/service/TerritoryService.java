package com.axity.formatter.service;

import java.util.List;

import com.axity.formatter.commons.dto.TerritoryDto;
import com.axity.formatter.commons.request.PaginatedRequestDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.commons.response.PaginatedResponseDto;

/**
 * Interface TerritoryService
 * 
 * @author username@axity.com
 */
public interface TerritoryService
{

  /**
   * Method to get Territorys
   * 
   * @param request
   * @return
   */
  PaginatedResponseDto<TerritoryDto> findTerritorys( PaginatedRequestDto request );

  /**
   * Method to get Territory by id
   * 
   * @param id
   * @return
   */
  GenericResponseDto<TerritoryDto> find( Integer id );

  /**
   * Method to create a Territory
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<TerritoryDto> create( TerritoryDto dto );

  /**
   * Method to update a Territory
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<Boolean> update( TerritoryDto dto );

  /**
   * Method to delete a Territory
   * 
   * @param id
   * @return
   */
  GenericResponseDto<Boolean> delete( Integer id );
}
