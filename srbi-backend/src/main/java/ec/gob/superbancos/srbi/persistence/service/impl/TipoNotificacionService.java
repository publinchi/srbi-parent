package ec.gob.superbancos.srbi.persistence.service.impl;

import com.google.common.collect.Lists;
import ec.gob.superbancos.srbi.persistence.dao.ITipoNotificacionDao;
import ec.gob.superbancos.srbi.persistence.model.TipoNotificacion;
import ec.gob.superbancos.srbi.persistence.service.ITipoNotificacionService;
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
public class TipoNotificacionService extends AbstractService<TipoNotificacion> implements ITipoNotificacionService {

    @Autowired
    private ITipoNotificacionDao dao;

    public TipoNotificacionService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<TipoNotificacion, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Page<TipoNotificacion> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<TipoNotificacion> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

}
