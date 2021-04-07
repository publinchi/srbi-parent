package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMenuPerfilDao extends JpaRepository<MenuPerfil, Long> {

    @Query("select u from srbi_menu_perfil u where u.idMenu=13 and u.idPerfil = :idPerfil")
    MenuPerfil findByIdPerfil(@Param("idPerfil") Long idPerfil);

}
