package com.smutek.chat;

import com.smutek.chat.asciiart.ASCIIArtService;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by damian on 11.03.17.
 */
public class MessageReceiver extends Thread {
    private BufferedReader in;

    public MessageReceiver(BufferedReader in){
        this.in = in;
    }

    public void run(){
        try {
            while (true) {
                String response = in.readLine();
                if (response == null){
                    System.out.println("Server not responding");
                    break;
                }
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
