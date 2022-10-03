/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package epsevg.upc.edu.c2223;

import java.awt.Color;
import java.awt.Graphics2D;
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
    
    
    private static TreeMap<Double,String> t_corner0 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner1 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner2 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner3 = new TreeMap<Double,String>();
    
    //Provisional
    private static double distancia_t1= 0.0;
    private static double distancia_t2= 0.0;
    private static double distancia_t3= 0.0;
    private static double distancia_t4= 0.0;
    
    private double angulo = 0.0;
    //------------------

    //static String[] quinCorner = new String [4];
    static Double X_1, Y_1, X_2, Y_2;
    static Boolean T1_OnCorner, T2_OnCorner, T3_OnCorner, T4_OnCorner, T5_OnCorner = false;
    
    
    
    public void run(){
        //turnLeft(getHeading());
        double x_t = getX(), y_t = getY();
        out.println("   MIIIII Coordenada X: "+x_t);
        out.println("    MIIIII  Coordenada Y: "+y_t);
        System.out.println(">>Hacia donde miro: "+getHeading());
        
        if(!"epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(getName())){
            try {
               sendMessage("epsevg.upc.edu.c2223.PruebaRobot* (3)", "EnvioCoords:"+x_t+","+y_t);  //epsevg.upc.edu.c2223.PruebaRobot* (3) sent me: 142.91460826814074,717.9883720521718
            } catch (IOException ignored) {}
    
        }else{
            calculaDistancia("epsevg.upc.edu.c2223.PruebaRobot* (3)", x_t, y_t);
            //FIXME: Cambiar nombres de variables propias del robot para que sea menos lioso
        }
        //System.out.println("TreeMap_Corner0 Elements...\n" + t_corner0);
        //System.out.println("TreeMap_Corner1 Elements...\n" + t_corner1);
        //System.out.println("TreeMap_Corner2 Elements...\n" + t_corner2);
        //System.out.println("TreeMap_Corner3 Elements...\n" + t_corner3);
        //AsignarEsquina();
        
        //while(true){
            
            //ahead(-1000);
            //turnRight(90);
        //}
    }
    @Override
    public void onPaint(Graphics2D g) {
        // Set the paint color to red
        //g.setColor(java.awt.Color.GREEN);
        setRadarColor(java.awt.Color.GREEN);
        // Paint a filled rectangle at (50,50) at size 100x150 pixels
        //g.fillRect(50, 50, 100, 150);
    } 
    
    public double obtenirDistanciaEsquina(double xc, double xtank, double yc, double ytank){
        //return Math.abs(xc-xtank) + Math.abs(yc-ytank);
        return Math.sqrt((Math.pow((xc-xtank),2))+(Math.pow((yc-ytank),2)));
    }

    public void calculaDistancia(String CualTankSoy, double x, double y){
        //double DistEsq = 0.0;
        System.out.println("Tanque Que entra en CualTankSoy "+CualTankSoy);
        if(CualTankSoy.equals("epsevg.upc.edu.c2223.PruebaRobot* (1)") ){
           distancia_t1=obtenirDistanciaEsquina(0.0, x, 800.0, y);
           //Probamos a enviar un mensaje
           try {
               sendMessage("epsevg.upc.edu.c2223.PruebaRobot* (1)", "DistanciaHaciaLaEsquina:"+distancia_t1+"-"+"epsevg.upc.edu.c2223.PruebaRobot* (1)");  
                    //epsevg.upc.edu.c2223.PruebaRobot* (3) sent me: 142.91460826814074,717.9883720521718
            } catch (IOException ignored) {}
        }else if(CualTankSoy.equals("epsevg.upc.edu.c2223.PruebaRobot* (2)")){
            distancia_t2=obtenirDistanciaEsquina(1000.0, x, 800.0, y);
        }else if(CualTankSoy.equals("epsevg.upc.edu.c2223.PruebaRobot* (3)")){
            distancia_t3=obtenirDistanciaEsquina(1000.0, x, 0.0, y);
        }else if(CualTankSoy.equals("epsevg.upc.edu.c2223.PruebaRobot* (4)")){
            distancia_t4=obtenirDistanciaEsquina(0.0, x, 0.0, y);
        
        }
        /*for(int i =0; i<4; ++i){
            if(i==0){
                DistEsq = obtenirDistanciaEsquina(0.0, x, 0.0, y);
                
                //t_corner0.put(DistEsq, CualTankSoy+"_c"+i);
            }else if(i == 1){
                DistEsq = obtenirDistanciaEsquina(0.0, x, 800.0, y);
                t_corner1.put(DistEsq, CualTankSoy+"_c"+i);
            }else if(i == 2){
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 800.0, y);
                t_corner2.put(DistEsq, CualTankSoy+"_c"+i);
            }else{
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 0.0, y);
                t_corner3.put(DistEsq, CualTankSoy+"_c"+i);
            }
        } */
        

    }

    
    
    public void onMessageReceived(MessageEvent event) {
        out.println(event.getSender() + " sent me: " + event.getMessage()); // + "  soy: "+event.getMessage().getClass()
        String mssg = (event.getMessage()).toString();
        String sender = (event.getSender());
        String[] ssb1 = mssg.split(":");
        String command = ssb1[0];
        System.out.println("========================================");
        System.out.println("Que tiene el command "+command);
        System.out.println("========================================");
        
        System.out.println("Quien me envia:"+sender);
        
        if(command.equals("EnvioCoords")){
            String comvalue = ssb1[1];
            String[] ssb2 = comvalue.split(",");
            String coordX = ssb2[0];
            System.out.println("========================================");
            System.out.println("Que tiene el comvalue "+comvalue);
            System.out.println("========================================");
            System.out.println("========================================");
            System.out.println("Las X son:"+coordX);
            System.out.println("========================================");
            String coordY = ssb2[1];
            if("epsevg.upc.edu.c2223.PruebaRobot* (1)".equals(sender)){
                X_1 = Double.parseDouble(coordX); 
                Y_1 = Double.parseDouble(coordY);
                out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                calculaDistancia(sender, X_1, Y_1);
            }
            else if("epsevg.upc.edu.c2223.PruebaRobot* (2)".equals(sender)){
                X_2 = Double.parseDouble(coordX); 
                Y_2 = Double.parseDouble(coordY);
                out.println("X_2 recibido = " +X_2+ "  Y_2 recibido = " +Y_2+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_2, Y_2);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(sender)){
                X_2 = Double.parseDouble(coordX); 
                Y_2 = Double.parseDouble(coordY);
                out.println("X_2 recibido = " +X_2+ "  Y_2 recibido = " +Y_2+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_2, Y_2);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (4)".equals(sender)){
                X_2 = Double.parseDouble(coordX); 
                Y_2 = Double.parseDouble(coordY);
                out.println("X_2 recibido = " +X_2+ "  Y_2 recibido = " +Y_2+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_2, Y_2);
            }
        
        }else if(command.equals("DistanciaHaciaLaEsquina")){
            String s = ssb1[1];
            String[] ssb2 = s.split("-");
            String stringdist = ssb2[0];
            System.out.println("Distancia llegada:"+stringdist);
            double dist = Double.parseDouble(stringdist);
            String reciver = ssb2[1];
            
            
            if("epsevg.upc.edu.c2223.PruebaRobot* (1)".equals(reciver)){
                System.out.println("Hacia donde miro: "+getHeading());
                double angulo_restante = 315.0-getHeading();
                //dist = (0.0+dist*Math.sin(angulo_restante))+Math.abs(800+dist*Math.cos(angulo_restante));
                System.out.println("Angulo_restante: "+angulo_restante);
                //
                turnRight(angulo_restante);
                ahead(dist);
                System.out.println(">>>>Distancia: "+dist);
                //setAdjustRadarForRobotTurn(true); //Preguntar bernat
                //setTurnRight(angulo_restante);
                //setAhead(dist);
                //setRadarColor(java.awt.Color.GREEN);
                //execute();
                
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (2)".equals(sender)){
                turnRight(45);
                ahead(dist);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(sender)){
                turnRight(135);
                ahead(dist);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (4)".equals(sender)){
                turnRight(225);
                ahead(dist);
            }else{
                System.out.println("No es ninguno de los 4");
            }
            
        }
        
   }
    
    public void onScannedRobot(ScannedRobotEvent e){
        if(! isTeammate(e.getName())){
            fire(1);
        }
    }
    
    public void onHitByBullet(HitByBulletEvent e){
        turnLeft(180);
    }

        
}