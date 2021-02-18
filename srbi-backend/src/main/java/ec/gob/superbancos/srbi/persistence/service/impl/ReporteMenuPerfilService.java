package ec.gob.superbancos.srbi.persistence.service.impl;

import com.google.common.collect.Lists;
import ec.gob.superbancos.srbi.persistence.dao.IReporteMenuPerfilDao;
import ec.gob.superbancos.srbi.persistence.model.ReporteMenuPerfil;
import ec.gob.superbancos.srbi.persistence.service.IReporteMenuPerfilService;
import ec.gob.superbancos.srbi.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReporteMenuPerfilService extends AbstractService<ReporteMenuPerfil> implements IReporteMenuPerfilService {

    @Autowired
    private IReporteMenuPerfilDao dao;

    public ReporteMenuPerfilService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<ReporteMenuPerfil, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Page<ReporteMenuPerfil> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<ReporteMenuPerfil> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

}
