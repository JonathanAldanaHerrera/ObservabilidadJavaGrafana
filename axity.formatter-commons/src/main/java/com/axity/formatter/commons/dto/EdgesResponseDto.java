package com.axity.formatter.commons.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class EdgesResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Edge> edges;

    @Getter
    @Setter
    public static class Edge implements Serializable {
        private static final long serialVersionUID = 1L;

        private String id;
        private String source;
        private String target;
        private boolean highlighted;
        private String color;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
