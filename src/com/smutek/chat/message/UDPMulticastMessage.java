package com.smutek.chat.message;

import java.io.Serializable;

/**
 * Created by damian on 13.03.17.
 */
public class UDPMulticastMessage implements Serializable {
    private String nick;
    private String message;

    public UDPMulticastMessage(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
