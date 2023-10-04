package edu.scnu.wiki.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author long
 * @version 1.0
 * @ClassName UserSaveReq
 * @description: TODO
 * @date 2023/10/3 20:53
 */
@Data
public class UserSaveResetPasswordReq {
    private Long id;
    @NotNull(message = "【密码不能为空】")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含 数字和英文，长度6-32")
    private String password;
}
