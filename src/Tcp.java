import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class Tcp
{
    PrintWriter out;
    BufferedReader in;
    ButtonHandler txButtonHandler;
    JButton sendButton = Netcat.sendButton;
    JTextArea rxArea = Netcat.rxArea;
    JTextField txArea = Netcat.txArea;

    void tx() throws IOException
    {
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
            }
        }
    }

    void rx() throws IOException
    {
        String recievedMessage;
        while ((recievedMessage = in.readLine()) != null)
        {
            String existingText = rxArea.getText();
            rxArea.setText(existingText + "Client: " + recievedMessage + System.lineSeparator());
        }
    }
}