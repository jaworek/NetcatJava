import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Netcat extends NetcatGUI
{
    String role;
    static String remoteAddr;
    static String localPort, remotePort;

    public Netcat(String title)
    {
        super(title);
    }

    public void run() throws IOException
    {
        if (role.equals("TCP Server"))
        {
            System.out.println("nc -l " + localPort);
            new TcpServer(localPort);
        }
        if (role.equals("TCP Client"))
        {
            System.out.println("nc " + remoteAddr + " " + remotePort);
            new TcpClient(remoteAddr, remotePort);
        }
        if (role.equals("UDP Server"))
        {
            System.out.println("nc -u -l " + localPort);
            new UdpServer(localPort);
        }
        if (role.equals("UDP Client"))
        {
            System.out.println("nc -u " + remoteAddr + " " + remotePort);
            new UdpClient(remoteAddr, remotePort);
        }
    }

    public void g()
    {
        NetcatStartup p = new NetcatStartup("Netcat Connection");

        p.setSize(new Dimension(550, 250));
        p.setResizable(false);
        p.setVisible(true);
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

        f.setTitle(f.role);
        f.setSize(new Dimension(550, 240));
        f.setResizable(false);
        f.setVisible(true);
        f.run();
    }
}