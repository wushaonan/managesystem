package com.sn.manageservice.repostory;

import com.sn.manageservice.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 向南
 * @date 2021/12/26 17:58
 * @description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //自定义repository。手写sql
    @Query(value = "update user set name=?1 where id=?4",nativeQuery = true)   //占位符传值形式
    @Modifying
    int updateById(String name,int id);

    @Query("from User u where u.userName=:userName")   //SPEL表达式
    User findUser(@Param("userName") String userName);// 参数username 映射到数据库字段username
}
