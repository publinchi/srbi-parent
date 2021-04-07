package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
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
import java.util.List;

@Named
@ApplicationScoped
public class MenuPerfilService {

    private static final Logger logger = LoggerFactory.getLogger(MenuPerfilService.class);

    List<MenuPerfil> menuPerfiles;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        menuPerfiles = findAll();
    }

    public List<MenuPerfil> getMenuPerfiles() {
        return findAll();
    }

    /*
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
        WebTarget webTarget = restClient.getWebTarget("usuarios/" + usuario.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.delete();
    }

    public UsuarioPerfil find(long idUsuario) {
        return null;
    }

    public UsuarioPerfil findByLogin(final String login) {
        WebTarget webTarget = restClient.getWebTarget("usuarios/login/" + login);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.get(UsuarioPerfil.class);
        } catch (NotFoundException notFoundException) {
            return null;
        }
    }
*/
    public List<MenuPerfil> findAll() {
        WebTarget webTarget = restClient.getWebTarget("menuperfiles");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<MenuPerfil>>() {});
    }

    public MenuPerfil findByIdPerfil(final long idPerfil) {
        WebTarget webTarget = restClient.getWebTarget("menuperfiles/findByIdPerfil/" + idPerfil);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.get(MenuPerfil.class);
        } catch (NotFoundException notFoundException) {
            return null;
        }
    }
}
