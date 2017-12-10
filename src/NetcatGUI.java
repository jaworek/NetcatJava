import java.awt.*;
import javax.swing.*;

class NetcatGUI extends JFrame
{
    public static JButton sendButton;
    public static JTextArea rxArea;
    public static JTextField txArea;
    public JScrollPane pane;
    public Container container;

    public NetcatGUI(String title)
    {
        super(title);

        container = getContentPane();
        container.setLayout(new FlowLayout());

        rxArea = new JTextArea(6, 43);
        rxArea.setEditable(false);
        pane = new JScrollPane(rxArea);

        txArea = new JTextField(43);

        sendButton = new JButton("Send");

        container.add(new JLabel("Chat:"));
        container.add(pane);
        container.add(new JLabel("Send message:"));
        container.add(txArea);
        container.add(sendButton);

        this.setSize(new Dimension(550, 240));
        this.setResizable(false);
        this.setVisible(true);
    }
}