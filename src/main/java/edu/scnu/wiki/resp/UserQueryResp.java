package edu.scnu.wiki.resp;

import lombok.Data;

/**
 * @author long
 * @version 1.0
 * @ClassName UserQueryResp
 * @description: TODO
 * @date 2023/10/3 20:53
 */
@Data
public class UserQueryResp {
    private Long id;


    private String loginName;


    private String name;

    private String password;
}
