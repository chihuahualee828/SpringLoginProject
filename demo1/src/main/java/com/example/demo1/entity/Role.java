package com.example.demo1.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="roles")
public class Role {
	
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	@Column(name="Name")
    private String name;
	
//	@Column(name="canView", columnDefinition="tinyint(1) default 0")
//    private boolean canView;
//	
//	@Column(name="canEdit", columnDefinition="tinyint(1) default 0")
//    private boolean canEdit;
//	
	
    //@Transient : for front end extra parameter that's not in entity
    public Role() {
	}

	public Role(Long id, String name) {
		this.id=id;
        this.name = name;
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

//    
//    public boolean isCanView() {
//		return canView;
//	}
//
//	public void setCanView(boolean canView) {
//		this.canView = canView;
//	}
//
//	public boolean isCanEdit() {
//		return canEdit;
//	}
//
//	public void setCanEdit(boolean canEdit) {
//		this.canEdit = canEdit;
//	}





//
//	@ManyToMany(mappedBy = "isRole")
//    Set<Account> accounts;
    
}
