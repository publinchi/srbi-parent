package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilDao extends JpaRepository<Perfil, Long> {

}