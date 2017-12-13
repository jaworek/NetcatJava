import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TcpClient extends Tcp
{
    TcpClient(String remoteAddr, String remotePort) throws IOException
    {
        super();
        Socket socket = new Socket(remoteAddr, Integer.parseInt(remotePort));
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        rx();

        socket.close();
        System.exit(1);
    }
}