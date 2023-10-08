package edu.scnu.wiki.service;

import edu.scnu.wiki.req.*;
import edu.scnu.wiki.resp.EbookQueryResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.resp.UserLoginResp;
import edu.scnu.wiki.resp.UserQueryResp;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName UserService
 * @description: TODO
 * @date 2023/10/3 20:52
 */

public interface UserService {
    PageResp<UserQueryResp> list(UserQueryReq req);
    List<UserQueryResp> all(UserQueryReq req);

    int save(UserSaveReq req);

    int delete(long id);
    int resetPassword(UserSaveResetPasswordReq req);

    UserLoginResp login(UserLoginReq req);
}
