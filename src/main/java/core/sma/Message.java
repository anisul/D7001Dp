package core.sma;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

public class Message {
    private static final int T_ANY = 0;
    private static final int T_INTEGER = 1;
    private static final int T_REAL = 2;
    private static final int T_STRING = 3;
    private static final int T_BOOLEAN = 4;
    private static final int T_UNDEFINED = 5;
    private Hashtable parameters = new Hashtable();
    private int type = 0;
    private int tag = 0;
    private int length = 0;
    private static final int HEADER_SIZE = 3;
    private char[] rep;
    private int repIndex = 0;
    private StringBuffer sb = new StringBuffer();
    public Message(char[] packedRep) {
        rep = packedRep;
        unpack();
    }

    public Message() {
    }

    public static Message getMessage(Reader in) throws IOException {
        int messageLength = (int) in.read();
        if (messageLength < 0)
            throw new IOException();
        char[] buf = new char[messageLength];
        int bytesRead = in.read(buf,0,messageLength);
        if (messageLength != bytesRead)
            throw new IOException();
        return new Message(buf);
    }

    public void putMessage(Writer out) throws IOException {
        pack();
        out.write(rep.length);
        out.write(rep);
        out.flush();
    }

    public void setParam(String key, String value) {
        parameters.put(key,value);
    }

    public void setType(int value) {
        //TODO
        type=value;
    }
    public int getType() {
        return type;
        //TODO
    }

    public String getParam(String key) {
        return (String) parameters.get(key);
    }

    public char[] getCharArray() {
        pack();
        return rep;
    }

    private void putInt(int value) {
        if (repIndex < rep.length)
            rep[repIndex++] = (char) value;
    }

    private void putParameter(String k, String v) {
        putString(k);
        putString(v);
    }

    private void putString(String s) {
        putInt(s.length());
        putInt(T_STRING);
        char[] convertedText = s.toCharArray();
        for (int i=0; i < convertedText.length; i++)
            rep[repIndex++] = convertedText[i];
    }

    private int getInt() {
        if (repIndex < rep.length)
            return (int) rep[repIndex++];
        else
            return -1;
    }

    private String getString() {
        int paramLength = getInt();
        int paramType = getInt();
        sb.setLength(0);
        for (int i=0; i < paramLength; i++) {
            if (repIndex < rep.length)
                sb.append(rep[repIndex++]);
        }

        return sb.toString();
    }

    private int computeStorageSize() {
        int totalSize = HEADER_SIZE;
        Enumeration e = parameters.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            totalSize += (2 + key.length());
            String value = (String) parameters.get(key);
            totalSize += (2 + value.length());
        }

        return totalSize;
    }

    public void pack() {
        int totalStorage = computeStorageSize();
        rep = new char[totalStorage];
        length = totalStorage - HEADER_SIZE;
        repIndex = 0;
        putInt(type);
        putInt(tag);
        putInt(length);
        Enumeration e = parameters.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = (String) parameters.get(key);
            putParameter(key,value);
        }
    }

    public void unpack() {
		/* need to clear out the hashtable first */
        parameters.clear();
        repIndex = 0;
        type = getInt();
        tag = getInt();
        length = getInt();
        while (repIndex < rep.length) {
            String key = getString();
            String value = getString();
            parameters.put(key,value);
        }
    }

    public String toString() {
        return "Message: type = "+ type + " param = " + parameters;
    }


    public static void main(String args[]) {
        Message m = new Message();
        m.setParam("v0","1.5");
        m.setParam("v1","2.0");
        m.setParam("person","Luiz");
        m.pack();
        Message m2 = new Message(m.getCharArray());
        System.out.println("0 = " + m2.getParam("v0"));
        System.out.println("v1 = " + m2.getParam("v1"));
        System.out.println("person = " + m2.getParam("person"));
    }
}
