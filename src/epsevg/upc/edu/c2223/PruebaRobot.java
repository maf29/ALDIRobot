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
    public class Foo{
        public String name;
        public Double distancia;
        public Foo(Double distancia, String name){
            this.distancia = distancia;
            this.name = name;
        }
    }
    
    
    private static TreeMap<Double,String> t_corner0 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner1 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner2 = new TreeMap<Double,String>();
    private static TreeMap<Double,String> t_corner3 = new TreeMap<Double,String>();
    
    static String[] vect = new String [4];        
    //array.add(new Foo("demo","fdemo"));
    //array.get(0).name;   ==> Return "demo"
    
    static Double X_1, Y_1, X_2, Y_2;
    
    static Boolean T1_OnCorner, T2_OnCorner, T3_OnCorner, T4_OnCorner, T5_OnCorner = false;
    
    public void run(){
        turnLeft(getHeading());
        double x_t = getX(), y_t = getY();
        out.println("   MIIIII Coordenada X: "+x_t);
        out.println("    MIIIII  Coordenada Y: "+y_t);
        if(!"epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(getName())){
            try {
               sendMessage("epsevg.upc.edu.c2223.PruebaRobot* (3)", ""+x_t+","+y_t);  //epsevg.upc.edu.c2223.PruebaRobot* (3) sent me: 142.91460826814074,717.9883720521718
            } catch (IOException ignored) {}
    
        }else{
            calculaDistancia("epsevg.upc.edu.c2223.PruebaRobot* (3)", x_t, y_t);
            //FIXME: Cambiar nombres de variables propias del robot para que sea menos lioso
        }
        System.out.println("TreeMap_Corner0 Elements...\n" + t_corner0);
        System.out.println("TreeMap_Corner1 Elements...\n" + t_corner1);
        System.out.println("TreeMap_Corner2 Elements...\n" + t_corner2);
        System.out.println("TreeMap_Corner3 Elements...\n" + t_corner3);
        
        //
        asignarEsquina();
        
        while(true){
            ahead(1000);
            turnRight(90);
        }
    }

    public void asignar_array(Foo minimo, Foo min_c0, String strg_c0, Foo min_c1, String strg_c1, Foo min_c2, String strg_c2,Foo min_c3, String strg_c3){
        if((minimo.distancia == min_c0.distancia) && (minimo.name == min_c0.name)){
            vect[0] = (strg_c0);
        }
        else if((minimo.distancia == min_c1.distancia) && (minimo.name == min_c1.name)){
            vect[1]=(strg_c1);
        }
        else if((minimo.distancia == min_c2.distancia) && (minimo.name == min_c2.name)){
            vect[2]=(strg_c2);
        }
        else if((minimo.distancia == min_c3.distancia) && (minimo.name == min_c3.name)){
            vect[3]=(strg_c3);
        }
    }
    
    public void asignarEsquina(){
        //FIXME: Mirar si pones un valor sigue dando false.
        //Boolean containNull = Arrays.stream(array).allMatch(Objects::nonNull);

        Foo min_c0 = new Foo(t_corner0.firstKey(), t_corner0.get(t_corner0.firstKey()));
        String strg_c0 = t_corner0.firstKey().toString() +"|"+ t_corner0.get(t_corner0.firstKey()).toString();
        
        Foo min_c1 = new Foo(t_corner1.firstKey(), t_corner1.get(t_corner1.firstKey()));
        String strg_c1 = t_corner1.firstKey().toString() +"|"+ t_corner1.get(t_corner1.firstKey()).toString();
        
        Foo min_c2 = new Foo(t_corner2.firstKey(), t_corner2.get(t_corner2.firstKey()));
        String strg_c2 = t_corner2.firstKey().toString() +"|"+ t_corner2.get(t_corner2.firstKey()).toString();
        
        Foo min_c3 = new Foo(t_corner3.firstKey(), t_corner3.get(t_corner3.firstKey()));
        String strg_c3 = t_corner3.firstKey().toString() +"|"+ t_corner3.get(t_corner3.firstKey()).toString();
        
        Foo minimo;
	//while(containNull) {

            if((min_c0.name == min_c1.name) && (min_c1.name == min_c2.name)  && (min_c2.name == min_c3.name) ){   //si todas las distancias son iguales
                //Miramos quien es el minimo
                minimo = min_c0;
                if(minimo.distancia < min_c1.distancia){
                    minimo.distancia = min_c1.distancia;
                    minimo.name = min_c1.name;
                }
                if(minimo.distancia < min_c2.distancia){
                    minimo.distancia = min_c2.distancia;
                    minimo.name = min_c2.name;
                }
                if(minimo.distancia < min_c3.distancia){
                    minimo.distancia = min_c3.distancia;
                    minimo.name = min_c3.name;
                }

                //Cuando obtengamos el minim, vamos a mirar que variable es para saber de que corner es
                asignar_array(minimo, min_c0,strg_c0, min_c1,strg_c1, min_c2,strg_c2, min_c3,strg_c3);
            }
            /*else{
                
                
            }*/
            

           // containNull = Arrays.stream(array).allMatch(Objects::nonNull);
	//}
        
    }
    
    public double obtenirDistanciaEsquina(double xc, double xtank, double yc, double ytank){
        return Math.abs(xc-xtank) + Math.abs(yc-ytank);
    }

    public void calculaDistancia(String CualTankSoy, double x, double y){
        double DistEsq = 0.0;
        System.out.println("Tanque Que entra en CualTankSoy "+CualTankSoy);
        for(int i =0; i<4; ++i){
            if(i==0){
                DistEsq = obtenirDistanciaEsquina(0.0, x, 0.0, y);
                t_corner0.put(DistEsq, CualTankSoy);
            }else if(i == 1){
                DistEsq = obtenirDistanciaEsquina(0.0, x, 800.0, y);
                t_corner1.put(DistEsq, CualTankSoy);
            }else if(i == 2){
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 800.0, y);
                t_corner2.put(DistEsq, CualTankSoy);
            }else{
                DistEsq = obtenirDistanciaEsquina(1000.0, x, 0.0, y);
                t_corner3.put(DistEsq, CualTankSoy);
            }
        }
        
        
    }

    
    
    public void onMessageReceived(MessageEvent event) {
        out.println(event.getSender() + " sent me: " + event.getMessage()); // + "  soy: "+event.getMessage().getClass()
        String mssg = (event.getMessage()).toString();
        String sender = (event.getSender());
        
        if("epsevg.upc.edu.c2223.PruebaRobot* (1)".equals(sender) || "epsevg.upc.edu.c2223.PruebaRobot* (2)".equals(sender)){
            
            //Si robots (1,2) envian coordenaadas a Robot 3 
            String[] m = mssg.split(",");
            
            System.out.println("Quien me envia:"+sender);
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
        
        
        //SI se recibe mensaje de la esquina asignada (Robot 3 a TODOS SUS COMPAÑEROS)H
        if("epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(sender)){
            //procesar mssg
            
            //mover robot actual a la posición asignada que esta en el mensaje
            
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