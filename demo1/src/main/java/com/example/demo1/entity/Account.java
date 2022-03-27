package com.example.demo1.entity;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="account")
public class Account {
	
	@Id
	@Column(name="id")
    private Long id;
	
	@Column(name="email_address")
    private String emailAddress;
	
	@Column(name="password")
    private String password;
	
	@Column(name="name")
    private String name;
	
	@Column(name="display_name")
    private String displayName;
	
	@Column(name="mobile")
    private int mobile;
	
	@Column(name="is_active", columnDefinition="tinyint(1) default 1")
    private boolean isActive=true;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
    		  name = "accounts_roles", 
    		  joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")}, 
    		  inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();
	
    
    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Account() {
	}

	public Account(Long id, String name, String password) {
		this.id=id;
        this.name = name;
        this.password = password;
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayNmae(String displayName) {
        this.displayName = displayName;
    }
    
    public int getMobile() {
        return mobile;
    }
    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
    
    public boolean getIsActive() {
    	return isActive;
    }
    public void setIsActive(boolean isActive) {
    	this.isActive=isActive;
    }
    
    
    
    

}