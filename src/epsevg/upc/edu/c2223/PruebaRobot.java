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
import java.util.Iterator;
import java.util.List;
//import robocode.util;
import java.lang.Math;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author UX431F
 */

public class PruebaRobot extends TeamRobot{
    /*public class Pair{
        public String name;
        public Double distancia;
        public Pair(Double distancia, String name){
            this.distancia = distancia;
            this.name = name;
        }  
    }*/
    
    
    
    
    
    
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
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
    private static String kamikaze = "";
    //------------------

    //static String[] quinCorner = new String [4];
    static Double X_1, Y_1;
    //static Boolean T1_OnCorner, T2_OnCorner, T3_OnCorner, T4_OnCorner, T5_OnCorner = false;
    static Boolean OnCorner=false;
    /*private static Double[] c0  = new Double [5];
    private static Double[] c1  = new Double [5];
    private static Double[] c2  = new Double [5];
    private static Double[] c3  = new Double [5];
    */
    LinkedList<node> c0 = new LinkedList<node>();
    LinkedList<node> c1 = new LinkedList<node>();
    LinkedList<node> c2 = new LinkedList<node>();
    LinkedList<node> c3 = new LinkedList<node>();
    LinkedList<node> distArray = new LinkedList<node>();
    
    //LinkedList<String> tank_asig = new LinkedList<String>();
    //LinkedList<Double> distArray  = new LinkedList<Double>();
    
    
    public void print(LinkedList<node> a){
        System.out.println("ESTOY EN FUNCION PRINT: ");
        for (int i = 0; i < a.size(); i++) {
            System.out.print(" - "+a.get(i).distancia+ " & "+a.get(i).name );
        }
        System.out.println("\n PRINT DONE");
        
    }
    
    public void run(){
        //turnLeft(getHeading());
        setRadarColor(java.awt.Color.GREEN);
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

        
        
    }
    @Override
    public void onPaint(Graphics2D g) {
        // Set the paint color to red
        //g.setColor(java.awt.Color.GREEN);
        setRadarColor(java.awt.Color.GREEN);
        // Paint a filled rectangle at (50,50) at size 100x150 pixels
        //g.fillRect(50, 50, 100, 150);
    } 
    
    public double calcularAngulo(double xtank, double ytank, int indice){
        double xc, yc;
        if(indice==0){
            xc= 0.0;
            yc= 0.0;
        }else if(indice==1){
            xc= 0.0;
            yc= getBattleFieldHeight();
        }else if(indice==2){
            xc= getBattleFieldWidth();
            yc= getBattleFieldHeight();
        }else{
            xc= getBattleFieldWidth();
            yc= 0.0;
        }
        double gamma = getHeading();
        double a=xc-xtank;
        double b=yc-ytank;
        double alfa= Math.toDegrees(Math.atan2(b,a));
        double beta=-gamma-(alfa-90.0);
        
        return beta;
    }
    
    public double obtenirDistanciaEsquina(double xc, double xtank, double yc, double ytank){
        //return Math.abs(xc-xtank) + Math.abs(yc-ytank);
        return Math.sqrt((Math.pow((xc-xtank),2))+(Math.pow((yc-ytank),2)));
    }
    
    public void calculaDistancia(String CualTankSoy, double x, double y){
        //System.out.println("ESTOY EN FUNCION calculaDistancia: ");
        //System.out.println("Tanque Que entra en CualTankSoy "+CualTankSoy);
        
        double distancia_c0=obtenirDistanciaEsquina(0.0, x, 0.0, y);
        double distancia_c1=obtenirDistanciaEsquina(0.0, x, getBattleFieldHeight(), y);
        double distancia_c2=obtenirDistanciaEsquina(getBattleFieldWidth(), x, getBattleFieldHeight(), y);
        double distancia_c3=obtenirDistanciaEsquina(getBattleFieldWidth(), x, 0.0, y);
        
        node n;
        n = new node(distancia_c0, CualTankSoy);
        c0.add(n); Collections.sort(c0); //añade y ordena la lista
        n = new node(distancia_c1, CualTankSoy);
        c1.add(n); Collections.sort(c1);
        n = new node(distancia_c2, CualTankSoy);
        c2.add(n); Collections.sort(c2);
        n = new node(distancia_c3, CualTankSoy);
        c3.add(n); Collections.sort(c3);


        if((c1.size() == 5) || (c0.size() == 5) || (c2.size() == 5) || (c3.size() == 5)){
            System.out.println("Entro en Asignar cantonada");
            AsignarCantonada();
        } 
        
    }

    public void remove(LinkedList<node> a, String name){
        int index = 0;
        for(int i = 0; i < a.size(); i++) {
            if(a.get(i).name.equals(name)){
                index=i;
                //return;
                i = a.size();
            }
        }
        //System.out.print(" INDEX TO REMOVE: "+ index);
        a.remove(index); //print(c1);
    }
    
    public void enviarMensajesAsignación(){
        try {
            sendMessage(distArray.get(0).name, "DistanciaHaciaLaEsquina:"+distArray.get(0).distancia+"-"+0); 
        } catch (IOException ignored) {}

        try {
            sendMessage(distArray.get(1).name, "DistanciaHaciaLaEsquina:"+distArray.get(1).distancia+"-"+1); 
        } catch (IOException ignored) {}

        try {
            sendMessage(distArray.get(2).name, "DistanciaHaciaLaEsquina:"+distArray.get(2).distancia+"-"+2); 
        } catch (IOException ignored) {}
        try {
            sendMessage(distArray.get(3).name, "DistanciaHaciaLaEsquina:"+distArray.get(3).distancia+"-"+3); 
        } catch (IOException ignored) {}

    }

    public void AsignarCantonada(){
        //tank_asig, distArray
        node n;
        //c0
        System.out.println("---------IMPRIMIENDO LISTAS--------");
        print(c0);
        print(c1);
        print(c2);
        print(c3);
        System.out.println("-----------------------------------");
        n = c0.getFirst(); 
        distArray.add(n);
        //System.out.println("c0.getFirst() --> "+ c0.getFirst().distancia + " & "+ c0.getFirst().name);
         //System.out.print("distArray --> ");print(distArray);
        //System.out.print(" <-- FINNNN distArray  ");
        //c1.remove(n.name);
        remove(c1,n.name); 
        remove(c2,n.name);
        remove(c3,n.name);
        //c1
        n = c1.getFirst();  
        distArray.add(n);
        
        remove(c2,n.name); 
        remove(c3,n.name);
        //c2
        n = c2.getFirst();  
        distArray.add(n);
        remove(c3,n.name);
        //c3
        n = c3.getFirst();  
        distArray.add(n);
        remove(c3,n.name);
        kamikaze=c3.getFirst().name;
        
        System.out.print("distArray --> ");
        print(distArray);
        System.out.print("El kamikaze es: "+kamikaze);
        
        //DistanciaHaciaLaEsquina:
        enviarMensajesAsignación();
        
    }
    
    public void centinella(){
        try {
            System.out.println("ESPEROOOOOOOOOOOOOO");
            //wait(1000);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("TERMINE DE ESPEROOOOOOOOOOOOOO"); 
        } catch (Exception e) {
            System.out.println("EXCEPCION");
        }
        back(50);
        ahead(50);   
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
            String coordY = ssb2[1];
            X_1 = Double.parseDouble(coordX); 
            Y_1 = Double.parseDouble(coordY);
 
            if("epsevg.upc.edu.c2223.PruebaRobot* (1)".equals(sender)){

                //out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                calculaDistancia(sender, X_1, Y_1);
            }
            else if("epsevg.upc.edu.c2223.PruebaRobot* (2)".equals(sender)){
                
                //out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_1, Y_1);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (3)".equals(sender)){
                
                //out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_1, Y_1);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (4)".equals(sender)){
               
                //out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_1, Y_1);
            }else if("epsevg.upc.edu.c2223.PruebaRobot* (5)".equals(sender)){
               
                //out.println("X_1 recibido = " +X_1+ "  Y_1 recibido = " +Y_1+ "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                calculaDistancia(sender, X_1, Y_1);
            }
        
        }else if(command.equals("DistanciaHaciaLaEsquina")){
            String s = ssb1[1];
            String[] ssb2 = s.split("-");
            String stringdist = ssb2[0];
            System.out.println("Distancia llegada:"+stringdist);
            double dist = Double.parseDouble(stringdist);
            String strgindice = ssb2[1];
            int indice = Integer.parseInt(strgindice);
            
            System.out.println("Mis X:"+getX());
            System.out.println("Mis X:"+getY());
            double ang = calcularAngulo(getX(), getY(), indice);
            turnRight(ang); //Antes estaba puesto angulo restante
            ahead(dist);
            
            
            System.out.print("!!!!!!!!!!!DISPARAR!!!!!!!!!!!!!");
            if(indice == 0) turnGunRight(calcularAngulo(getX(), getY(), 2));
            else if(indice == 1) turnGunRight(calcularAngulo(getX(), getY(), 3));
            else if(indice == 2) turnGunRight(calcularAngulo(getX(), getY(), 0));
            else turnGunRight(calcularAngulo(getX(), getY(), 1));
            
            fire(5.9);
            while(true){
                centinella();
                turnRadarRight(Double.POSITIVE_INFINITY);
            }
            //System.out.print("!!!!!!!!!!!POSITIVE_INFINITY!!!!!!!!!!!!!");
            
            //turnGunRight(1000);
            
            
            //turnGunRight(ang);
            //System.out.println("Valor OnCorner:"+OnCorner);
            //OnCorner=true;
            //System.out.println("Valor OnCorner Poniendolo a true:"+OnCorner);
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