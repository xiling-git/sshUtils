package com.hxl.ssh;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SSHConfiguration {

    private boolean proxyEnabled = false;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private String charset;

}
