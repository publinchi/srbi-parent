package org.primefaces.showcase.view.data.datatable;

import ec.gob.superbancos.srbi.persistence.model.Menu;
import org.primefaces.PrimeFaces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.showcase.service.MenuService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class MenuCrudView implements Serializable {

    private List<Menu> menus;

    private Menu selectedMenu;

    private List<Menu> selectedMenus;

    private MenuModel model;

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
            /*if(Objects.nonNull(this.selectedMenu.getIdMenuPadre()) && this.selectedMenu.getIdMenuPadre().longValue() == 0) {
                this.selectedMenu.setIdMenuPadre(null);
            }*/
            this.selectedMenu.setIdUsuarioModificacion(5);
            Menu menu = this.menuService.save(this.selectedMenu);
            if(Objects.nonNull(menu)) {
                this.menus.add(menu);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Menú Agregado"));
            }
        }
        else {
            /*if(Objects.nonNull(this.selectedMenu.getIdMenuPadre()) && this.selectedMenu.getIdMenuPadre().longValue() == 0) {
                this.selectedMenu.setIdMenuPadre(null);
            }*/
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

    /*
    public void elaborarMenu()
    {
        List<DefaultMenuItem> items= new ArrayList<>();
        for (Menu m: this.menus)
        {
            if (m.getNivel()==1)
            {
                DefaultSubMenu menuRaiz= new DefaultSubMenu();
                menuRaiz.setLabel(m.getNombre());
                for (Menu mi: this.menus)
                {
                    Menu subMenu;
                    subMenu = mi.getSubmenu();
                    if (subMenu!=null)
                    {
                        if(subMenu.getId()==m.getId())
                        {
                            DefaultMenuItem item= new DefaultMenuItem();
                            item.setValue(subMenu.getNombre());
                            //menuRaiz.addElement(item);
                            menuRaiz.getElements().add(item);
                        }
                    }
                }
                getModel().addElement(menuRaiz);
                //model.getElements().add(menuRaiz);
            }
            else
            {
                DefaultMenuItem item= new DefaultMenuItem();
                item.setValue(m.getNombre());
                getModel().addElement(item);
            }

        }

    }
*/

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
}
