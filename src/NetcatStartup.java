import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NetcatStartup extends NetcatStartupGUI
{
    public ButtonHandler bHandler;
    volatile boolean finished = false;

    public NetcatStartup(String title)
    {
        super(title);
        bHandler = new ButtonHandler();
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

    public boolean run()
    {
        while (!finished) ;
        return true;
    }
}