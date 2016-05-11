package net.cfrost.web.core.security.authentication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import net.cfrost.web.core.base.entity.IDEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="SYS_AUTHORITY")
public class Authority extends IDEntity<Authority> implements GrantedAuthority {
   
    public final static String ANONYMOUS = "ANONYMOUS";

    @Column(name="AUTHORITY",nullable=false,unique=true)
    private String authority;
    @Column(name="DESCRIPTION")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }    
}
