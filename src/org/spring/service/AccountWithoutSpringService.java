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
package org.spring.service;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.spring.data.Account;
import org.spring.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountWithoutSpringService {

    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;

    private AccountMapper accountMapper;
    private SqlSession session = null;

    public void setupSession() throws Exception {
        if (session == null) {
            session = sqlSessionFactory.getObject().openSession(); // This
                                                                   // obtains a
                                                                   // database
        }

        if (accountMapper == null) {
            // connection!
            accountMapper = session.getMapper(AccountMapper.class);

        }
    }

    public Account getAccount(String username) throws Exception {
        setupSession();
        return accountMapper.getAccountByUsername(username);
      }
    
    public void insertAccount(Account account) throws Exception {
        setupSession();
        
        try{
            accountMapper.insertAccount(account);
            session.commit(); // This commits the data to the database. Required
            
        }catch(Exception e){
            session.rollback();
            throw e;    
        }
      }

    public void updateAccount(Account account) throws Exception {
        setupSession();
        
        try{
            accountMapper.updateAccount(account);
            session.commit(); // This commits the data to the database. Required
            
        }catch(Exception e){
            session.rollback();
            throw e;    
        }
      }
}
