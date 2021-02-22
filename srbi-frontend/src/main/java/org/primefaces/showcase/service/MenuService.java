package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.Menu;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.primefaces.showcase.common.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
public class MenuService {

    List<Menu> menus;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        menus = findAll();
    }

    public List<Menu> getMenus() {
        return findAll();
    }

    public List<Menu> getProducts(int size) {

        if (size > menus.size()) {
            Random rand = new Random();

            List<Menu> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(menus.size());
                randomList.add(menus.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(menus).subList(0, size);
        }

    }

    public Menu save(Menu menu) {
        Menu clientResponse;

        if(menu.getId() <=0) {
            WebTarget webTarget = restClient.getWebTarget("menus");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.post(Entity.entity(menu, MediaType.APPLICATION_JSON), Menu.class);
        } else {
            WebTarget webTarget = restClient.getWebTarget("menus/" + menu.getId());
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.put(Entity.entity(menu, MediaType.APPLICATION_JSON), Menu.class);
        }

        return clientResponse;
    }

    public void delete(Menu menu) {
        WebTarget webTarget = restClient.getWebTarget("menus/" + menu.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.delete();
    }

    public Menu find(long id) {
        return null;
    }

    public List<Menu> findAll() {
        WebTarget webTarget = restClient.getWebTarget("menus");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<Menu>>() {});
    }

}
