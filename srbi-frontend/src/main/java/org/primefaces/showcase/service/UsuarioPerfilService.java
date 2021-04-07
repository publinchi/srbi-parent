package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import org.primefaces.showcase.common.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Named
@ApplicationScoped
public class UsuarioPerfilService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioPerfilService.class);

    List<UsuarioPerfil> usuariosPerfil;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        usuariosPerfil = findAll();
    }

    public List<UsuarioPerfil> getUsuariosPerfil() {
        return findAll();
    }

    public UsuarioPerfil save(UsuarioPerfil usuarioPerfil) throws BadRequestException {
        UsuarioPerfil clientResponse = null;

        if(usuarioPerfil.getId() <=0) {
            WebTarget webTarget = restClient.getWebTarget("usuarios");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.post(Entity.entity(usuarioPerfil, MediaType.APPLICATION_JSON), UsuarioPerfil.class);
        } else {
            WebTarget webTarget = restClient.getWebTarget("usuarios/" + usuarioPerfil.getId());
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            invocationBuilder.put(Entity.entity(usuarioPerfil, MediaType.APPLICATION_JSON), UsuarioPerfil.class);
        }

        return clientResponse;
    }

    public void delete(UsuarioPerfil usuario) {
        WebTarget webTarget = restClient.getWebTarget("usuarioperfiles/" + usuario.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.delete();
    }

    public UsuarioPerfil find(long idUsuario) {
        return null;
    }

    public UsuarioPerfil findByLogin(final String login) {
        WebTarget webTarget = restClient.getWebTarget("usuarioperfiles/login/" + login);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.get(UsuarioPerfil.class);
        } catch (NotFoundException notFoundException) {
            return null;
        }
    }

    public List<UsuarioPerfil> findAll() {
        WebTarget webTarget = restClient.getWebTarget("usuarioperfiles");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<UsuarioPerfil>>() {});
    }

    public UsuarioPerfil findByIdUsuario(final long IdUsuario) {
        System.out.println(" findIdUsuarioPerfil servicio " + IdUsuario);
        WebTarget webTarget = restClient.getWebTarget("usuarioperfiles/usuario/" + IdUsuario);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.get(UsuarioPerfil.class);
        } catch (NotFoundException notFoundException) {
            return null;
        }
    }

}
