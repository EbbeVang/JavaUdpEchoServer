package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;


public class Controller {

    // Our fxml elements we need to grab.
    @FXML
    TableView<UdpMessage> table;
    @FXML
    ToggleButton toggleBtnEcho;
    @FXML
    ToggleButton toggleBtnBroadcast;

    private UdpConnector udpConnector;
    private UdpBroadcastServer broadcastServer;

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
        if (broadcastServer.isBroadcast())
        {
            broadcastServer.setBroadcast(false);
            toggleBtnBroadcast.setText("OFF");
        }
        else
        {
            startBroadcasting();
            toggleBtnBroadcast.setText(("ON"));
        }
    }

    public void clearLog()
    {
        table.getItems().clear();
    }

    public void initialize()
    {
        System.out.println("initialize");

        startUdpConnection();
        startBroadcasting();
    }

    private void startBroadcasting() {
        broadcastServer = new UdpBroadcastServer();
        new Thread(broadcastServer).start();
    }

    private void startUdpConnection() {
        if (udpConnector != null) udpConnector.closeSocket();
        udpConnector = new UdpConnector(this);
        new Thread(udpConnector).start();
    }

    public void receiveMessage(UdpMessage udpMessage)
    {
        table.getItems().add(0, udpMessage);
    }
}