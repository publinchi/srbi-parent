package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.TipoNotificacion;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoNotificacionDao extends JpaRepository<TipoNotificacion, Long> {

}