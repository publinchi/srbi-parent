package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPerfilService extends IOperations<Perfil> {

    Page<Perfil> findPaginated(Pageable pageable);

}
