package com.sn.manageservice.api;

import com.sn.manageservice.api.param.LoginParam;
import com.sn.manageservice.controller.vo.Response;
import com.sn.manageservice.pojo.User;
import com.sn.manageservice.repostory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author 向南
 * @date 2023/6/1 11:47
 * @description
 */
@RestController
@RequestMapping("/rest/manage")
@Slf4j
public class RestManageController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login")
    public Response appLogin(@RequestBody @Validated  LoginParam loginParam) {
        User userInfo = userRepository.findUser(loginParam.getUserName());
        if (userInfo == null || !Objects.equals(loginParam.getPassWord(), userInfo.getPassword())) {
            return Response.fail("账号或者密码有误", null);
        }
        return Response.ok(null);
    }
}
