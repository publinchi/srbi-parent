package ec.gob.superbancos.srbi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "srbi_menu_perfil")
public class MenuPerfil implements Serializable {

    @Id
    @Column(name = "id_menu_perfil")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_menu")
    private long idMenu;
    @Column(name = "id_perfil")
    private long idPerfil;
    private long estado;
    @Column(name = "id_usuario_creacion")
    private long idUsuarioCreacion;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "id_usuario_modificacion")
    private long idUsuarioModificacion;
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public long getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(long idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(long idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
