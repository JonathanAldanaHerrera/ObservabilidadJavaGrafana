package com.axity.formatter.facade;

import com.axity.formatter.commons.dto.EdgesResponseDto;
import com.axity.formatter.commons.dto.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;

public interface MonitoringFacade {
    GenericResponseDto<NodesResponseDto> getNodes();
    GenericResponseDto<EdgesResponseDto> getEdges();
}
