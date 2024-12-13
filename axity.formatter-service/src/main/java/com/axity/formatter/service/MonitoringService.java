package com.axity.formatter.service;

import com.axity.formatter.commons.grafana.EdgesResponseDto;
import com.axity.formatter.commons.grafana.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;

public interface MonitoringService {
    GenericResponseDto<NodesResponseDto> getNodes();

    GenericResponseDto<EdgesResponseDto> getEdges();
}
