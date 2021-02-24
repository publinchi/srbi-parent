package ec.gob.superbancos.srbi.persistence.service;

import ec.gob.superbancos.srbi.persistence.IOperations;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService extends IOperations<Usuario> {

    Page<Usuario> findPaginated(Pageable pageable);

    Usuario findByLogin(final String login);

}
