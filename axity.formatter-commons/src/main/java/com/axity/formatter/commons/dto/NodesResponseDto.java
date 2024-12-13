package com.axity.formatter.commons.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class NodesResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private List<Node> nodes;

    @Getter
    @Setter
    public static class Node {
        private String id;
        private String title;
        private String subtitle;
        private boolean highlighted;

        private double arc__success;

        private double arc__errors;
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
          Gson gson = new GsonBuilder().disableHtmlEscaping().create();
          return gson.toJson( this );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
      Gson gson = new GsonBuilder().disableHtmlEscaping().create();
      return gson.toJson( this );
    }
}
