package com.smutek.chat.udp;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by damian on 11.03.17.
 */
public class UDPConnectionHandler extends Thread {
    private int port;
    private DatagramSocket socket = null;
    private InetAddress address;
    private static int bufferSize = 1024;

    public UDPConnectionHandler(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
            address = InetAddress.getByName("localhost");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


    public void sendUDP(String image){
        try {
            byte[] sendBuffer = new byte[bufferSize];
            byte[] imageBytes = image.getBytes();
            int start = 0;
            while(start < imageBytes.length){
                int end = Math.min(imageBytes.length, start + bufferSize);
                sendBuffer = Arrays.copyOfRange(imageBytes, start, end);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length , address, 12346);
                socket.send(sendPacket);
                start += bufferSize;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //if (socket != null) socket.close();
        }
    }

    public void run(){
        byte[] receiveBuffer = new byte[bufferSize];
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            try {
                socket.receive(receivePacket);
                System.out.println(new String(receivePacket.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
