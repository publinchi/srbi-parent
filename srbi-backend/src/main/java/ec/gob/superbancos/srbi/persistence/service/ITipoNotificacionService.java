package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.TipoNotificacion;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoNotificacionService extends IOperations<TipoNotificacion> {

    Page<TipoNotificacion> findPaginated(Pageable pageable);

}
