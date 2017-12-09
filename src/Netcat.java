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

    class TcpServer
    {
        ServerSocket serverSocket;
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        ButtonHandler txButtonHandler;

        TcpServer(String port) throws IOException
        {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            socket = serverSocket.accept();
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            txButtonHandler = new ButtonHandler();
            sendButton.addActionListener(txButtonHandler);

            rx();

            socket.close();
            serverSocket.close();

            System.exit(1);
        }

        void tx() throws IOException
        {
            out.println(txArea.getText());
            rxArea.setText(rxArea.getText() + "Me: " + txArea.getText() + System.lineSeparator());
            txArea.setText("");
        }

        private class ButtonHandler implements ActionListener
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
            String recievedMessage;
            while ((recievedMessage = in.readLine()) != null)
            {
                String existingText = rxArea.getText();
                rxArea.setText(existingText + "Client: " + recievedMessage + System.lineSeparator());
            }
        }
    }

    class TcpClient
    {
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        ButtonHandler txButtonHandler;

        TcpClient(String remoteAddr, String remotePort) throws IOException
        {
            socket = new Socket(remoteAddr, Integer.parseInt(remotePort));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            txButtonHandler = new ButtonHandler();
            sendButton.addActionListener(txButtonHandler);

            rx();

            socket.close();

            System.exit(1);
        }

        void tx() throws IOException
        {
            out.println(txArea.getText());
            rxArea.setText(rxArea.getText() + "Me: " + txArea.getText() + System.lineSeparator());
            txArea.setText("");
        }

        private class ButtonHandler implements ActionListener
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
            String recievedMessage;
            while ((recievedMessage = in.readLine()) != null)
            {
                String existingText = rxArea.getText();
                rxArea.setText(existingText + "Client: " + recievedMessage + System.lineSeparator());
            }
        }
    }

    class UdpServer
    {
        DatagramSocket socket;
        InetAddress ip = null;
        int port;
        ButtonHandler txButtonHandler;
        String fromServer = "";

        UdpServer(String localPort) throws IOException
        {
            socket = new DatagramSocket(Integer.parseInt(localPort));

            txButtonHandler = new ButtonHandler();
            sendButton.addActionListener(txButtonHandler);

            rx();

            System.exit(1);
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

        private class ButtonHandler implements ActionListener
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

                if (fromServer != null)
                    rxArea.setText(fromServer);
            }
            while (fromServer != null);

            socket.close();
            System.exit(1);
        }
    }

    class UdpClient
    {
        DatagramSocket socket;
        InetAddress ip;
        int port;
        ButtonHandler txButtonHandler;
        String fromServer = "";

        UdpClient(String remoteAddr, String remotePort) throws IOException
        {
            socket = new DatagramSocket();
            port = Integer.parseInt(remotePort);
            ip = InetAddress.getByName(remoteAddr);

            txButtonHandler = new ButtonHandler();
            sendButton.addActionListener(txButtonHandler);

            rx();

            System.exit(1);
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

        private class ButtonHandler implements ActionListener
        {
            public void actionPerformed(ActionEvent event)                     //throws IOException
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

                if (fromServer != null)
                    rxArea.setText(fromServer);
            }
            while (fromServer != null);

            socket.close();
            System.exit(1);
        }
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