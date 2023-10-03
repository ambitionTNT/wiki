package edu.scnu.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scnu.wiki.domain.User;
import edu.scnu.wiki.domain.UserExample;
import edu.scnu.wiki.mapper.UserMapper;
import edu.scnu.wiki.req.UserQueryReq;
import edu.scnu.wiki.req.UserSaveReq;
import edu.scnu.wiki.resp.PageResp;
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
            criteria.andNameLike("%" + req.getLoginName() + "%");
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
        if (!ObjectUtils.isEmpty(user.getId())){
            user.setId(snowFlake.nextId());
            return userMapper.insert(user);
        }else {
            // 更改
            return userMapper.updateByPrimaryKey(user);
        }

    }

    @Override
    public int delete(long id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
