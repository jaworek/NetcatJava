import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

abstract class Tcp
{
    PrintWriter out;
    BufferedReader in;
    private JTextArea rxArea = Netcat.rxArea;
    private JTextField txArea = Netcat.txArea;

    Tcp()
    {
        ButtonHandler txButtonHandler = new ButtonHandler();
        JButton sendButton = Netcat.sendButton;
        sendButton.addActionListener(txButtonHandler);
    }

    void tx() throws IOException
    {
        if (txArea.getText().equals("")) return;
        out.println(txArea.getText());
        rxArea.setText(rxArea.getText() + "Me: " + txArea.getText() + System.lineSeparator());
        txArea.setText("");
    }

    class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)                         //throws IOException
        {
            try
            {
                tx();
            } catch (IOException e)
            {
                System.out.println("Caught an exception");
            }
        }
    }

    void rx() throws IOException
    {
        String receivedMessage;
        while ((receivedMessage = in.readLine()) != null)
        {
            rxArea.setText(rxArea.getText() + "Client: " + receivedMessage + System.lineSeparator());
        }
    }
}