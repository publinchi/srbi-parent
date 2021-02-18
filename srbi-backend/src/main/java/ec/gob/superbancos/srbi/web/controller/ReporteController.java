package ec.gob.superbancos.srbi.web.controller;

import com.google.common.base.Preconditions;
import ec.gob.superbancos.srbi.persistence.model.Reporte;
import ec.gob.superbancos.srbi.persistence.service.IReporteService;
import ec.gob.superbancos.srbi.web.exception.MyResourceNotFoundException;
import ec.gob.superbancos.srbi.web.hateoas.event.PaginatedResultsRetrievedEvent;
import ec.gob.superbancos.srbi.web.hateoas.event.ResourceCreatedEvent;
import ec.gob.superbancos.srbi.web.hateoas.event.SingleResourceRetrievedEvent;
import ec.gob.superbancos.srbi.web.util.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/reportes")
public class ReporteController {

    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IReporteService service;

    public ReporteController() {
        super();
    }

    // API

    // Note: the global filter overrides the ETag value we set here. We can still analyze its behaviour in the Integration Test.
    /*@GetMapping(value = "/{id}/custom-etag")
    public ResponseEntity<Usuario> findByIdWithCustomEtag(@PathVariable("id") final Long id,
                                                          final HttpServletResponse response) {
        final Usuario usuario = RestPreconditions.checkFound(service.findById(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return ResponseEntity.ok()
                .eTag(Long.toString(usuario.getVersion()))
                .body(usuario);
    }*/

    // read - one

    @GetMapping(value = "/{id}")
    public Reporte findById(@PathVariable("id") final Long id, final HttpServletResponse response) {
        try {
            final Reporte resourceById = RestPreconditions.checkFound(service.findById(id));

            eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
            return resourceById;
        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario Not Found", exc);
        }

    }

    // read - all

    @GetMapping
    public List<Reporte> findAll() {
        return service.findAll();
    }

    @GetMapping(params = { "page", "size" })
    public List<Reporte> findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size,
                                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<Reporte> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Reporte>(Reporte.class, uriBuilder, response, page,
                resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }

    @GetMapping("/pageable")
    public List<Reporte> findPaginatedWithPageable(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                   final HttpServletResponse response) {
        final Page<Reporte> resultPage = service.findPaginated(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Reporte>(Reporte.class, uriBuilder, response,
                pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage.getContent();
    }

    // write

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reporte create(@RequestBody final Reporte resource, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final Reporte object = service.create(resource);
        final Long idOfCreatedResource = object.getId();

        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));

        return object;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody final Reporte resource) {
        Preconditions.checkNotNull(resource);
        RestPreconditions.checkFound(service.findById(resource.getId()));
        service.update(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") final Long id) {
        service.deleteById(id);
    }

    /*@ExceptionHandler({ CustomException1.class, CustomException2.class })
    public void handleException(final Exception ex) {
        final String error = "Application specific error handling";
        logger.error(error, ex);
    }*/
}
