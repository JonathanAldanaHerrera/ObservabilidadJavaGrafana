package com.axity.formatter.commons.grafana;

import java.io.Serializable;

public class EndpointNegocioIdClass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long endpoint;
    private Long negocio;

    public Long getPkEndpoint() {
        return endpoint;
    }

    public void setPkEndpoint(Long endpoint) {
        this.endpoint = endpoint;
    }

    public Long getPkNegocio() {
        return negocio;
    }

    public void setPkNegocio(Long pkNegocio) {
        this.negocio = pkNegocio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointNegocioIdClass that = (EndpointNegocioIdClass) o;
        return endpoint.equals(that.endpoint) && negocio.equals(that.negocio);
    }

    @Override
    public int hashCode() {
        return 31 * endpoint.hashCode() + negocio.hashCode();
    }
}
