package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificacionDao extends JpaRepository<Notificacion, Long> {

}