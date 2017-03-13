package com.smutek.chat;

import com.smutek.chat.asciiart.ASCIIArtService;
import com.smutek.chat.udp.UDPConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 12345;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String nick = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Chat client started");
        System.out.println("Write your nick: ");
        try {
            nick = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(nick);
            int UDPPort = Integer.parseInt(in.readLine());
            UDPConnectionHandler udpConnectionHandler = new UDPConnectionHandler(UDPPort);
            udpConnectionHandler.start();
            MessageReceiver messageReceiver = new MessageReceiver(in);
            messageReceiver.start();
            while (true){
                String msg = br.readLine();
                if (msg.equals("k")){
                    udpConnectionHandler.sendUDP(ASCIIArtService.getImage());
                }
                else
                    out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
            out.close();
            in.close();
            br.close();
        }
    }
}
