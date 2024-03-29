import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NetcatStartup extends NetcatStartupGUI
{
    private volatile boolean finished = false;

    NetcatStartup(String title)
    {
        super(title);
        ButtonHandler bHandler = new ButtonHandler();
        startButton.addActionListener(bHandler);
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            role = (String) netcatRole.getSelectedItem();

            remoteAddr = remoteIPTextField.getText();
            remotePort = remotePortTextField.getText();
            localPort = localPortTextField.getText();

            switch (role)
            {
                case "TCP Server":
                case "UDP Server":
                    if (localPort.matches("\\d*"))
                    {
                        finished = true;
                    } else
                    {
                        JOptionPane.showMessageDialog(container, "Please, provide correct port number.", "Invalid local port", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case "TCP Client":
                case "UDP Client":
                    if (remotePort.matches("\\d*") && remoteAddr.matches("\\d*\\.\\d*\\.\\d*\\.\\d*"))
                    {
                        finished = true;
                    } else
                    {
                        JOptionPane.showMessageDialog(container, "Please, provide correct port number or ip address.", "Invalid local port or ip address", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }
        }
    }

    public void run()
    {
        while (!finished) ;
    }
}