package ec.gob.superbancos.srbi.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.gob.superbancos.srbi.persistence.deser.MultiDateDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "srbi_perfil")
public class Perfil implements Serializable {

    @Id
    @Column(name = "id_perfil")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long estado;
    private String nombre;
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
    private String descripcion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        Perfil other = (Perfil) obj;
        if (id == 0) {
            return other.id == 0;
        } else return id == other.id;
    }

}
