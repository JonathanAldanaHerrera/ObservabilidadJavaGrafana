package com.axity.formatter.controller;

import com.axity.formatter.commons.dto.EdgesResponseDto;
import com.axity.formatter.commons.dto.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.facade.MonitoringFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodegraph")
@CrossOrigin
@Slf4j
public class MetrincsController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private MonitoringFacade monitoringFacade;

    /***
     * Method to get Nodes
     *
     * @return A JSON with formatted Nodes
     */
    @GetMapping(path = "/nodes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto<NodesResponseDto>> getNodes() {

        var result = this.monitoringFacade.getNodes();

        HttpStatus status = HttpStatus.OK;
        if (result == null) {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(result, status);

    }

    /***
     * Method to get Nodes
     *
     * @return A JSON with formatted Edges
     */
    @GetMapping(path = "/edges", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto<EdgesResponseDto>> getEdges() {

        var result = this.monitoringFacade.getEdges();

        HttpStatus status = HttpStatus.OK;
        if( result == null )
        {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>( result, status );
    }
}