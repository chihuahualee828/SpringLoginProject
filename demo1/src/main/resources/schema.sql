CREATE SEQUENCE IF NOT EXISTS IdSequence
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1;

CREATE TABLE IF NOT EXISTS account (
	id bigint DEFAULT IdSequence.nextval PRIMARY KEY,
--	id bigint NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL UNIQUE,
	display_name VARCHAR(255),
	password VARCHAR(255) NOT NULL,
	email_address VARCHAR(255),
	mobile INTEGER,
	is_active BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS roles (
	role_id bigint DEFAULT IdSequence.nextval PRIMARY KEY,
--	role_id bigint NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY(role_id)
);

CREATE TABLE IF NOT EXISTS accounts_roles (
	account_id bigint NOT NULL,
	role_id bigint NOT NULL,
	PRIMARY KEY(account_id, role_id),
	FOREIGN KEY (account_id) REFERENCES account(id),
	FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

