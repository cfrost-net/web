package net.cfrost.web.core.security.authentication.dao;

import java.util.List;

import net.cfrost.web.core.base.dao.hibernate5.IBaseDao;
import net.cfrost.web.core.security.authentication.entity.Authority;

public interface IAuthorityDao extends IBaseDao<Authority> {

    List<Authority> findAllRoles();
    
}
