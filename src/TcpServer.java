import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class TcpServer extends Tcp
{
    TcpServer(String port) throws IOException
    {
        super();
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
        Socket socket = serverSocket.accept();
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        rx();

        socket.close();
        serverSocket.close();
        System.exit(1);
    }
}