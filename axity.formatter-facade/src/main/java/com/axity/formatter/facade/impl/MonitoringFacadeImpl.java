package com.axity.formatter.facade.impl;

import com.axity.formatter.commons.grafana.EdgesResponseDto;
import com.axity.formatter.commons.grafana.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.facade.MonitoringFacade;
import com.axity.formatter.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringFacadeImpl implements MonitoringFacade {

    @Autowired
    private MonitoringService monitoringService;

    @Override
    public GenericResponseDto<NodesResponseDto> getNodes() {
        return this.monitoringService.getNodes();
    }

    @Override
    public GenericResponseDto<EdgesResponseDto> getEdges() {
        return this.monitoringService.getEdges();
    }
}
