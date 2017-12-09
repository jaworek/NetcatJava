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

class Udp
{
    DatagramSocket socket;
    InetAddress ip = null;
    int port;
    ButtonHandler txButtonHandler;
    String fromServer = "";
    JButton sendButton = Netcat.sendButton;
    JTextArea rxArea = Netcat.rxArea;
    JTextField txArea = Netcat.txArea;

    Udp()
    {
        txButtonHandler = new ButtonHandler();
        sendButton.addActionListener(txButtonHandler);
    }

    void tx() throws IOException
    {
        byte[] buf;

        String toServer;

        toServer = txArea.getText();

        buf = toServer.getBytes();

        DatagramPacket packet = new DatagramPacket(buf, toServer.length(), ip, port);

        if (ip != null) socket.send(packet);

        fromServer += "Me: " + txArea.getText() + System.lineSeparator();
        rxArea.setText(fromServer);

        txArea.setText("");
    }

    class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)                         //throws IOException
        {
            try
            {
                tx();
            } catch (IOException e)
            {
            }
        }
    }

    void rx() throws IOException
    {
        byte[] buf = new byte[256];

        do
        {
            for (int i = 0; i < 256; i++) buf[i] = 0;

            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);

            fromServer += "Client: " + new String(packet.getData()) + System.lineSeparator();

            ip = packet.getAddress();

            port = packet.getPort();

            rxArea.setText(fromServer);
        }
        while (fromServer != null);

        socket.close();
        System.exit(1);
    }
}

class UdpServer extends Udp
{
    UdpServer(String localPort) throws IOException
    {
        super();
        socket = new DatagramSocket(Integer.parseInt(localPort));

        rx();
    }
}

class UdpClient extends Udp
{
    UdpClient(String remoteAddr, String remotePort) throws IOException
    {
        super();
        socket = new DatagramSocket();
        port = Integer.parseInt(remotePort);
        ip = InetAddress.getByName(remoteAddr);

        rx();
    }
}