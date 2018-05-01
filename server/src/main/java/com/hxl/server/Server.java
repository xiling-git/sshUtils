package com.hxl.server;

import com.hxl.basic.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器
 */
@Data
@NoArgsConstructor
public class Server extends AbstractEntity {

    //服务器IP
    private String ip;
    //服务器名称
    private String name;
    //操作系统
    private String os;
    //SSH端口
    private int sshPort = 22;

    //用户
    private List<User> users = new ArrayList<>();
    //应用
    private List<Application> applications = new ArrayList<>();

    //cpu
    private CPU cpu;
    //内存
    private Memory memory;
    //硬盘
    private HardDisk hardDisk;

    //备注
    private String remark;

}
