package sample;

public class UdpBroadcastServer implements Runnable{
    private boolean broadcast;

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    @Override
    public void run() {

    }

    private void broadcastLoop()
    {}
}
