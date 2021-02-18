package ec.gob.superbancos.srbi.persistence.service.impl;

import com.google.common.collect.Lists;
import ec.gob.superbancos.srbi.persistence.dao.IMenuDao;
import ec.gob.superbancos.srbi.persistence.model.Menu;
import ec.gob.superbancos.srbi.persistence.service.IMenuService;
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
public class MenuService extends AbstractService<Menu> implements IMenuService {

    @Autowired
    private IMenuDao dao;

    public MenuService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<Menu, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Page<Menu> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

}
