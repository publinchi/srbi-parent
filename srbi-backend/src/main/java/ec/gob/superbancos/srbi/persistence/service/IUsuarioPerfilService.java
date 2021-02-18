package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioPerfilService extends IOperations<UsuarioPerfil> {

    Page<UsuarioPerfil> findPaginated(Pageable pageable);

}
