package com.example.demo1.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class AccountRole {
	@Entity
	class Account {

	    @Id
	    Long id;

	    @ManyToMany
	    Set<Role> isRoles;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Set<Role> getIsRoles() {
			return isRoles;
		}

		public void setIsRoles(Set<Role> isRoles) {
			this.isRoles = isRoles;
		}
	    
	    
	    
	}

	@Entity
	class Course {

	    @Id
	    Long id;

	    @ManyToMany
	    Set<Account> isAccounts;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Set<Account> getIsAccounts() {
			return isAccounts;
		}

		public void setIsAccounts(Set<Account> isAccounts) {
			this.isAccounts = isAccounts;
		}
	    
	    
	    // additional properties
	    // standard constructors, getters, and setters
	}
}
