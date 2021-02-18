package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.ReporteMenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReporteMenuPerfilService extends IOperations<ReporteMenuPerfil> {

    Page<ReporteMenuPerfil> findPaginated(Pageable pageable);

}
