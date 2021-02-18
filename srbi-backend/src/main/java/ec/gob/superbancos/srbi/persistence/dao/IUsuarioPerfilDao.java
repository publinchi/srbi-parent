package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioPerfilDao extends JpaRepository<UsuarioPerfil, Long> {

}