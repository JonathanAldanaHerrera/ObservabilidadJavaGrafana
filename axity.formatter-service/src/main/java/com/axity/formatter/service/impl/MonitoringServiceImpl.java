package com.axity.formatter.service.impl;

import com.axity.formatter.commons.grafana.EdgesResponseDto;
import com.axity.formatter.commons.grafana.NodesResponseDto;
import com.axity.formatter.commons.response.GenericResponseDto;
import com.axity.formatter.persistence.*;
import com.axity.formatter.service.MonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    LogsRepository logsRepository;

    @Autowired
    NegociosRepository negociosRepository;

    @Autowired
    MicroserviciosRepository microserviciosRepository;

    @Autowired
    EndpointsRepository endpointsRepository;

    @Autowired
    EndpointNegocioRepository endpointNegocioRepository;


    @Override
    public GenericResponseDto<NodesResponseDto> getNodes() {
        List<NodesResponseDto.Node> nodes = new ArrayList<>();

        // Obtener los conteos de los códigos de estado HTTP
        List<Object[]> statusCounts = logsRepository.findStatusCodeCountByPathAndMethod();

        // Crear mapas para almacenar los conteos por path y method, microservicio y negocio
        Map<String, Map<String, Long[]>> statusCountMap = new HashMap<>();
        Map<Long, Long[]> microserviceArcMap = new HashMap<>();
        Map<Long, Long[]> negocioArcMap = new HashMap<>();

        // Rellenamos los mapas con los conteos de los códigos de estado
        for (Object[] result : statusCounts) {
            String path = (String) result[0];
            String method = (String) result[1];
            Long count2xx = (Long) result[2];
            Long countOtherCodes = (Long) result[3];

            // Almacenar el conteo de 2xx y otros códigos para cada path y method
            statusCountMap
                    .computeIfAbsent(path, k -> new HashMap<>())
                    .put(method, new Long[]{count2xx, countOtherCodes});
        }

        // Obtener nodos de endpoints
        endpointsRepository.findAll().forEach(endpoint -> {
            String path = endpoint.getPath();
            String method = endpoint.getMethod();

            // Obtener el conteo de 2xx y otros códigos desde el mapa
            Long[] counts = statusCountMap
                    .getOrDefault(path, new HashMap<>())
                    .getOrDefault(method, new Long[]{0L, 0L});

            Long successCount = counts[0];  // 2xx
            Long otherCount = counts[1];    // Otros códigos

            Long totalCount = successCount + otherCount;

            // Calcular el porcentaje de 2xx y su complemento
            Double arcSuccess = totalCount > 0 ? successCount / (double) totalCount : 0.0;
            Double arcErrors = 1 - arcSuccess;


            // Crear el nodo para el endpoint
            NodesResponseDto.Node node = createNode(
                    "endpoint_",
                    endpoint.getId(),
                    endpoint.getPath(),
                    "Endpoint",
                    arcSuccess,
                    arcErrors,
                    false
            );

            nodes.add(node);


            //-- Obtenemos los valores de arc para cada microservicio --//
            // Obtener el ID del microservicio relacionado con este endpoint
            Long microserviceId = endpoint.getMicroservicios().getId();

            // Agregar los valores de arc a la entrada del microservicio en el mapa
            microserviceArcMap.compute(microserviceId, (id, currentArc) -> {
                if (currentArc == null) {
                    return new Long[]{successCount, otherCount};  // Inicializamos con los valores del primer endpoint
                } else {
                    currentArc[0] += successCount;  // Acumular 2xx
                    currentArc[1] += otherCount;    // Acumular otros códigos
                    return currentArc;
                }
            });




            //-- Obtenemos los valores de arc para cada negocio --//
            // Obtener los negocioIds de la tabla intermedia 'negocio_endpoint'
            List<Long> negocioIds = endpointNegocioRepository.findNegocioIdsByEndpointId(endpoint.getId());

            // Verificar si hay negocios asociados con este endpoint
            for (Long negocioId : negocioIds) {
                // Agregar los valores de arc a la entrada del negocio en el mapa
                negocioArcMap.compute(negocioId, (id, currentArc) -> {
                    if (currentArc == null) {
                        return new Long[]{successCount, otherCount};  // Inicializamos con los valores del primer negocio
                    } else {
                        currentArc[0] += successCount;  // Acumular 2xx
                        currentArc[1] += otherCount;    // Acumular otros códigos
                        return currentArc;
                    }
                });
            }



        });

        // Generamos los nodos para cada microservicio
        microserviciosRepository.findAll().forEach(service -> {
            Long microserviceId = service.getId();
            Long[] arcCounts = microserviceArcMap.getOrDefault(microserviceId, new Long[]{0L, 0L});

            Long successCount = arcCounts[0];
            Long otherCount = arcCounts[1];
            Long totalCount = successCount + otherCount;

            // Calcular el porcentaje de 2xx y su complemento
            Double arcSuccess = totalCount > 0 ? successCount / (double) totalCount : 0.0;
            Double arcErrors = 1 - arcSuccess;

            // Crear el nodo para el microservicio
            NodesResponseDto.Node node = createNode(
                    "microservicio_",
                    service.getId(),
                    service.getName(),
                    "Microservicio",
                    arcSuccess,
                    arcErrors,
                    false
            );

            nodes.add(node);
        });

        // Generamos los nodos para cada negocio
        negociosRepository.findAll().forEach(rule -> {
            Long negocioId = rule.getId();
            Long[] arcCounts = negocioArcMap.getOrDefault(negocioId, new Long[]{0L, 0L});

            Long successCount = arcCounts[0];
            Long otherCount = arcCounts[1];
            Long totalCount = successCount + otherCount;

            // Calcular el porcentaje de 2xx y su complemento
            Double arcSuccess = totalCount > 0 ? successCount / (double) totalCount : 0.0;
            Double arcErrors = 1 - arcSuccess;

            // Crear el nodo para la regla de negocio
            NodesResponseDto.Node node = createNode(
                    "negocio_",
                    rule.getId(),
                    rule.getName(),
                    "Regla de Negocio",
                    arcSuccess,
                    arcErrors,
                    false
            );

            nodes.add(node);
        });

        // Crear el DTO de respuesta
        NodesResponseDto nodesResponseDto = new NodesResponseDto();
        nodesResponseDto.setNodes(nodes);

        // Crear la respuesta genérica con los nodos
        GenericResponseDto<NodesResponseDto> response = new GenericResponseDto<>();
        response.setBody(nodesResponseDto);

        // Retornar la respuesta
        return response;
    }

    private NodesResponseDto.Node createNode(String prefix, Long long1, String title, String subtitle, double successRate, double errorRate, boolean isHighlighted) {
        NodesResponseDto.Node node = new NodesResponseDto.Node();
        node.setId(prefix + long1);
        node.setTitle(title);
        node.setSubtitle(subtitle);
        node.setHighlighted(isHighlighted);
        node.setArc__success(successRate);
        node.setArc__errors(errorRate);
        return node;
    }



    @Override
    public GenericResponseDto<EdgesResponseDto> getEdges() {
        List<Object[]> relations = endpointNegocioRepository.findNegocioEndpointRelations();
        List<EdgesResponseDto.Edge> edges = new ArrayList<>();

        // Relaciones entre Negocios y Endpoints
        for (Object[] row : relations) {
            Integer negocioId = (Integer) row[0];
            Integer endpointId = (Integer) row[1];

            EdgesResponseDto.Edge edge = createEdge("edge_neg_ms_" + negocioId + "_" + endpointId,
                    "negocio_" + negocioId, "endpoint_" + endpointId);
            edges.add(edge);
        }

        // Relaciones entre Microservicios y Endpoints
        endpointsRepository.findAll().forEach(endpoint -> {
            Long endpointId = endpoint.getId();
            Long microservicioId = endpoint.getMicroservicios().getId();

            EdgesResponseDto.Edge edge = createEdge("edge_end_ms_" + endpointId + "_" + microservicioId,
                    "endpoint_" + endpointId, "microservicio_" + microservicioId);
            edges.add(edge);
        });

        // Crear el DTO de respuesta
        EdgesResponseDto edgesResponseDto = new EdgesResponseDto();
        edgesResponseDto.setEdges(edges);

        GenericResponseDto<EdgesResponseDto> response = new GenericResponseDto<>();
        response.setBody(edgesResponseDto);

        return response;
    }

    private EdgesResponseDto.Edge createEdge(String id, String source, String target) {
        EdgesResponseDto.Edge edge = new EdgesResponseDto.Edge();
        edge.setId(id);
        edge.setSource(source);
        edge.setTarget(target);
        edge.setColor("green");
        edge.setHighlighted(false);
        return edge;
    }
}
