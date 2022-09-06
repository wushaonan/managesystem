package com.sn.manageservice.repostory;

import com.sn.manageservice.pojo.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 向南
 * @date 2021/12/28 11:14
 * @description
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAccountByAccount(String account);

    Page<Account> findAll(Pageable pageable);
}
