package com.axity.formatter.facade;

import com.axity.formatter.commons.grafana.EdgesResponseDto;
import com.axity.formatter.commons.grafana.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;

public interface MonitoringFacade {
    GenericResponseDto<NodesResponseDto> getNodes();
    GenericResponseDto<EdgesResponseDto> getEdges();
}
