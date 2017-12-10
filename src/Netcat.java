import java.io.*;

public class Netcat extends NetcatGUI
{
    private static String role;
    private static String remoteAddr, localPort, remotePort;

    private Netcat(String title)
    {
        super(title);
    }

    private void run() throws IOException
    {
        switch (role)
        {
            case "TCP Server":
                System.out.println("nc -l " + localPort);
                new TcpServer(localPort);
                break;
            case "TCP Client":
                System.out.println("nc " + remoteAddr + " " + remotePort);
                new TcpClient(remoteAddr, remotePort);
                break;
            case "UDP Server":
                System.out.println("nc -u -l " + localPort);
                new UdpServer(localPort);
                break;
            case "UDP Client":
                System.out.println("nc -u " + remoteAddr + " " + remotePort);
                new UdpClient(remoteAddr, remotePort);
                break;
        }
    }

    private void g()
    {
        NetcatStartup p = new NetcatStartup("Netcat Connection");

        p.run();
        role = p.role;
        remoteAddr = p.remoteAddr;
        remotePort = p.remotePort;
        localPort = p.localPort;
        p.dispose();
    }

    public static void main(String[] args) throws IOException
    {
        Netcat f = new Netcat("Netcat");

        f.g();
        f.setTitle(Netcat.role);
        f.run();
    }
}