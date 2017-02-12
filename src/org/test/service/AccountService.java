/**
 *    Copyright 2010-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.test.data.Account;
import org.test.mapper.AccountMapper;

@Service
public class AccountService {

	@Autowired
	private AccountMapper accountMapper;

	@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ, readOnly = true)
	public Account getAccount(String username) {
		return accountMapper.getAccountByUsername(username);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor=Exception.class)
	public void insertAccount(Account account) {
		accountMapper.insertAccount(account);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void updateAccount(Account account) {
		accountMapper.updateAccount(account);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public void deleteAccount(String username) {
		accountMapper.deleteAccount(username);
	}
	
}
