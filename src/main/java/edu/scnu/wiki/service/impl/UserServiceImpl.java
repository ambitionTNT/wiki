package edu.scnu.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scnu.wiki.domain.User;
import edu.scnu.wiki.domain.UserExample;
import edu.scnu.wiki.exception.BusinessException;
import edu.scnu.wiki.exception.BusinessExceptionCode;
import edu.scnu.wiki.mapper.UserMapper;
import edu.scnu.wiki.req.UserLoginReq;
import edu.scnu.wiki.req.UserQueryReq;
import edu.scnu.wiki.req.UserSaveReq;
import edu.scnu.wiki.req.UserSaveResetPasswordReq;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.resp.UserLoginResp;
import edu.scnu.wiki.resp.UserQueryResp;
import edu.scnu.wiki.service.UserService;
import edu.scnu.wiki.utils.CopyUtil;
import edu.scnu.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName UserServiceImpl
 * @description: TODO
 * @date 2023/10/3 21:01
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;
    @Override
    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameLike("%" + req.getLoginName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        PageResp<UserQueryResp> userQueryRespPageResp = new PageResp<>();

        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);

        List<UserQueryResp> userQueryResps = CopyUtil.copyList(userList, UserQueryResp.class);

        userQueryRespPageResp.setList(userQueryResps);
        userQueryRespPageResp.setTotal(userPageInfo.getTotal());
        return userQueryRespPageResp;

    }

    @Override
    public List<UserQueryResp> all(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        List<User> userList = userMapper.selectByExample(userExample);
        List<UserQueryResp> userQueryResps = CopyUtil.copyList(userList, UserQueryResp.class);
        return userQueryResps;
    }

    @Override
    public int save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(user.getId())){
            if(ObjectUtils.isEmpty(selectByLoginName(user.getLoginName()))){
                user.setId(snowFlake.nextId());
                return userMapper.insert(user);
            }else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }






        }else {
            // 更改
            user.setLoginName(null);
            user.setPassword(null);
            return userMapper.updateByPrimaryKeySelective(user);
        }

    }

    private User selectByLoginName(String loginName){

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size()==0){
            return null;
        }else {
            return userList.get(0);
        }
    }

    @Override
    public int delete(long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int resetPassword(UserSaveResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserLoginResp login(UserLoginReq req) {

        User userDB = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDB)){
            //用户不存在，返回用户不存在异常
            log.info("用户名不存在, {}", req.getLoginName(), userDB.getPassword());
            throw new BusinessException(BusinessExceptionCode.USER_NAME_NOT_EXIST);
        }else {
            if (userDB.getPassword().equals(req.getPassword())){
                UserLoginResp UserLoginResp = CopyUtil.copy(userDB, UserLoginResp.class);
                return UserLoginResp;
            }else {
                //密码错误
                log.info("密码错误, {}", req.getLoginName(), userDB.getPassword());
                throw new BusinessException(BusinessExceptionCode.USER_NAME_NOT_EXIST);
            }
        }

    }
}
