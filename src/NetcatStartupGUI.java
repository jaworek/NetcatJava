import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NetcatStartupGUI extends JFrame
{
    JButton startButton;
    JComboBox netcatRole;
    JTextField remotePortTextField, remoteIPTextField, localPortTextField;
    Container container;
    ListHandler lHandler;
    String role, remoteAddr, localPort, remotePort;

    @SuppressWarnings("unchecked")
    public NetcatStartupGUI(String title)
    {
        super(title);
        String[] netcatRoleString = {"                                              Netcat Role:                                         ",
                "TCP Server", "TCP Client", "UDP Server", "UDP Client"};

        container = getContentPane();
        container.setLayout(new FlowLayout());
        netcatRole = new JComboBox(netcatRoleString);
        remotePortTextField = new JTextField("1234",43);
        remoteIPTextField = new JTextField("127.0.0.1",43);
        localPortTextField = new JTextField("1234",43);

        lHandler = new ListHandler();
        netcatRole.addActionListener(lHandler);

        startButton = new JButton("Start");

        container.add(netcatRole);
        container.add(new JLabel("Remote IP:"));
        container.add(remoteIPTextField);
        container.add(new JLabel("Remote Port:"));
        container.add(remotePortTextField);
        container.add(new JLabel("Local Port:"));
        container.add(localPortTextField);
        container.add(startButton);

        this.setSize(new Dimension(550, 250));
        this.setResizable(false);
        this.setVisible(true);
    }

    private class ListHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            role = (String) netcatRole.getSelectedItem();
            if (role.equals("TCP Server") || role.equals("UDP Server"))
            {
                remoteIPTextField.setEditable(false);
                remoteIPTextField.setBackground(Color.RED);
                remotePortTextField.setEditable(false);
                remotePortTextField.setBackground(Color.RED);
                localPortTextField.setEditable(true);
                localPortTextField.setBackground(Color.WHITE);
            }
            if (role.equals("TCP Client") || role.equals("UDP Client"))
            {
                remoteIPTextField.setEditable(true);
                remoteIPTextField.setBackground(Color.WHITE);
                remotePortTextField.setEditable(true);
                remotePortTextField.setBackground(Color.WHITE);
                localPortTextField.setEditable(false);
                localPortTextField.setBackground(Color.RED);
            }
        }
    }
}