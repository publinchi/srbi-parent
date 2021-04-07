package org.primefaces.showcase.service;

import ec.gob.superbancos.srbi.persistence.model.Menu;
import ec.gob.superbancos.srbi.persistence.model.Reporte;
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
public class ReporteService {

    List<Reporte> reportes;

    @Inject
    private RestClient restClient;

    @PostConstruct
    public void init() {
        reportes = findAll();
    }

    public List<Reporte> getReportes() {
        return findAll();
    }

    public List<Reporte> getProducts(int size) {

        if (size > reportes.size()) {
            Random rand = new Random();

            List<Reporte> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(reportes.size());
                randomList.add(reportes.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(reportes).subList(0, size);
        }

    }

    public Reporte save(Reporte reporte) {
        Reporte clientResponse;

        if(reporte.getId() <=0) {
            WebTarget webTarget = restClient.getWebTarget("reportes");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.post(Entity.entity(reporte, MediaType.APPLICATION_JSON), Reporte.class);
        } else {
            WebTarget webTarget = restClient.getWebTarget("reportes/" + reporte.getId());
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            clientResponse = invocationBuilder.put(Entity.entity(reporte, MediaType.APPLICATION_JSON), Reporte.class);
        }

        return clientResponse;
    }

    public void delete(Reporte reporte) {
        WebTarget webTarget = restClient.getWebTarget("reportes/" + reporte.getId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.delete();
    }

    public Reporte find(long id) {
        return null;
    }

    public List<Reporte> findAll() {
        WebTarget webTarget = restClient.getWebTarget("reportes");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<Reporte>>() {});
    }

    public List<Reporte> findIdUsuario(int IdUsuario) {
        WebTarget webTarget = restClient.getWebTarget("reportes/"+IdUsuario);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(new GenericType<List<Reporte>>() {});
    }

}
