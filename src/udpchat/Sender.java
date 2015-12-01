/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import static udpchat.Listener.NAME_PREFIX;
import static udpchat.Listener.PORT;

/**
 *
 * @author rafal
 */
public class Sender extends Thread implements ActionListener{

    ChatWindow chat;
    DatagramSocket socket;
    Timer zegar;
    
    
    public Sender(ChatWindow chat) {
        this.chat = chat;
        
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        zegar = new Timer(250, this);
        zegar.start();
    }

    @Override
    public void run() {
        super.run(); 
        
    }

    public void sendMessage(String message) {
        byte[] buf = new byte[256];
        buf = (chat.getUserName()+": "+message).getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), PORT);
            socket.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        byte[] buf = new byte[256];
        buf = (NAME_PREFIX+chat.getUserName()).getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), PORT);
            socket.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
