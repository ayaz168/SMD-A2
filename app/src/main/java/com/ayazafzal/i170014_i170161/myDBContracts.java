package com.ayazafzal.i170014_i170161;

import android.provider.BaseColumns;

public class myDBContracts {
    public static String DB_NAME="myChat.db";//Table Name
    public static int DB_VERSION=2;//Like V1, V2, Vn

    public static class Users implements BaseColumns { //Class for one table
        public static String TABLENAME="USER_TABLE";//Actual Table name
        public static String _EMAIL="EMAIL";//Actual Column name
        public static String _PASSWORD="PASSWORD";//Actual Column name
        public static String _FIRSTNAME="FIRSTNAME";//Actual Column name
        public static String _LASTNAME="LASTNAME";//Actual Column name
        public static String _GENDER="GENDER";//Actual Column name
        public static String _BIO="BIO";//Actual Column name
        public static String _STATUS="STATUS";//Actual Column name
        public static String _PHONE="PHONE";//Actual Column name
        public static String _IMAGE = "IMAGE";
    }
    public static class Friends implements BaseColumns{ //Class for one table
        public static String TABLENAME="FRIENDS_TABLE";//Actual Table name
        public static String _ID1="ID1";//Actual Column name
        public static String _ID2="ID2";//Actual Column name
    }
    public static class Messages implements BaseColumns{ //Class for one table
        public static String TABLENAME="MESSAGETABLE";//Actual Table name
        public static String _SENDER="SENDER";//Actual Column name
        public static String _RECEIVER="RECEIVER";//Actual Column name
        public static String _HOUR="HOUR";//Actual Column name
        public static String _MINUTE="MINUTE";//Actual Column name
        public static String _CALL="CALL";//Actual Column name
        public static String _TEXTMESSAGE="TEXTMESSAGE";//Actual Column name
        public static String _IMAGEMESSAGE="IMAGEMESSAGE";//Actual Column name
        public static String _SCREENSHOT="SCREENSHOT";//Actual Column name
    }
}
