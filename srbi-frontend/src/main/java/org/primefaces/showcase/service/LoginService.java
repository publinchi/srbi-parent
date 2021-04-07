package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.primefaces.showcase.common.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


@Named
@ApplicationScoped
public class LoginService {

    @Inject
    private RestClient restClient;

    public boolean login(Usuario usuario) {
        WebTarget webTarget = restClient.getWebTarget("logins");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.post(Entity.entity(usuario, MediaType.APPLICATION_JSON), Boolean.class);
    }

    public Usuario validarCredencial(Usuario usuario) {
        Usuario clientResponse = null;
        System.out.println("service");
        System.out.println(usuario.getLogin());
        WebTarget webTarget = restClient.getWebTarget("logins/validarLDAP/");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.post(Entity.entity(usuario, MediaType.APPLICATION_JSON), Usuario.class);
    }



}
