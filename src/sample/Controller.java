package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;


public class Controller implements MessageHandler {

    @FXML
    TextArea textarea;
    @FXML
    TableView<UdpMessage> table;
    @FXML
    ToggleButton toggleBtnEcho;

    private boolean isBroadcasting = true;
    private boolean isListening = true;
    private UdpConnector udpConnector;

    public void toggleBtnEchoServer()
    {
        System.out.println("togglebtnECHO clicked");
        if (udpConnector.isReceiveMessages())
        {
            udpConnector.setReceiveMessages(false);
            toggleBtnEcho.setText("OFF");
        }
        else
        {
            startUdpConnection();
            toggleBtnEcho.setText("ON");
        }


    }

    public void toggleBtnBroadcastServer()
    {
        System.out.println("togglebtnBROADCAST clicked");
    }

    public void clearLog()
    {
        table.getItems().clear();
    }

    public void initialize()
    {
        System.out.println("initialize");

        startUdpConnection();

    }

    private void startUdpConnection() {
        udpConnector = new UdpConnector(this);
        new Thread(udpConnector).start();
    }

    @Override
    public void receiveMessage(UdpMessage udpMessage)
    {
        table.getItems().add(0, udpMessage);
    }


}
