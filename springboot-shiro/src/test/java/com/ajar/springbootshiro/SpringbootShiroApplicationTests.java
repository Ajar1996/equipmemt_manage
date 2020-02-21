package com.ajar.springbootshiro;

import com.ajar.springbootshiro.dao.RoleDao;
import com.ajar.springbootshiro.dao.UserDao;
import com.ajar.springbootshiro.dao.UserRoleDao;
import com.ajar.springbootshiro.model.Role;
import com.ajar.springbootshiro.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootShiroApplicationTests {

    @Autowired
    UserRoleDao userRoleDao;


    @Test
    public void contextLoads() {
/*        System.out.println("aaaaa");
        userRoleDao.deleteByUserId(1);
        System.out.println("bbbbb");*/
    }

}
