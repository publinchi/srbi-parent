/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.showcase.view.login;

import ec.gob.superbancos.srbi.persistence.model.Usuario;
import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.showcase.service.LoginService;
import org.primefaces.showcase.service.MenuPerfilService;
import org.primefaces.showcase.service.UsuarioPerfilService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pestupinan
 */
@Named("loginView")
@SessionScoped
public class LoginView implements Serializable {

    @Getter
    @Setter
    private String userName, password;
    private Usuario usuario;
    private UsuarioPerfil usuarioPerfil;
    private static final Logger LOGGER = Logger.getLogger(LoginView.class.getName());
    @Inject
    private LoginService loginService;

    @Inject
    private MenuPerfilService menuPerfilService;
    @Inject
    private UsuarioPerfilService usuarioPerfilService;


    public LoginView() {}

    public void login() {
        Usuario usuario = new Usuario();
        usuario.setLogin(userName);
        usuario.setContrasenia(password);
        boolean exito = loginService.login(usuario);
        FacesContext context = FacesContext.getCurrentInstance();
        if(exito) {
            this.setUsuario(usuario);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Exitoso",
                    "Bienvenido."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            try {
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                session.setAttribute("loginView", this);
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/index.xhtml");
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
            LOGGER.log(Level.INFO, "Login OK");
            userName = null;
            password = null;
            return;
        } else {
            userName = null;
            password = null;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Error", "Nombre de usuario o contraseña incorrectos"));
            PrimeFaces.current().ajax().update("form:messages", "frmLogin:basic");
        }
    }

    public boolean isLoggedIn() {
        Logger.getLogger(LoginView.class.getName()).log(Level.INFO, "Cheking logged in");
        return getUsuario() != null;
    }

    public String logout() {
        setUsuario(null);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    private void loadAuthorizedModules() {
 /*
        if (gnrUsuaMods == null) {
            GnrUsuaModFacadeREST gnrUsuaModFacadeREST = new GnrUsuaModFacadeREST();
            String gnrUsuaModsString = gnrUsuaModFacadeREST.findByNombreUsuario(String.class, userName.toUpperCase());
            gnrUsuaMods = gson.fromJson(gnrUsuaModsString,
                    new TypeToken<List<GnrUsuaMod>>() {
                    }.getType());

            checkPermission();
        }
  */
    }

    private void checkPermission() {
    /*
        LOGGER.log(Level.INFO, "gnrUsuaMods size: {0}", gnrUsuaMods.size());

        for (GnrUsuaMod gnrUsuaMod : gnrUsuaMods) {
            switch (gnrUsuaMod.getGnrUsuaModPK().getCodModulo()) {
                case PEDIDOS_Y_DESPACHOS:
                    pedidoVisible = true;
                    LOGGER.log(Level.INFO, "pedidoVisible: {0}", pedidoVisible);
                    visitaVisible = true;
                    LOGGER.log(Level.INFO, "visitaVisible: {0}", visitaVisible);
                    break;
                case CUENTAS_POR_COBRAR:
                    cobroVisible = true;
                    LOGGER.log(Level.INFO, "cobroVisible: {0}", cobroVisible);
                    break;
                case CAJAS:
                    cajasVisible = true;
                    LOGGER.log(Level.INFO, "cajasVisible: {0}", cajasVisible);
                    break;
                case NOMINA:
                    nominaVisible = true;
                    LOGGER.log(Level.INFO, "nominaVisible: {0}", nominaVisible);
                    break;
                default:
                    break;
            }
        }
     */
    }

    public void validarLDAP() {
        int idPerfil=0;
        String nombreIndex="/index.xhtml";
        List<UsuarioPerfil> usuarioPerfiles= new ArrayList<>();
        Usuario usuario = new Usuario();
        Usuario usuarioValidado= new Usuario();
        usuario.setLogin(userName);
        usuario.setContrasenia(password);
        System.out.println(usuario.getLogin());
        usuarioValidado = loginService.validarCredencial(usuario);
        System.out.println("Id validado: "+ usuarioValidado.getId());
        System.out.println(usuarioValidado.getLogin());
        FacesContext context = FacesContext.getCurrentInstance();
        if(usuarioValidado.getId()>0) {
            this.setUsuario(usuarioValidado);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Exitoso",
                    "Bienvenido."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            PrimeFaces.current().ajax().update("form:messages", "frmLogin:basic");
            try {
                System.out.println("id usuario: "+usuario.getId());
                usuarioPerfil= usuarioPerfilService.findByIdUsuario(usuarioValidado.getId());
                System.out.println(usuarioPerfil.getIdPerfil());
                idPerfil= (int)usuarioPerfil.getIdPerfil();
                if (idPerfil==1)
                    nombreIndex="/index.xhtml";
                else
                    nombreIndex="/index1.xhtml";
                System.out.println(nombreIndex);
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                session.setAttribute("loginView", this);
                System.out.println("ruta " + context.getExternalContext().getRequestContextPath() );
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + nombreIndex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
            LOGGER.log(Level.INFO, "Login OK");
            userName = null;
            password = null;
            return;
        } else {
            userName = null;
            password = null;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Error", "Nombre de usuario o contraseña incorrectos"));
            PrimeFaces.current().ajax().update("form:messages", "frmLogin:basic");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
