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
import java.util.*;
import java.util.Iterator;
import java.util.List;
//import robocode.util;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;

/**
 *
 * @author UX431F
 */

public class CornerTeamRobot extends TeamRobot{
    
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
    private static String kamikaze;
   
    
    static Double X_TankPos, Y_TankPos;
    static Boolean OnCorner=false;
    
    LinkedList<node> c0 = new LinkedList<node>();
    LinkedList<node> c1 = new LinkedList<node>();
    LinkedList<node> c2 = new LinkedList<node>();
    LinkedList<node> c3 = new LinkedList<node>();
    LinkedList<node> distArray = new LinkedList<node>();
    
    LinkedList<node> enemigos = new LinkedList<node>();
    static node enemigo_cercano;
    static boolean encontrado = false, estoy_muerto = false, he_llegado=false; 
    static long time_initi, time_execute;
    static int cantonada_asignada;
 


    /**
     *
     * @param event
     */

    public void print(LinkedList<node> a){
        for (int i = 0; i < a.size(); i++) {
            System.out.print(" - "+a.get(i).distancia+ " & "+a.get(i).name );
        }        
    }
    
    @Override
    public void onHitRobot(HitRobotEvent event) {
       if (event.getBearing() > -90 && event.getBearing() <= 90) {
           back(30);
       } else {
           ahead(30);
       }
    }


    @Override
    public void onHitWall(HitWallEvent event) {
       out.println("Ouch, I hit a wall bearing " + event.getBearing() + " degrees.");
       ahead(100);
       back(100);
   }
    
    public void run(){
        setRadarColor(java.awt.Color.GREEN);
        double x_t = getX(), y_t = getY();
        
        if(!"epsevg.upc.edu.c2223.CornerTeamRobot* (3)".equals(getName())){
            try {
               sendMessage("epsevg.upc.edu.c2223.CornerTeamRobot* (3)", "EnvioCoords:"+x_t+","+y_t); 
            } catch (IOException ignored) {}
    
        }else{
            calculaDistancia("epsevg.upc.edu.c2223.CornerTeamRobot* (3)", x_t, y_t);
            
        }
        
        
        turnRadarRight(360);//turnGunRight(360);
        turnRadarLeft(360);//turnGunLeft(360);
        print(enemigos);
        
        enemigo_cercano = new node(enemigos.get(0).distancia, enemigos.get(0).name);
        
        matar_enemigo();
    }

    public void matar_enemigo(){
        print(enemigos); 
        
        while(!encontrado){
            turnRadarRight(5); 
        }
       
        if(encontrado){
            double r_heading=getRadarHeading(), g_heading = getGunHeading(), heading = getHeading();
            if(kamikaze.equals(getName())){
                
                if(heading <= 270){
                    if(r_heading > heading) turnRight(r_heading- heading);
                    else turnLeft(heading - r_heading);
                }
                else {  //headin > 270
                    if(r_heading>270){
                        if(r_heading > heading) turnRight(r_heading- heading);
                        else turnLeft(heading - r_heading);
                    }
                    else{
                        turnLeft(heading-r_heading);
                    }
                }
                
                ahead(enemigo_cercano.distancia-30.0);
                
                g_heading = getGunHeading();
                r_heading=getRadarHeading();
            }else{
            
                if(r_heading <= g_heading) turnGunLeft(g_heading-r_heading); 
                else if(r_heading>g_heading)turnGunRight(r_heading-g_heading);
            }
            
            while(!estoy_muerto){
                dispara(enemigo_cercano.distancia);
            }
        }
        encontrado = false;
        
        if(!enemigos.isEmpty())matar_enemigo();
    }

    public void dispara(double distancia_enemigo){
        if(distancia_enemigo >= 200) fire(1);
        else if(distancia_enemigo >= 50) fire(2);
        else fire(3);
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
        System.out.println("Gamma: "+gamma);
        double a=xc-xtank;
        double b=yc-ytank;
        double alfa= Math.toDegrees(Math.atan2(b,a));
        System.out.println("Alfa: "+alfa);
        double beta=-gamma-(alfa-90.0);
        System.out.println("Beta: "+beta);
        
        return beta;
    }
    
    public double obtenirDistanciaEsquina(double xc, double xtank, double yc, double ytank){
        return Math.sqrt((Math.pow((xc-xtank),2))+(Math.pow((yc-ytank),2)));
    }
    
    public void calculaDistancia(String CualTankSoy, double x, double y){
        
        double margen = 75.0;
        double distancia_c0=obtenirDistanciaEsquina(0.0+margen, x, 0.0+margen, y);
        double distancia_c1=obtenirDistanciaEsquina(0.0+margen, x, getBattleFieldHeight()-margen, y);
        double distancia_c2=obtenirDistanciaEsquina(getBattleFieldWidth()-margen, x, getBattleFieldHeight()-margen, y);
        double distancia_c3=obtenirDistanciaEsquina(getBattleFieldWidth()-margen, x, 0.0+margen, y);
        
        node n;
        n = new node(distancia_c0, CualTankSoy);
        c0.add(n); 
        Collections.sort(c0); //añade y ordena la lista
        n = new node(distancia_c1, CualTankSoy);
        c1.add(n); 
        Collections.sort(c1);
        n = new node(distancia_c2, CualTankSoy);
        c2.add(n); 
        Collections.sort(c2);
        n = new node(distancia_c3, CualTankSoy);
        c3.add(n); 
        Collections.sort(c3);


        if((c1.size() == 5) || (c0.size() == 5) || (c2.size() == 5) || (c3.size() == 5)){
            AsignarCantonada();
        } 
        
    }

    public void remove(LinkedList<node> a, String name){
        for(int i = 0; i < a.size(); i++) {
            if(a.get(i).name.equals(name)){
                a.remove(i);
                i = a.size();
            }
        }

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
        n = c0.getFirst(); 
        distArray.add(n);
       
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
        System.out.println("El kamikaze es: "+kamikaze);
        try{
            broadcastMessage("kamikaze:"+kamikaze);
        }catch(IOException ignored){}
        
        
        enviarMensajesAsignación();
        
    }
    
    public void centinella(int esquina) {
        double heading = getHeading();
        
        try {
            if ((heading <= 90.0) && (heading >= 0.0)) { // cuadrante I
                if ((esquina == 0) || (esquina == 1))
                    turnRight(90.0 - heading);
                else
                    turnLeft(90.0 + heading);
            } else if (((heading > 90.0) && (heading <= 180.0)) || ((heading > 180.0) && (heading <= 270.0))) { // cuadrante II || cuadrante III
                if ((esquina == 0) || (esquina == 1))
                    turnLeft(heading - 90.0);
                else
                    turnRight(270.0 - heading);
            } else if ((heading > 270.0) && (heading <= 360.0)) { // cuadrante IV
                if ((esquina == 0) || (esquina == 1))
                    turnRight((360.0 - heading) + 90.0);
                else
                    turnLeft(heading - 270.0);
            }
            for(int i = 0; i < 3; i++){
                ahead(100);
                back(100);
            }
        } catch (Exception e) {
            System.out.println("EXCEPCION");
        }

    }

    public void girar(double angulo){
        if(angulo <=-180.0){
            angulo = 360+angulo;
        }else if(angulo>=180.0){
            angulo = 360-angulo;
        }
        System.out.println("Angulo convertido: "+angulo);
        turnRight(angulo);
        
    }
    
    
    public void onMessageReceived(MessageEvent event) {
        out.println(event.getSender() + " sent me: " + event.getMessage()); // + "  soy: "+event.getMessage().getClass()
        String mssg = (event.getMessage()).toString();
        String sender = (event.getSender());
        String[] ssb1 = mssg.split(":");
        String command = ssb1[0];
        
        
        System.out.println("Quien me envia:"+sender);
        
        if(command.equals("EnvioCoords")){
            String comvalue = ssb1[1];
            String[] ssb2 = comvalue.split(",");
            String coordX = ssb2[0];
            String coordY = ssb2[1];
            X_TankPos = Double.parseDouble(coordX); 
            Y_TankPos = Double.parseDouble(coordY);
            calculaDistancia(sender, X_TankPos, Y_TankPos);
            
        }else if(command.equals("DistanciaHaciaLaEsquina")){
            String s = ssb1[1];
            String[] ssb2 = s.split("-");
            String stringdist = ssb2[0];
            System.out.println("Distancia llegada:"+stringdist);
            double dist = Double.parseDouble(stringdist);
            String strgindice = ssb2[1];
            cantonada_asignada = Integer.parseInt(strgindice);
            
            double ang = calcularAngulo(getX(), getY(), cantonada_asignada);
            
            girar(ang);
            ahead(dist);
            
            he_llegado = true;
            
            centinella(cantonada_asignada);
            
        }
        else if(command.equals("kamikaze")){
            kamikaze = ssb1[1];
           
        }
        
   }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent e){
        
        String name = e.getName();
        System.out.println("Nombre del robot que detecto: "+name);
        
        if(! isTeammate(name)){
            double distancia_enemigo = e.getDistance();
            System.out.println("ESTOY A ESTA DISTANCIA "+ distancia_enemigo +"DEL ENEMIGO: "+ name);
            node n = new node(distancia_enemigo, name);
            
            boolean find = false;
            int i = 0;
            while(!find && i < enemigos.size()){
                if(enemigos.get(i).name.equals(name)) find = true;
                else if((enemigos.get(i).name.equals(name)) && (enemigos.get(i).distancia != distancia_enemigo)){
                    enemigos.get(i).distancia = distancia_enemigo;
                    Collections.sort(enemigos);
                    find = true;
                }
                i++;
            }
            if(!find){
                enemigos.add(n);
                Collections.sort(enemigos);
            }
        }
        
        if((enemigo_cercano != null) && (enemigo_cercano.name).equals(e.getName())){
            encontrado = true;
        }
    }
    
    @Override
    public void onRobotDeath(RobotDeathEvent e){
        String name = e.getName();
        System.out.println("Robot MUERTOOOOOOOOOOOO: " + name);
        remove(enemigos, name); //enemigos enemigo_cercano;
        print(enemigos);
        if(!enemigos.isEmpty()){
            if(enemigo_cercano.name == name){
                enemigo_cercano = enemigos.get(0);
                encontrado = false;
                estoy_muerto = true;                
            }
            else{
                estoy_muerto = false;
                encontrado = false;
            }
        }else stop();
    }

    static int status = 0;
    @Override
    public void onHitByBullet(HitByBulletEvent e){
        if(status == 0){
            back(100);
            status = 1;
        }
        else if(status == 1){
            ahead(100);
            status = 0;
        }
    }        
}