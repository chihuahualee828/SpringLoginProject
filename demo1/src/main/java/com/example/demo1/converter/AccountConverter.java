package com.example.demo1.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo1.entity.Account;
import com.example.demo1.entity.AccountRequest;
import com.example.demo1.entity.AccountResponse;
import com.example.demo1.entity.Role;

public class AccountConverter {
	private AccountConverter() {

    }

    public static Account toAccount(AccountRequest request) {
        Account user = new Account();
        user.setId(request.getId());
        user.setEmailAddress(request.getEmailAddress());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        
//        user.setDisplayNmae(request.getDisplayName());
//        user.setMobile(request.getMobile());
//        user.setIsActive(request.getIsActive());
        return user;
    }

    public static AccountRequest toAccountRequest(Account user) {
        AccountRequest request = new AccountRequest();
        request.setId(user.getId());
        request.setEmailAddress(user.getEmailAddress());
        request.setName(user.getName());
//        response.setDisplayName(user.getDisplayName());
//        response.setMobile(user.getMobile());
        request.setPassword(user.getPassword());
//        response.setIsActive(user.getIsActive());

        return request;
    }
    
    public static AccountResponse toAccountResponse(Account user) {
        AccountResponse response = new AccountResponse();
        response.setId(user.getId());
        response.setEmailAddress(user.getEmailAddress());
        response.setName(user.getName());
//        response.setDisplayName(user.getDisplayName());
//        response.setMobile(user.getMobile());
        response.setPassword(user.getPassword());
//        response.setIsActive(user.getIsActive());

        return response;
    }

    public static List<AccountResponse> toAccountResponses(List<Account> users) {
        return users.stream()
                .map(AccountConverter::toAccountResponse)
                .collect(Collectors.toList());
    }
}
