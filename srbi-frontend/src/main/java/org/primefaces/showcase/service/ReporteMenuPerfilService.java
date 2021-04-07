package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.ReporteMenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import org.primefaces.showcase.common.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Named
@ApplicationScoped
public class ReporteMenuPerfilService {

    private static final Logger logger = LoggerFactory.getLogger(ReporteMenuPerfilService.class);

    List<ReporteMenuPerfil> reporteMenuPerfiles;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        reporteMenuPerfiles = findAll();
    }

    public List<ReporteMenuPerfil> getReporteMenuPerfiles() {
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
    public List<ReporteMenuPerfil> findAll() {
        WebTarget webTarget = restClient.getWebTarget("reportemenuperfiles");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<ReporteMenuPerfil>>() {});
    }

    public List<ReporteMenuPerfil> findByIdMenuPerfil(final long idMenuPerfil) {
        System.out.println(" findByIdMenuPerfil servicio ");
        WebTarget webTarget = restClient.getWebTarget("reportemenuperfiles/findByIdMenuPerfil/"+ idMenuPerfil);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        System.out.println(" findByIdMenuPerfil servicio  fin ");
        return invocationBuilder.get(new GenericType<List<ReporteMenuPerfil>>() {});

    }
}
