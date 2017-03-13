package com.smutek.chat.udp;

import com.smutek.chat.message.UDPMulticastMessage;

import java.io.*;
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
    private String nick;

    public UDPMulticastConnectionHandler(String nick){
        this.nick = nick;
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
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(receiveBuffer));
                UDPMulticastMessage message = (UDPMulticastMessage) in.readObject();
                in.close();
                if (!message.getNick().equals(nick)) {
                    System.out.println(message.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                multicastSocket.leaveGroup(address);
                multicastSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMulticastMsg(UDPMulticastMessage msg){
        DatagramSocket datagramSocket = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(bufforSize);
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(msg);
            out.close();
            byte[] buffer = baos.toByteArray();
            datagramSocket = new DatagramSocket();
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address, port);
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
