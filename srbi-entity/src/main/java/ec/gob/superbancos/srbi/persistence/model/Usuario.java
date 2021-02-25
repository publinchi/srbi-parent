package ec.gob.superbancos.srbi.persistence.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.gob.superbancos.srbi.persistence.deser.MultiDateDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "srbi_usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    @Column(name = "id_usuario_creacion")
    private long idUsuarioCreacion;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;
    private long intento;
    private String ip;
    private String contrasenia;
    @Column(name = "id_usuario_modificacion")
    private long idUsuarioModificacion;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    private long estado;
    @Column(name = "id_departamento")
    private long idDepartamento;
    @Column(name = "correo_empresarial")
    private String correoEmpresarial;

    public Usuario() {

    }

    public Usuario(long id, String login, long idUsuarioCreacion, Date fechaModificacion, long intento, String ip, String contrasenia, long idUsuarioModificacion, Date fechaCreacion, long estado, long idDepartamento, String correoEmpresarial) {
        this.id = id;
        this.login = login;
        this.idUsuarioCreacion = idUsuarioCreacion;
        this.fechaModificacion = fechaModificacion;
        this.intento = intento;
        this.ip = ip;
        this.contrasenia = contrasenia;
        this.idUsuarioModificacion = idUsuarioModificacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.idDepartamento = idDepartamento;
        this.correoEmpresarial = correoEmpresarial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(long idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public long getIntento() {
        return intento;
    }

    public void setIntento(long intento) {
        this.intento = intento;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public long getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(long idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    public long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getCorreoEmpresarial() {
        return correoEmpresarial;
    }

    public void setCorreoEmpresarial(String correoEmpresarial) {
        this.correoEmpresarial = correoEmpresarial;
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
        Usuario other = (Usuario) obj;
        if (id == 0) {
            return other.id == 0;
        } else return id == other.id;
    }
}
