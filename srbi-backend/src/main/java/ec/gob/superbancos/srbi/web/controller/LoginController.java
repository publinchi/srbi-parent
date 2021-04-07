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

    @PostMapping( "/validarLDAP/")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario validarLDAP(@RequestBody final Usuario resource, final HttpServletResponse response) {
    //public boolean validarLDAP(@RequestBody final Usuario resource) {
        Preconditions.checkNotNull(resource);
        //final Usuario foo = service.validarLDAP(resource);
        final Usuario foo = service.validarLDAP(resource.getLogin(), resource.getContrasenia());
        if(Objects.isNull(foo)) {
            System.out.println("es null el objeto");
            //return false;
        }
        else
            if (foo.getId()>0)
            {
                System.out.println("id" + foo.getId());
                //return true;
            }
        System.out.println("id" + foo.getId());
        //return false;
        return foo;
    }
}
