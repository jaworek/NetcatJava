import java.io.IOException;
import java.net.DatagramSocket;

class UdpServer extends Udp
{
    UdpServer(String localPort) throws IOException
    {
        super();
        socket = new DatagramSocket(Integer.parseInt(localPort));

        rx();
    }
}