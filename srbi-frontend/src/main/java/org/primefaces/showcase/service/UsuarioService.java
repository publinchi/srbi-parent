package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.primefaces.showcase.common.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Named
@ApplicationScoped
public class UsuarioService {

    List<Usuario> usuarios;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        usuarios = findAll();
    }

    public List<Usuario> getUsuarios() {
        return findAll();
    }

    public List<Usuario> getProducts(int size) {

        if (size > usuarios.size()) {
            Random rand = new Random();

            List<Usuario> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(usuarios.size());
                randomList.add(usuarios.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(usuarios).subList(0, size);
        }

    }

    public Usuario save(Usuario usuario) {
        Usuario clientResponse;

        if(usuario.getId() <=0) {
            WebTarget webTarget = restClient.getWebTarget("usuarios");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.post(Entity.entity(usuario, MediaType.APPLICATION_JSON), Usuario.class);
        } else {
            WebTarget webTarget = restClient.getWebTarget("usuarios/" + usuario.getId());
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.put(Entity.entity(usuario, MediaType.APPLICATION_JSON), Usuario.class);
        }

        return clientResponse;
    }

    public void delete(Usuario usuario) {
        WebTarget webTarget = restClient.getWebTarget("usuarios/" + usuario.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.delete();
    }

    public Usuario find(long id) {
        return null;
    }

    public Usuario findByLogin(final String login) {
        WebTarget webTarget = restClient.getWebTarget("usuarios/login/" + login);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.get(Usuario.class);
        } catch (NotFoundException notFoundException) {
            return null;
        }
    }

    public List<Usuario> findAll() {
        WebTarget webTarget = restClient.getWebTarget("usuarios");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<Usuario>>() {});
    }

}
