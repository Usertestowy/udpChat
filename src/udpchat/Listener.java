/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafal
 */
public class Listener extends Thread{
    public static final int PORT = 54321;
    public static final String NAME_PREFIX = "#user:";
    
    ChatWindow chat;
    DatagramSocket socket;
    
    public Listener(ChatWindow chat) {
        this.chat = chat;
    }

    private boolean isNameMessage(String message) {
        boolean isName = false;
        if(message.startsWith(NAME_PREFIX)){
            String name = message.substring(6);
            chat.updateUserList(name);
            isName = true;
        }
        
        return isName;
    }
    
    @Override
    public void run() {
        super.run(); 
        byte[] buf = new byte[256];
   byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        try {
            
            socket = new DatagramSocket(PORT);
            
        } catch (SocketException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String line;
        while(true){
                try {
                        socket.receive(packet);
                } catch (IOException e) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, e);
                }
                
                line = new String(packet.getData(), 0, packet.getLength());
                
                if( ! isNameMessage(line)) {
                    chat.addMessage(line);
                }
        }
    }
    
}
