package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuPerfilDao extends JpaRepository<MenuPerfil, Long> {

}