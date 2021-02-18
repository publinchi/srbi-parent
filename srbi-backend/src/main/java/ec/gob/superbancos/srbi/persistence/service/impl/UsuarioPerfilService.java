package ec.gob.superbancos.srbi.persistence.service.impl;

import com.google.common.collect.Lists;
import ec.gob.superbancos.srbi.persistence.dao.IUsuarioDao;
import ec.gob.superbancos.srbi.persistence.dao.IUsuarioPerfilDao;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import ec.gob.superbancos.srbi.persistence.model.UsuarioPerfil;
import ec.gob.superbancos.srbi.persistence.service.IUsuarioPerfilService;
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
public class UsuarioPerfilService extends AbstractService<UsuarioPerfil> implements IUsuarioPerfilService {

    @Autowired
    private IUsuarioPerfilDao dao;

    public UsuarioPerfilService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<UsuarioPerfil, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Page<UsuarioPerfil> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPerfil> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

}
