package com.hxl.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SFTPUtils {

    private Session session;

    public SFTPUtils(Session session) {
        this.session = session;
    }

    public void get(String src, String dst) {
        ((Executor) channelSftp -> channelSftp.get(src, dst)).execute(session);
    }

    public void put(String src, String dst, int mode) {
        ((Executor) channelSftp -> channelSftp.put(src, dst, mode)).execute(session);
    }

    private interface Executor {
        void callback(ChannelSftp channelSftp) throws SftpException;

        default void execute(Session session) {
            ChannelSftp channel = null;
            try {
                session.connect(3000);
                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();
                callback(channel);
            } catch (JSchException | SftpException e) {
                log.error("ChannelSftp error: ", e);
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
                if (session != null) {
                    session.disconnect();
                }
            }
        }
    }

}
