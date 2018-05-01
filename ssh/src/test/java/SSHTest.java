import com.hxl.server.Server;
import com.hxl.server.User;
import com.hxl.ssh.SSHConfiguration;
import com.hxl.ssh.SSHUtils;
import com.hxl.ssh.ShellUtils;
import com.jcraft.jsch.ChannelShell;

public class SSHTest {

    public static void main(String[] args) {

        Server server = new Server();
        server.setIp("10.252.109.20");
        server.setSshPort(22);
        server.getUsers().add(new User("root", "Zst.1258)20", true, ""));
        server.getUsers().add(new User("gd12580", "Gd12580!@%*)20", false, ""));
        server.setOs("Linux");
        SSHUtils sshUtils = new SSHUtils(server);

        SSHConfiguration configuration = new SSHConfiguration();
        configuration.setCharset("UTF-8");
        configuration.setProxyEnabled(true);
        configuration.setProxyHost("10.252.109.41");
        configuration.setProxyPort(8081);
        sshUtils.setConfiguration(configuration);

//        System.out.println(server);

//        SSHUtils.createSession(server).commend("su - root").commend("123456").commend("ls").execute();
//        SSHUtils.createSession(server).put("test.txt", "/home/tomcat/test.html", ChannelSftp.OVERWRITE);
//        SSHUtils.createSession(server).commend("ls").execute();
//        SSHUtils.createSession(server).sftp().get("/home/tomcat/test.html", "test.txt");
//        SSHUtils.createSession(server).sftp().put("tomcat/86c221be-6ab2-ef53-1589-fe16877914f8.pl", "/tmp/86c221be-6ab2-ef53-1589-fe16877914f8.pl", ChannelSftp.OVERWRITE);
//        SSHUtils.createSession(server).sftp().put("tomcat/86c221be-6ab2-ef53-1589-fe16877914f8.sh", "/tmp/86c221be-6ab2-ef53-1589-fe16877914f8.sh", ChannelSftp.OVERWRITE);
//        sshUtils.shell()
//                .commend("sh '/tmp/86c221be-6ab2-ef53-1589-fe16877914f8.sh' '10.252.109.20' 'gd12580' 'Gd12580!@%*)20' '/opt/12580Service/apache-tomcat-8.5.24_8081/conf'").execute();
//        sshUtils.sftp().get("/tmp/10.252.109.20_86c221be-6ab2-ef53-1589-fe16877914f8_chk.xml", "10.252.109.20_86c221be-6ab2-ef53-1589-fe16877914f8_chk.xml");

//        SSHUtils.createSession(server).shell().shell("ls").execute();

        User root = null;
        for (User user : server.getUsers()) {
            if ("root".equals(user.getUsername())) {
                root = user;
                break;
            }
        }

        ShellUtils shell = sshUtils.shell();
        shell.openChannel();
        shell.init();
        shell.execute("su - root");
        shell.execute(root.getPassword());
        String memory = shell.execute("cat /proc/meminfo|grep MemTotal");
        String cpu = shell.execute("cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c");
        String cpu2 = shell.execute("cat /proc/cpuinfo | grep physical | uniq -c");
        String hardDisk = shell.execute("df -h");
        String version = shell.execute("cat /etc/issue | grep Linux ");
        System.out.println(memory);
        System.out.println(cpu);
        System.out.println(cpu2);
        System.out.println(hardDisk);
        System.out.println(version);
        shell.close();

    }

}
