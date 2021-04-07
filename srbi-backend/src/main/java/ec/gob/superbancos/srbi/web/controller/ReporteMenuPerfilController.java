package ec.gob.superbancos.srbi.web.controller;

import com.google.common.base.Preconditions;
import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.ReporteMenuPerfil;
import ec.gob.superbancos.srbi.persistence.service.IReporteMenuPerfilService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/reportemenuperfiles")
public class ReporteMenuPerfilController {

    private static final Logger logger = LoggerFactory.getLogger(ReporteMenuPerfilController.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IReporteMenuPerfilService service;

    public ReporteMenuPerfilController() {
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
    public ReporteMenuPerfil findById(@PathVariable("id") final Long id, final HttpServletResponse response) {
        try {
            final ReporteMenuPerfil resourceById = RestPreconditions.checkFound(service.findById(id));

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
    public List<ReporteMenuPerfil> findAll() {
        return service.findAll();
    }

    @GetMapping(params = { "page", "size" })
    public List<ReporteMenuPerfil> findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size,
                                                 final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<ReporteMenuPerfil> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<ReporteMenuPerfil>(ReporteMenuPerfil.class, uriBuilder, response, page,
                resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }

    @GetMapping("/pageable")
    public List<ReporteMenuPerfil> findPaginatedWithPageable(Pageable pageable, final UriComponentsBuilder uriBuilder,
                                                             final HttpServletResponse response) {
        final Page<ReporteMenuPerfil> resultPage = service.findPaginated(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<ReporteMenuPerfil>(ReporteMenuPerfil.class, uriBuilder, response,
                pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage.getContent();
    }

    // write

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteMenuPerfil create(@RequestBody final ReporteMenuPerfil resource, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final ReporteMenuPerfil foo = service.create(resource);
        final Long idOfCreatedResource = foo.getId();

        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));

        return foo;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @RequestBody final ReporteMenuPerfil resource) {
        Preconditions.checkNotNull(resource);
        if(Objects.equals(id, resource.getId())) {
            RestPreconditions.checkFound(service.findById(resource.getId()));
            service.update(resource);
        } else
            RestPreconditions.checkFound(false);
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
//, final HttpServletResponse response
    @GetMapping(value = "/findByIdMenuPerfil/{idMenuPerfil}")
    public List<ReporteMenuPerfil>  findByIdMenuPerfil(@PathVariable("idMenuPerfil") final Long idMenuPerfil) {
        System.out.println(" findByIdMenuPerfil controller ");
        List<ReporteMenuPerfil> reportl= new ArrayList<>();
        reportl=service.findByIdMenuPerfil(idMenuPerfil);
        System.out.println(" tamaño de lista " + reportl.size());
        return reportl;

    }
}
