package com.axity.formatter.service;

import com.axity.formatter.commons.dto.EdgesResponseDto;
import com.axity.formatter.commons.dto.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;

public interface MonitoringService {
    GenericResponseDto<NodesResponseDto> getNodes();

    GenericResponseDto<EdgesResponseDto> getEdges();
}
