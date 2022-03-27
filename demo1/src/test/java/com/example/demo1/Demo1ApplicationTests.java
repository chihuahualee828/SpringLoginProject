package com.example.demo1;

import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

//import com.example.demo1.service.AccountService;
import com.example.demo1.service.Demo1Service;
import com.example.demo1.converter.AccountConverter;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.Role;
import com.example.demo1.repository.AccountRepository;


@Slf4j
@SpringBootTest
class Demo1ApplicationTests {

	@Autowired
    private AccountRepository accountRepository;

	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Demo1Service demo1Service;
	
    @Test
    public void test() throws Exception {
    	
    	
//    	this.passwordEncoder = new BCryptPasswordEncoder();
//        //table is cleared when test started
//    	Account account1=new Account();
//    	Role role = new Role();
//    	Set<Role> roles = new HashSet<>();
//    	role.setId(Long.valueOf(2));
//    	role.setName("MANAGER");
//    	roles.add(role);
////    	demo1Service.createAccount();
//    	account1.setId(Long.valueOf(8345323));
//    	account1.setName("BB");
//    	account1.setPassword(passwordEncoder.encode("123"));
//    	account1.setRoles(roles);
//    	accountRepository.save(account1);

    	accountRepository.updateActive(false, Long.valueOf(2223));
    	//System.out.println(accountRepository.findById(Long.valueOf(2222)).get().getName());
    	
        // 測試findAll, 查詢所有記錄
//        Assertions.assertEquals(2, accountRepository.findAll().size());

        // 測試findByName, 查詢姓名為FFF的User
        //Assertions.assertEquals(1, accountRepository.findByName("A"));

        // 測試刪除姓名為AAA的User
        //accountRepository.delete(accountRepository.findById(Long.valueOf(1111)));

        // 測試findAll, 查詢所有記錄, 驗證上面的刪除是否成功
        //Assertions.assertEquals(9, accountRepository.findAll().size());

    }

}
