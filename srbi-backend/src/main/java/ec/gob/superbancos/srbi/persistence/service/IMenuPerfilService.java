package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMenuPerfilService extends IOperations<MenuPerfil> {

    Page<MenuPerfil> findPaginated(Pageable pageable);

}
