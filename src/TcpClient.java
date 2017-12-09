import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TcpClient extends Tcp
{
    Socket socket;

    TcpClient(String remoteAddr, String remotePort) throws IOException
    {
        super();
        socket = new Socket(remoteAddr, Integer.parseInt(remotePort));
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        txButtonHandler = new ButtonHandler();
        sendButton.addActionListener(txButtonHandler);

        rx();

        socket.close();

        System.exit(1);
    }
}