package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.Reporte;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReporteService extends IOperations<Reporte> {

    Page<Reporte> findPaginated(Pageable pageable);

}
