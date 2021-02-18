package ec.gob.superbancos.srbi.persistence.dao;

import ec.gob.superbancos.srbi.persistence.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReporteDao extends JpaRepository<Reporte, Long> {

}