/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package epsevg.upc.edu.c2223;

import java.awt.Color;
import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import java.io.Serializable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import robocode.MessageEvent;
//import java.util.TreeMap;
//import java.util.Map;
import java.util.*;
//import robocode.util;
import java.lang.Math;

/**
 *
 * @author UX431F
 */

public class PruebaRobot extends TeamRobot{
    
    
    private static Map<Double,String> t = new TreeMap<Double,String>();
    //TreeMap<Integer,String> t2 = new TreeMap<Integer,String>();
    //TreeMap<Integer,String> t3 = new TreeMap<Integer,String>();
    //TreeMap<Integer,String> t4 = new TreeMap<Integer,String>();
    //TreeMap<Integer,String> t5 = new TreeMap<Integer,String>();
    
    static Double X_1, Y_1, X_2, Y_2;
    
    
    
    public void run(){
        turnLeft(getHeading());
        ///*
        List<Double> x1;
        String[] teammates = getTeammates();
        
        double x_1 = getX(), y_1 = getY();
        //calculaDistancia(member, x, y);

        out.println("   MIIIII Coordenada X: "+x_1);
        out.println("    MIIIII  Coordenada Y: "+y_1);
        
        if (teammates != null) {
            int i = 0;
            
            for (String member : teammates){ //temmates --> son el resto de compañeros, NO YO
                setBodyColor(Color.yellow);
                setRadarColor(Color.green);
                
                //TreeMap<Integer,String> t = new TreeMap<Integer,String>();
                out.println(" I'm : " + member);
                //out.println()
                double x = getX(), y = getY();
                calculaDistancia(member, x, y);
                
                out.println(" Coordenada X: "+x);
                out.println(" Coordenada Y: "+y);
                double w = getBattleFieldWidth(), h = getBattleFieldHeight();
                // out.println("getBattleFieldWidth: "+w);
                // out.println("getBattleFieldHeight: "+ h);
                
                
                out.println("ARRAY:   "+Arrays.toString(teammates));
                
                
                
                
              
            }
            /*try {
                // Send RobotColors object to our entire team
                broadcastMessage("I'm here BRODCAST!");
            } catch (IOException ignored) {}*/
            System.out.println("TreeMap Elements...\n" + t);
        }
        //String name = getName();
        //System.out.println("getName:  ",name);
        if(!"epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(getName())){
            try {
                sendMessage("epsevg.upc.edu.c2223.PruebaRobot* (3)", ""+x_1+","+y_1);  //epsevg.upc.edu.c2223.PruebaRobot* (3) sent me: 142.91460826814074,717.9883720521718
             } catch (IOException ignored) {}
    
        }else{
            calculaDistancia("epsevg.upc.edu.c2223.PruebaRobot* (3)", x_1, y_1);
            //FIXME: Cambiar nombres de variables propias del robot para que sea menos lioso
        }
        //*/
        while(true){
            ahead(1000);
            turnRight(90);
        }
    }
    public double obtenirDistanciaEsquina(double xc, double xtank, double yc, double ytank){
        return Math.abs(xc-xtank) + Math.abs(yc-ytank);
    }

    public void calculaDistancia(String CualTankSoy, double x, double y){
        double DistEsq = 0.0;
        for(int i =0; i<4; ++i){
            if(i==0){
                
                DistEsq = obtenirDistanciaEsquina(0.0, x, 0.0, y);
                t.put(DistEsq, CualTankSoy+"_c"+i);
            }else if(i == 1){
                DistEsq = obtenirDistanciaEsquina(0.0, x, 800.0, y);
                t.put(DistEsq, CualTankSoy+"_c"+i);
            }else if(i == 2){
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 800.0, y);
                t.put(DistEsq, CualTankSoy+"_c"+i);
            }else{
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 0.0, y);
                t.put(DistEsq, CualTankSoy+"_c"+i);
            }
        }
        

    }

    
    
    public void onMessageReceived(MessageEvent event) {
        out.println(event.getSender() + " sent me: " + event.getMessage()); // + "  soy: "+event.getMessage().getClass()
        String mssg = (event.getMessage()).toString();
        String[] m = mssg.split(",");
        String sender = (event.getSender()).toString();
        
        if("epsevg.upc.edu.c2223.PruebaRobot* (1)".equals(sender)){
            X_1 = Double.parseDouble(m[0]); 
            Y_1 = Double.parseDouble(m[1]);
            out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            calculaDistancia(sender, X_1, Y_1);
        }
        else if("epsevg.upc.edu.c2223.PruebaRobot* (2)".equals(sender)){
            X_2 = Double.parseDouble(m[0]); 
            Y_2 = Double.parseDouble(m[1]);
            out.println("X_2 recibido = " +X_2+ "  Y_2 recibido = " +Y_2+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
            calculaDistancia(sender, X_2, Y_2);
        }
        
   }
    
    public void onScannedRobot(ScannedRobotEvent e){
        fire(1);
    }
    
    public void onHitByBullet(HitByBulletEvent e){
        turnLeft(180);
    }

        
}