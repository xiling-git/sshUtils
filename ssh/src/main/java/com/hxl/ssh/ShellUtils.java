package com.hxl.ssh;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ShellUtils {

    private List<String> commends = new ArrayList<>();
    private Session session;
    private ChannelShell channel;
    private String charset;

    public ShellUtils(Session session, String charset) {
        this.session = session;
        this.charset = charset;
    }

    public ShellUtils commend(String commend) {
        commends.add(commend);
        return this;
    }

    public ChannelShell openChannel() {
        try {
            session.connect(3000);
            channel = (ChannelShell) session.openChannel("shell");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public void execute() {
        try {
            session.connect(3000);
            channel = (ChannelShell) session.openChannel("shell");

            channel.connect(1000);
            //获取输入流和输出流
            execute2();
        } catch (JSchException | InterruptedException | IOException e) {
            log.error("execute error", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

    }

    private void execute2() throws InterruptedException, IOException {

        InputStream inputStream = channel.getInputStream();
        OutputStream outputStream = channel.getOutputStream();

//        PipedInputStream pis = new PipedInputStream();
//        PipedOutputStream pos = new PipedOutputStream(pis);

        Iterator<String> iterator = commends.stream().iterator();

        while (true) {
            //等待服务器响应，如果发现命令没有执行完成，则需要加大等待时间
            Thread.sleep(1000L);

            String result = outResult(inputStream);
            boolean isRun = result != null;

            //命令未执行完成，等待响应
            if (!isRun) {
                continue;
            }

            log.info(result);

            //所有命令执行完，结束
            if (!iterator.hasNext()) {
                break;
            }

            if (iterator.hasNext()) {
                String commend = iterator.next();
                //发送需要执行的SHELL命令，需要用\n结尾，表示回车
                if (!commend.endsWith("\n")) {
                    commend += "\n";
                }
                outputStream.write(commend.getBytes());
                outputStream.flush();
            }

        }
    }

    private String outResult(InputStream inputStream) throws IOException {
        if (inputStream.available() > 0) {

            byte[] data = new byte[inputStream.available()];
            int nLen = inputStream.read(data);

            if (nLen < 0) {
                throw new IOException("network error.");
            }

            return new String(data, 0, nLen, charset);
        }
        return null;
    }

}
