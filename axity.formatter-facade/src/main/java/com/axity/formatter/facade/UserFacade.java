package com.axity.formatter.facade;

import java.util.List;

import com.axity.formatter.commons.dto.UserDto;
import com.axity.formatter.commons.request.PaginatedRequestDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.commons.response.PaginatedResponseDto;

import graphql.schema.DataFetchingEnvironment;

/**
 * Interface UserFacade
 * 
 * @author username@axity.com
 */
public interface UserFacade
{
  /**
   * Method to get Users
   * 
   * @param request
   * @return
   */
  PaginatedResponseDto<UserDto> findUsers( PaginatedRequestDto request );

  /**
   * Method to get User by id
   * 
   * @param id
   * @return
   */
  GenericResponseDto<UserDto> find( Integer id );

  /**
   * Method to create a User
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<UserDto> create( UserDto dto );

  /**
   * Method to update a User
   * 
   * @param dto
   * @return
   */
  GenericResponseDto<Boolean> update( UserDto dto );

  /**
   * Method to delete a User
   * 
   * @param id
   * @return
   */
  GenericResponseDto<Boolean> delete( Integer id );
}
