package com.axity.formatter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axity.formatter.commons.dto.RoleDto;
import com.axity.formatter.commons.enums.ErrorCode;
import com.axity.formatter.commons.exception.BusinessException;
import com.axity.formatter.commons.request.MessageDto;
import com.axity.formatter.commons.request.PaginatedRequestDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.commons.response.PaginatedResponseDto;
import com.axity.formatter.model.RoleDO;
import com.axity.formatter.model.QRoleDO;
import com.axity.formatter.persistence.RolePersistence;
import com.axity.formatter.service.RoleService;
import com.github.dozermapper.core.Mapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * Class RoleServiceImpl
 * 
 * @author username@axity.com
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService
{
  @Autowired
  private RolePersistence rolePersistence;

  @Autowired
  private Mapper mapper;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedResponseDto<RoleDto> findRoles( PaginatedRequestDto request )
  {
    log.debug( "%s", request );

    int page = request.getOffset() / request.getLimit();
    Pageable pageRequest = PageRequest.of( page, request.getLimit(), Sort.by( "id" ) );

    var paged = this.rolePersistence.findAll( pageRequest );

    var result = new PaginatedResponseDto<RoleDto>( page, request.getLimit(), paged.getTotalElements() );

    paged.stream().forEach( x -> result.getData().add( this.transform( x ) ) );

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<RoleDto> find( Integer id )
  {
    GenericResponseDto<RoleDto> response = null;

    var optional = this.rolePersistence.findById( id );
    if( optional.isPresent() )
    {
      response = new GenericResponseDto<>();
      response.setBody( this.transform( optional.get() ) );
    }

    return response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<RoleDto> create( RoleDto dto )
  {

    RoleDO entity = new RoleDO();
    this.mapper.map( dto, entity );
    entity.setId(null);

    this.rolePersistence.save( entity );

    dto.setId(entity.getId());

    return new GenericResponseDto<>( dto );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> update( RoleDto dto )
  {
    var optional = this.rolePersistence.findById( dto.getId() );
    if( optional.isEmpty() )
    {
      throw new BusinessException( ErrorCode.FORMATTER_NOT_FOUND );
    }

    var entity = optional.get();
    
    
    entity.setName( dto.getName() );
    // TODO: Actualizar entity.Users (?) 

    this.rolePersistence.save( entity );

    return new GenericResponseDto<>( true );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericResponseDto<Boolean> delete( Integer id )
  {
    var optional = this.rolePersistence.findById( id );
    if( optional.isEmpty() )
    {
      throw new BusinessException( ErrorCode.FORMATTER_NOT_FOUND );
    }

    var entity = optional.get();
    this.rolePersistence.delete( entity );

    return new GenericResponseDto<>( true );
  }

  private RoleDto transform( RoleDO entity )
  {
    RoleDto dto = null;
    if( entity != null )
    {
      dto = this.mapper.map( entity, RoleDto.class );
    }
    return dto;
  }
}
