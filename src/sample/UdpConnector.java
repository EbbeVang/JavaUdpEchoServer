package sample;

import java.io.IOException;
import java.net.*;

public class UdpConnector implements Runnable{
    private DatagramSocket socket;
    private int udpPort = 7000;
    private int udpPortEcho = 7007;
    private MessageHandler messageHandler;
    private boolean receiveMessages = true;

    public UdpConnector(MessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
        setupSocket();
    }

    private void setupSocket() {
        try {
            socket = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String string, InetAddress address)
    {
        sendMessage(string.getBytes(), address);
    }

    public void sendMessage(byte[] bytes, InetAddress address)
    {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, udpPortEcho);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public UdpMessage receiceMessage() {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            System.out.println("waiting for a udp packet on port: "+ udpPort);
            socket.receive(packet);
            UdpMessage message = new UdpMessage(packet.getData(), packet.getLength(), packet.getAddress() , packet.getPort());
            System.out.println("received: "+ message);

            if (receiveMessages) messageHandler.receiveMessage(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void echoServer()
    {
        UdpMessage message = receiceMessage();
        try {
            sendMessage("echoserver receiver: "+message.getMessage(), InetAddress.getByName(message.getIp()));
        } catch (UnknownHostException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void run() {
        System.out.println("Started UdpConnector Thread");
        do
        {
            echoServer();
            //receiceMessage();
        }
        while(isReceiveMessages());
        socket.close();

    }

    public boolean isReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(boolean receiveMessages) {
        this.receiveMessages = receiveMessages;
    }
}
