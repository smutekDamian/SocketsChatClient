package com.smutek.chat.udp;

import java.io.IOException;
import java.net.*;


/**
 * Created by damian on 13.03.17.
 */
public class UDPMulticastConnectionHandler extends Thread {
    private int port = 13345;
    private String multicastAddr = "230.1.1.1";
    private MulticastSocket multicastSocket = null;
    private InetAddress address = null;
    private static int bufforSize = 3000;

    public UDPMulticastConnectionHandler(){
        try {
            address = InetAddress.getByName(multicastAddr);
            multicastSocket = new MulticastSocket(port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            multicastSocket.joinGroup(address);
            byte[] receiveBuffer = new byte[bufforSize];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, address , port);
            while (true){
                multicastSocket.receive(receivePacket);
                System.out.println(new String(receivePacket.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                multicastSocket.leaveGroup(address);
                multicastSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMulticastMsg(String msg){
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
            datagramSocket.send(sendPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

}
