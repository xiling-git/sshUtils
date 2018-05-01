package com.hxl.server;

import com.hxl.basic.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器上的应用
 */
@Data
@NoArgsConstructor
public class Application extends AbstractEntity {

    //名称
    private String name;
    //应用用户
    private User appUser;
    //应用部署路径
    private String path;
    //应用类型
    private ApplicationType type;
    //应用端口
    private int port;
    //应用访问地址
    private String url;
    //应用管理用户
    private List<User> managerUsers = new ArrayList<>();
    //登陆用户
    private List<User> loginUsers = new ArrayList<>();
    //备注
    private String remark;

}
