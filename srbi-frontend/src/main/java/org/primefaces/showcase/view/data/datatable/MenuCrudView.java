package org.primefaces.showcase.view.data.datatable;

import ec.gob.superbancos.srbi.persistence.model.Menu;
import org.primefaces.PrimeFaces;
import org.primefaces.showcase.service.MenuService;

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
public class MenuCrudView implements Serializable {

    private List<Menu> menus;

    private Menu selectedMenu;

    private List<Menu> selectedMenus;

    @Inject
    private MenuService menuService;

    @PostConstruct
    public void init() {
        this.menus = this.menuService.getMenus();
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Menu getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(Menu selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public List<Menu> getSelectedMenus() {
        return selectedMenus;
    }

    public void setSelectedMenus(List<Menu> selectedMenus) {
        this.selectedMenus = selectedMenus;
    }

    public void openNew() {
        this.selectedMenu = new Menu();
    }

    public void save() {
        if (this.selectedMenu.getId() <= 0) {
            this.selectedMenu.setFechaCreacion(Calendar.getInstance().getTime());
            this.selectedMenu.setIdUsuarioCreacion(5);
            if(Objects.nonNull(this.selectedMenu.getIdMenuPadre()) && this.selectedMenu.getIdMenuPadre().longValue() == 0) {
                this.selectedMenu.setIdMenuPadre(null);
            }
            Menu menu = this.menuService.save(this.selectedMenu);
            if(Objects.nonNull(menu)) {
                this.menus.add(menu);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Menú Agregado"));
            }
        }
        else {
            if(Objects.nonNull(this.selectedMenu.getIdMenuPadre()) && this.selectedMenu.getIdMenuPadre().longValue() == 0) {
                this.selectedMenu.setIdMenuPadre(null);
            }
            this.selectedMenu.setFechaModificacion(Calendar.getInstance().getTime());
            try {
                this.menuService.save(this.selectedMenu);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Menú Actualizado"));
            } catch(BadRequestException badRequestException){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al actualizar",
                        "Menu no pudo ser actualizado!!!"));
            }
        }

        this.selectedMenu = null;
        PrimeFaces.current().executeScript("PF('manageMenuDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-menus");
    }

    public void delete() {
        this.menuService.delete(this.selectedMenu);
        this.menus.remove(this.selectedMenu);
        if(Objects.nonNull(this.selectedMenus))
            this.selectedMenus.remove(this.selectedMenu);
        this.selectedMenu = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Menú Removido"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-menus");
    }

    public void findAll() {
        this.menuService.findAll();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMenus()) {
            int size = this.selectedMenus.size();
            return size > 1 ? size + " Menus Seleccionados" : "1 Menu Seleccionado";
        }
        return "Borrar";
    }

    public boolean hasSelectedMenus() {
        return this.selectedMenus != null && !this.selectedMenus.isEmpty();
    }

    public void deleteSelectedMenus() {
        for (Menu menu: this.selectedMenus) {
            this.menuService.delete(menu);
        }
        this.menus.removeAll(this.selectedMenus);
        this.selectedMenus = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Menús removidos"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-menus");
        PrimeFaces.current().executeScript("PF('dtMenus').clearFilters()");
    }


}