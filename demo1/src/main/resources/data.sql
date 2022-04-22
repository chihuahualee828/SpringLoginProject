MERGE INTO account(name, password, is_active, mobile) KEY(name) VALUES ('ada', 
'$2a$10$XScjdSCyyU9xnyJCxCSPReasj/z0K7xv8aipy7hNoUoTZddndWO8e', true, 0);
 
MERGE INTO roles (name) KEY(name) VALUES ('USER');
MERGE INTO roles (name) KEY(name) VALUES ('MANAGER');
MERGE INTO roles (name) KEY(name) VALUES ('ADMIN');

--MERGE INTO accounts_roles (account_id, role_id) KEY(account_id,role_id) VALUES (1, 3);
MERGE INTO accounts_roles (account_id, role_id) KEY(account_id,role_id) 
	VALUES(SELECT id from account WHERE name='ada',
	SELECT role_id from roles WHERE name='ADMIN');