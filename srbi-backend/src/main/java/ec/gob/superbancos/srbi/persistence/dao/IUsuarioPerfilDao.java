package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUsuarioPerfilDao extends JpaRepository<UsuarioPerfil, Long> {

    @Query("select up from srbi_usuario_perfil up where up.idUsuario = :idUsuario")
    UsuarioPerfil findByIdUsu(@Param("idUsuario") Long idUsuario);

}
