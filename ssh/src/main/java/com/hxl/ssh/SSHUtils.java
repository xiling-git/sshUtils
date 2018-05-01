package com.hxl.ssh;

import com.hxl.server.Server;
import com.hxl.server.User;
import com.jcraft.jsch.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class SSHUtils {

    private SSHConfiguration configuration;
    private Server server;
    private String charset = "UTF-8";

    public SSHUtils(Server server) {
        this.server = server;
    }

    public ShellUtils shell() {
        Session session = createSession(server);
        return new ShellUtils(session, charset);
    }

    public SFTPUtils sftp() {
        Session session = createSession(server);
        return new SFTPUtils(session);
    }

    private Session createSession(Server server) {
        try {
            JSch jSch = new JSch();
            Session session;
            //使用非管理员用户登录
            List<User> users = server.getUsers();
            if (users == null || users.size() == 0) {
                return null;
            }

            List<User> userList = users.stream().filter(u -> !u.isAdministrator()).collect(Collectors.toList());
            if (userList == null || userList.isEmpty()) {
                return null;
            }

            User user = userList.get(0);
            session = jSch.getSession(user.getUsername(), server.getIp(), server.getSshPort());

            session.setConfig("StrictHostKeyChecking", "no");

            //配置代理
            if (configuration != null) {
                if (configuration.isProxyEnabled()) {
                    switch (configuration.getProxyType()) {
                        case HTTP:
                            //http proxy
                            ProxyHTTP proxyHTTP = new ProxyHTTP(configuration.getProxyHost(), configuration.getProxyPort());
                            proxyHTTP.setUserPasswd(configuration.getProxyUsername(), configuration.getProxyPassword());
                            session.setProxy(proxyHTTP);
                            break;
                        case Socks4:
                            ProxySOCKS4 proxySOCKS4 = new ProxySOCKS4(configuration.getProxyHost(), configuration.getProxyPort());
                            proxySOCKS4.setUserPasswd(configuration.getProxyUsername(), configuration.getProxyPassword());
                            session.setProxy(proxySOCKS4);
                            break;
                        case Socks5:
                            ProxySOCKS5 proxySOCKS5 = new ProxySOCKS5(configuration.getProxyHost(), configuration.getProxyPort());
                            proxySOCKS5.setUserPasswd(configuration.getProxyUsername(), configuration.getProxyPassword());
                            session.setProxy(proxySOCKS5);
                            break;
                    }
                }
                //设置字符集
                String charset = configuration.getCharset();
                if (charset != null && !charset.isEmpty()) {
                    this.charset = charset;
                }
            }

            return session;
        } catch (JSchException e) {
            log.error("can not connect server", e);
        }
        return null;
    }

}
