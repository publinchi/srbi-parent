package org.primefaces.showcase.view.data.datatable;

import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.primefaces.PrimeFaces;
import org.primefaces.showcase.service.UsuarioService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class UsuarioCrudView implements Serializable {

    private List<Usuario> usuarios;

    private Usuario selectedUsuario;

    private List<Usuario> selectedUsuarios;

    @Inject
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        this.usuarios = this.usuarioService.getUsuarios();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public List<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(List<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }

    public void openNew() {
        this.selectedUsuario = new Usuario();
    }

    public void save() {
        if (this.selectedUsuario.getId() <= 0) {
            this.selectedUsuario.setFechaCreacion(Calendar.getInstance().getTime());
            this.selectedUsuario.setIp("");
            this.selectedUsuario.setContrasenia("");
            Usuario usuario = this.usuarioService.save(this.selectedUsuario);
            if(Objects.nonNull(usuario)) {
                this.usuarios.add(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Agregado"));
            }
        }
        else {
            this.selectedUsuario.setFechaModificacion(Calendar.getInstance().getTime());
            try {
                this.usuarioService.save(this.selectedUsuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Actualizado"));
            } catch(BadRequestException badRequestException){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al actualizar",
                        "Usuario no pudo ser actualizado!!!"));
            }
        }

        this.selectedUsuario = null;

        PrimeFaces.current().executeScript("PF('manageUsuarioDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
    }

    public void delete() {
        this.usuarioService.delete(this.selectedUsuario);
        this.usuarios.remove(this.selectedUsuario);
        if(Objects.nonNull(this.selectedUsuarios))
            this.selectedUsuarios.remove(this.selectedUsuario);
        this.selectedUsuario = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Removido"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
    }

    public void findAll() {
        this.usuarioService.findAll();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUsuarios()) {
            int size = this.selectedUsuarios.size();
            return size > 1 ? size + " Usuarios Seleccionados" : "1 Usuario Seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSelectedUsuarios() {
        return this.selectedUsuarios != null && !this.selectedUsuarios.isEmpty();
    }

    public void deleteSelectedUsuarios() {
        for (Usuario usuario : this.selectedUsuarios) {
            this.usuarioService.delete(usuario);
        }

        this.usuarios.removeAll(this.selectedUsuarios);
        this.selectedUsuarios = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuarios removidos"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        PrimeFaces.current().executeScript("PF('dtUsuarios').clearFilters()");
    }

}