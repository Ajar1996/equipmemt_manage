package com.ajar.springbootshiro.controller;

import com.ajar.springbootshiro.dao.UserDao;
import com.ajar.springbootshiro.model.User;
import com.ajar.springbootshiro.service.UserService;
import com.ajar.springbootshiro.utils.RUtil;
import com.ajar.springbootshiro.utils.ShiroUtil;
import com.ajar.springbootshiro.vo.Result;
import com.ajar.springbootshiro.vo.SessionVo;
import com.ajar.springbootshiro.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: Ajar
 * @time: 2019/10/21 11:29
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class SessionController {

    @Autowired
    SessionDAO sessionDAO;

    @Autowired
    UserService userService;

    @RequiresPermissions("sys:online")
    @GetMapping("/selectSessions")
    public Result selectSessions(){
        Collection<Session> sessions=sessionDAO.getActiveSessions();
        return RUtil.success(sessions);
    }

    @GetMapping("/selectUserEntity")
    public Result selectUserEntity(){
        SessionVo sessionVo=new SessionVo();
        sessionVo.setSession(ShiroUtil.getSession());
        sessionVo.setUserVo((UserVo) userService.selectUserDetail(ShiroUtil.getUserId()).getData());

        return RUtil.success(sessionVo);
    }


    @RequiresPermissions("sys:online:delete")
    @DeleteMapping("/logOutUser/{sessionid}")
    public Result logOutUser(@PathVariable String sessionid){
        Session session = sessionDAO.readSession(sessionid);
        if(session != null) {
            session.setTimeout(0);
        }
        return RUtil.success();
    }

}
