package ec.gob.superbancos.srbi.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.gob.superbancos.srbi.persistence.deser.MultiDateDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "srbi_notificacion")
public class Notificacion implements Serializable {

    @Id
    @Column(name = "id_notificacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_tipo_notificacion")
    private long idTipoNotificacion;
    @Column(name = "id_usuario")
    private long idUsuario;
    @Column(name = "fecha_envio")
    private Date fechaEnvio;
    private long estado;
    @Column(name = "id_usuario_creacion")
    private long idUsuarioCreacion;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "id_usuario_modificacion")
    private long idUsuarioModificacion;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    @Column(name = "tipo_notificacion_id_tipo_notificacion")
    private String tipoNotificacionIdTipoNotificacion;
    @Column(name = "usuario_id_usuario")
    private String usuarioIdUsuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdTipoNotificacion() {
        return idTipoNotificacion;
    }

    public void setIdTipoNotificacion(long idTipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
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

    public String getTipoNotificacionIdTipoNotificacion() {
        return tipoNotificacionIdTipoNotificacion;
    }

    public void setTipoNotificacionIdTipoNotificacion(String tipoNotificacionIdTipoNotificacion) {
        this.tipoNotificacionIdTipoNotificacion = tipoNotificacionIdTipoNotificacion;
    }

    public String getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(String usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : Long.valueOf(id).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Notificacion other = (Notificacion) obj;
        if (id == 0) {
            return other.id == 0;
        } else return id == other.id;
    }


}
