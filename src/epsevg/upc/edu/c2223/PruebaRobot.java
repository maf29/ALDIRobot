/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package epsevg.upc.edu.c2223;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import java.io.Serializable;
import java.io.IOException;
import java.util.Arrays;
import robocode.MessageEvent;
//import robocode.util;

/**
 *
 * @author UX431F
 */

/*
public void broadcastMessage(String team, Serializable message) throws IOException {
  if (peer != null) {
    ((ITeamRobotPeer) peer).broadcastMessage(message);
  } else {
    uninitializedException();
  }
}
*/



public class PruebaRobot extends TeamRobot{
    
    public void run(){
        turnLeft(getHeading());
        ///*
        
        String[] teammates = getTeammates();
        //System.out.println(teammates.length());
        out.println("ARRAY:   "+Arrays.toString(teammates));
        if (teammates != null) {    
            for (String member : teammates){ //temmates --> son el resto de compañeros, NO YO
                out.println(" I'm : " + member);
                //double coor_x = member.getX();
                //out.println()
                out.println("Coordenada X: "+getX());
                out.println("Coordenada Y: "+getY());
                out.println("ARRAY:   "+Arrays.toString(teammates));
                // epsevg.upc.edu.c2223.PruebaRobot*
                //sendMessage(""+member+"","I'm here!");
                try {
                   sendMessage(member,"MESSAGEEEEEEEEEE");
                } catch (IOException ignored) {}
            }
            //broadcastMessage("I'm here BRODCAST!");
            try {
                // Send RobotColors object to our entire team
                broadcastMessage("I'm here BRODCAST!");
            } catch (IOException ignored) {}
        }
        
        //*/
        while(true){
            ahead(1000);
            turnRight(90);
        }
    }
    
    
    public void onMessageReceived(MessageEvent event) {
        out.println(event.getSender() + " sent me: " + event.getMessage());
   }
    
    public void onScannedRobot(ScannedRobotEvent e){
        fire(1);
    }
    
    public void onHitByBullet(HitByBulletEvent e){
        turnLeft(180);
    }

        
}
