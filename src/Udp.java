import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Udp
{
    DatagramSocket socket;
    InetAddress ip = null;
    int port;
    private String fromServer = "";
    private JTextArea rxArea = Netcat.rxArea;
    private JTextField txArea = Netcat.txArea;

    Udp()
    {
        ButtonHandler txButtonHandler = new ButtonHandler();
        JButton sendButton = Netcat.sendButton;
        sendButton.addActionListener(txButtonHandler);
    }

    private void tx() throws IOException
    {
        byte[] buf;

        String toServer;

        toServer = txArea.getText();
        if (toServer.equals("")) return;

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