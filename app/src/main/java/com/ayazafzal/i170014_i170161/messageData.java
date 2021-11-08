package com.ayazafzal.i170014_i170161;

public class messageData implements Comparable<messageData> {
    String ID,Sender,Reciever,Hour,Minute,Call,Text,Image,Screenshot;

    public messageData(String id, String sender, String reciever, String hour, String minute, String call, String text, String image, String screenshot) {
        ID=id;
        Sender = sender;
        Reciever = reciever;
        Hour = hour;
        Minute = minute;
        Call = call;
        Text = text;
        Image = image;
        Screenshot = screenshot;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
    }

    public String getCall() {
        return Call;
    }

    public void setCall(String call) {
        Call = call;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getScreenshot() {
        return Screenshot;
    }

    public void setScreenshot(String screenshot) {
        Screenshot = screenshot;
    }

    @Override
    public int compareTo(messageData messageData) {
        Integer.parseInt("200");
        int hour1,hour2;
        int minute1,minute2;
        hour1=Integer.parseInt(this.Hour);
        hour2=Integer.parseInt(messageData.getHour());
        minute1=Integer.parseInt(this.Minute);
        minute2=Integer.parseInt(messageData.getMinute());
        if(hour1<hour2){
            return -1;
        }else if(hour1==hour2 && minute1<minute2){
            return  -1;
        }else{
            return  1;
        }
    }
}

