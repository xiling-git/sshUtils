package com.hxl.server;

import com.hxl.basic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    //用户名
    private String username;
    //密码
    private String password;
    //是否管理员
    private boolean isAdministrator = false;
    //备注
    private String remark;

}
