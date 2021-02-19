package org.primefaces.showcase.common;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@Named
@SessionScoped
public class RestClient implements Serializable {
    /* utilizar las API de cliente Jersey para crear un cliente
  RESTful Java para realizar "GET" y "POST" solicitudes a servicio REST*/
    private transient Client client;

    /*
        web.xml
    <context-param>
        <param-name>metadata.serviceBaseURI</param-name>
        <param-value>http://localhost:8080/Rest-JSF-Web-PrimeFaces/webresources/</param-value>
    </context-param>
    Referencia al path para acceder a los servicios rest
    */
    String SERVICE_BASE_URI;

    /**
     * FacesContext contiene toda la información por solicitud del Estado en relación
     * con la tramitación de una sola solicitud JavaServer Faces, y la prestación de la
     * respuesta correspondiente. Se transmite a, y potencialmente modificado por, cada
     * fase del ciclo de vida de procesamiento de la solicitud.
     **/
    @PostConstruct
    protected void initialize() {
        /*getCurrentInstance () Devuelve el FacesContext instancia para la
        solicitud de que se está procesando por el subproceso actual.*/
        FacesContext fc = FacesContext.getCurrentInstance();

        /*Devuelva el ExternalContext ejemplo para este FacesContext instancia.
        Es válido para llamar a este método durante el inicio de la aplicación o el apagado
        OBTIENE EL VALOR CONSTANTE DECLARADO EN EL ARCHIVO web.xml
        */
        SERVICE_BASE_URI = fc.getExternalContext().getInitParameter("metadata.serviceBaseURI");

        client = ClientBuilder.newClient();
    }

    /*Recibe la solicitud, Si client jersey es null lo inicializa*/
    public WebTarget getWebTarget(String relativeUrl) {
        if (client == null) {
            initialize();
        }
        /*Devuelve la recepcion del path de llamada, ejemplo
        http://localhost:8080/Rest-JSF-Web-PrimeFaces/webresources/Producto*/
        return client.target(SERVICE_BASE_URI + relativeUrl);
    }

    /*Se especifica el tipo MIME para la respuesta en este caso json GET*/
    public <T> List clientGetResponse(String relativeUrl) {
        if (client == null) {
            initialize();
        }
        WebTarget webTarget = getWebTarget(relativeUrl);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<List<T>>() {});
    }

    //POST
    public <T> T clientPostResponse(String relativeUrl, T object){
        if (client == null) {
            initialize();
        }

        WebTarget webTarget = getWebTarget(relativeUrl);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return (T) invocationBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON), object.getClass());
    }

    public <T> T clientPutResponse(String relativeUrl, T object) {
        if (client == null) {
            initialize();
        }

        WebTarget webTarget = getWebTarget(relativeUrl);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return (T) invocationBuilder.put(Entity.entity(object, MediaType.APPLICATION_JSON), object.getClass());
    }

    public <T> T clientDeleteResponse(String relativeUrl) {
        if (client == null) {
            initialize();
        }

        WebTarget webTarget = getWebTarget(relativeUrl);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return (T) invocationBuilder.delete();
    }
}
