package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.MenuPerfil;
import ec.gob.superbancos.srbi.persistence.model.ReporteMenuPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReporteMenuPerfilDao extends JpaRepository<ReporteMenuPerfil, Long> {

    @Query("select rmp from reporte_menu_perfil rmp where rmp = :idMenuPerfil")
    List<ReporteMenuPerfil> findByIdMenuPerfil(@Param("idMenuPerfil") Long idMenuPerfil);

}
