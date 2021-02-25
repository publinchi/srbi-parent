package ec.gob.superbancos.srbi.web.controller;

import com.google.common.base.Preconditions;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import ec.gob.superbancos.srbi.persistence.service.IUsuarioService;
import ec.gob.superbancos.srbi.web.hateoas.event.ResourceCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping(value = "/logins")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IUsuarioService service;

    public LoginController() {
        super();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean login(@RequestBody final Usuario resource, final HttpServletResponse response) {
        Preconditions.checkNotNull(resource);
        final Usuario foo = service.findByLogin(resource.getLogin());
        if(Objects.isNull(foo)) {
            return false;
        }

        final Long idOfCreatedResource = foo.getId();
        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));

        if(resource.getContrasenia().equals(foo.getContrasenia()))
            return true;
        return false;
    }
}
