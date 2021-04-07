package ec.gob.superbancos.srbi.persistence.service.impl;

import com.google.common.collect.Lists;
import com.sun.jndi.ldap.LdapCtxFactory;
import ec.gob.superbancos.srbi.persistence.dao.IUsuarioDao;
import ec.gob.superbancos.srbi.persistence.model.Usuario;
import ec.gob.superbancos.srbi.persistence.service.IUsuarioService;
import ec.gob.superbancos.srbi.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.List;

@Service
@Transactional
public class UsuarioService extends AbstractService<Usuario> implements IUsuarioService {

    @Autowired
    private IUsuarioDao dao;

    public UsuarioService() {
        super();
    }

    // API

    @Override
    protected PagingAndSortingRepository<Usuario, Long> getDao() {
        return dao;
    }

    // custom methods

    @Override
    public Page<Usuario> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Usuario findByLogin(String login) {
        return dao.findByLogin(login);
    }

    // overridden to be secured

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Usuario validarLDAP(String usuario, String contrasenia)
    {
        System.out.println("entro a validar");
        Usuario usuario1= new Usuario();
        String domainName;
        String serverName;
        domainName="SIBCOS.GOB";
        Hashtable props = new Hashtable();
        //String principalName = usuario.getLogin() + "@" + domainName;
        String principalName = usuario + "@" + domainName;
        System.out.println(principalName);
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, "LDAP://" + domainName);
        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        //props.put(Context.SECURITY_CREDENTIALS, usuario.getContrasenia());
        props.put(Context.SECURITY_CREDENTIALS, contrasenia);
        DirContext context;
        System.out.println("Antes de try");
        try {
            //context = LdapCtxFactory.getLdapCtxInstance("ldap://" + serverName + "." + domainName + '/', props);
            context = new InitialDirContext(props);
            System.out.println("Authentication succeeded!");
            usuario1 = dao.findByLogin(usuario);
            System.out.println("id" + usuario1.getId());
        }
        catch (AuthenticationException a) {
            System.out.println("Authentication failed: " + a);
        } catch (NamingException e) {
            System.out.println("Failed to bind to LDAP / get account information: " + e);
        }
        return usuario1;
    }
}
