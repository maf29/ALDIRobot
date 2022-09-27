/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package epsevg.upc.edu.c2223;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 *
 * @author UX431F
 */
public class PruebaRobot extends Robot{
    public void run(){
        turnLeft(getHeading());
        while(true){
            ahead(1000);
            turnRight(90);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e){
        fire(1);
    }
    
    public void onHitByBullet(HitByBulletEvent e){
        turnLeft(180);
    }
    
}
