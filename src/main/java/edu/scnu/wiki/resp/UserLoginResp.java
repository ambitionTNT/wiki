package edu.scnu.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @author long
 * @version 1.0
 * @ClassName UserLoginResp
 * @description: TODO
 * @date 2023/10/4 21:39
 */

@Data
public class UserLoginResp implements Serializable {

    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long id;

    private String token;
    private String loginName;


    private String name;

    private String password;
}
