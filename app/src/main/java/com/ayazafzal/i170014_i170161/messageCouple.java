package com.ayazafzal.i170014_i170161;

public class messageCouple {
    messageData send,response;

    public messageData getSend() {
        return send;
    }

    public void setSend(messageData send) {
        this.send = send;
    }
    public messageData getResponse() {
        return response;
    }

    public void setResponse(messageData response) {
        this.response = response;
    }

    public messageCouple(messageData send, messageData response) {
        this.send = send;
        this.response = response;
    }
}
