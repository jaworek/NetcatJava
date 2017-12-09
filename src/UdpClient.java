import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

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