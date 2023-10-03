package edu.scnu.wiki.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author long
 * @version 1.0
 * @ClassName UserQueryReq
 * @description: TODO
 * @date 2023/10/3 20:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryReq extends PageReq{
    private String loginName;
}
