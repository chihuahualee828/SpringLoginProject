package com.example.demo1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="systemmail")
public class SystemMail {
	
	@Id
	@Column(name="id")
    private Long id;
	
	
	@Column(name="Email")
    private String email;
	
	
	@Column(name="Password")
    private String password;
	
//	@Column(name="canView", columnDefinition="tinyint(1) default 0")
//    private boolean canView;
//	
//	@Column(name="canEdit", columnDefinition="tinyint(1) default 0")
//    private boolean canEdit;
//	
	
    //@Transient : for front end extra parameter that's not in entity
    public SystemMail() {
	}

	public SystemMail(Long id, String email, String password) {
		this.id=id;
		this.email=email;
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
    


}
