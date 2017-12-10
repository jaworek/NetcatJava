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
            finished = true;
        }
    }

    public void run()
    {
        while (!finished) ;
    }
}