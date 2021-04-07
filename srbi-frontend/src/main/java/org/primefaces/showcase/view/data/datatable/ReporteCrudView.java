package org.primefaces.showcase.view.data.datatable;

import ec.gob.superbancos.srbi.persistence.model.*;
import org.primefaces.PrimeFaces;
import org.primefaces.showcase.service.MenuPerfilService;
import org.primefaces.showcase.service.ReporteMenuPerfilService;
import org.primefaces.showcase.service.ReporteService;
import org.primefaces.showcase.service.UsuarioPerfilService;
import org.primefaces.showcase.view.login.LoginView;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class ReporteCrudView implements Serializable {

    private List<Reporte> reportes;
    private Reporte selectedReporte;
    private List<Reporte> selectedReportes;
    private Usuario usuario;
    private UsuarioPerfil usuarioPerfil;
    private List<Reporte> asignedReportes;
    private MenuPerfil menuPerfil;
    private List<ReporteMenuPerfil> reporteMenuPerfiles;
    private boolean blnReporte;

    @Inject
    private ReporteService reporteService;
    @Inject
    private MenuPerfilService menuPerfilService;
    @Inject
    private ReporteMenuPerfilService reporteMenuPerfilService;
    @Inject
    private UsuarioPerfilService usuarioPerfilService;

    @PostConstruct
    public void init() {
        this.reportes = this.reporteService.getReportes();
        this.asignedReportes= new ArrayList<>();
        this.asignedReportes= findIdUsuarioPerfil();
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public Reporte getSelectedReporte() {
        return selectedReporte;
    }

    public void setSelectedReporte(Reporte selectedReporte) {
        this.selectedReporte = selectedReporte;
        blnReporte=true;
    }

    public void asignarReporte(Reporte selectedReporte) {
        System.out.println(" asignarReporte usuario " + selectedReporte.getId());
        this.selectedReporte = selectedReporte;
        blnReporte=true;
    }

    public List<Reporte> getSelectedReportes() {
        return selectedReportes;
    }

    public void setSelectedReportes(List<Reporte> selectedReportes) {
        this.selectedReportes = selectedReportes;
    }

    public void openNew() {
        this.selectedReporte = new Reporte();
    }

    public void save() {
        if (this.selectedReporte.getId() <= 0) {
            this.selectedReporte.setFechaCreacion(Calendar.getInstance().getTime());
            this.selectedReporte.setIdUsuarioCreacion(5);
            this.selectedReporte.setIdUsuarioModificacion(5);
            Reporte reporte = this.reporteService.save(this.selectedReporte);
            if(Objects.nonNull(reporte)) {
                this.reportes.add(reporte);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte Agregado"));
            }
        }
        else {
            this.selectedReporte.setFechaModificacion(Calendar.getInstance().getTime());
            this.reporteService.save(this.selectedReporte);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte Actualizado"));
        }
        this.selectedReporte=null;
        PrimeFaces.current().executeScript("PF('manageReporteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Reportes");
    }

    public void delete() {
        this.reporteService.delete(this.selectedReporte);
        this.reportes.remove(this.selectedReporte);
        if(Objects.nonNull(this.selectedReportes))
            this.selectedReportes.remove(this.selectedReporte);
        this.selectedReporte = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte Removido"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Reportes");
    }

    public void findAll() {
        this.reporteService.findAll();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedReportes()) {
            int size = this.selectedReportes.size();
            return size > 1 ? size + " Reportes Seleccionados" : "1 Reporte Seleccionado";
        }
        return "Borrar";
    }

    public boolean hasSelectedReportes() {
        return this.selectedReportes != null && !this.selectedReportes.isEmpty();
    }

    public void deleteSelectedReportes() {
        for (Reporte Reporte: this.selectedReportes) {
            this.reporteService.delete(Reporte);
        }
        this.reportes.removeAll(this.selectedReportes);
        this.selectedReportes = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reportes removidos"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Reportes");
        PrimeFaces.current().executeScript("PF('dtReportes').clearFilters()");
    }

    public  List<Reporte> findIdUsuarioPerfil()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        LoginView loginView=(LoginView) session.getAttribute("loginView");
        usuario=loginView.getUsuario();
        System.out.println(" findIdUsuarioPerfil usuario " + usuario.getId());
        usuarioPerfil= usuarioPerfilService.findByIdUsuario(usuario.getId());
        System.out.println(" findByIdUsuario usuario " + usuarioPerfil.getId());
        System.out.println(" findByIdUsuario usuario " + usuarioPerfil.getIdPerfil());
        menuPerfil=menuPerfilService.findByIdPerfil(usuarioPerfil.getIdPerfil());
        System.out.println(" findByIdPerfil menuperfil " + menuPerfil.getId());
        reporteMenuPerfiles= reporteMenuPerfilService.findByIdMenuPerfil(menuPerfil.getId());
        System.out.println(" findByIdPerfil findByIdMenuPerfil " + reporteMenuPerfiles.size());
        for(Reporte repo: reportes){
            System.out.println(" entro for : idemenuperfil " + menuPerfil.getId());
            System.out.println(" entro for " + repo.getId());
            for(ReporteMenuPerfil repoMenuPeril: reporteMenuPerfiles)
            {
                System.out.println(" entro segundo for " + repoMenuPeril.getId());
                if(repo.getId()==repoMenuPeril.getIdReporte())
                    asignedReportes.add(repo);
            }
        }
        System.out.println(" totalreportesasignados " + asignedReportes.size());
        //reportes=asignedReportes;
        return asignedReportes;
    }

    public List<Reporte> getAsignedReportes() {
        return asignedReportes;
    }

    public void setAsignedReportes(List<Reporte> asignedReportes) {
        this.asignedReportes = asignedReportes;
    }

    public boolean isBlnReporte() {
        return blnReporte;
    }

    public void setBlnReporte(boolean blnReporte) {
        this.blnReporte = blnReporte;
    }
}
