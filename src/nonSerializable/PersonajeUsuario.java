package nonSerializable;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import serializable.PosJugador;

public class PersonajeUsuario extends Thread{
public int vidas=5;
public int posLineaDerX=263;
Logica mundo;
PImage img;
PImage imgVida;
PVector posiciones;
boolean vivoHilo;
private boolean limiteDer;
private boolean limiteIz;
PVector aceleracion;
private boolean puedeDisparar;
public ArrayList <DisparoJugadorLienzo> disparos;
	public PersonajeUsuario(Logica mundo, PImage perimg, PImage viditas) {
	vivoHilo=true;
	this.mundo=mundo;
	this.img=perimg;
	this.imgVida=viditas;
	disparos= new ArrayList<>();
	posiciones= new PVector(300,340);
	aceleracion= new PVector();
	new Thread(hiloSleepDisparar()).start();
	}
	
	public void pintar(){
		
        mundo.app.pushMatrix();
        mundo.app.translate(posiciones.x, posiciones.y);
        mundo.app.rotate(PApplet.radians(-mundo.ins.angulo));
		mundo.app.image(img, 30, 0);
		mundo.app.popMatrix();
		for (int i = 0; i < disparos.size(); i++) {
			disparos.get(i).pintar();
		}
		int PosVidaRestadaX= (int) PApplet.map(vidas, 5, 0, 263, 115);
		if(posLineaDerX>PosVidaRestadaX){
			posLineaDerX--;
		}
		
		mundo.app.strokeWeight(21);
	    mundo.app.stroke(0,200,100,200);
		mundo.app.line(113,650, posLineaDerX, 650);
		 mundo.app.stroke(200,50,80,250);
		mundo.app.strokeWeight(1);
		mundo.app.image(imgVida, 140, 650);
	}
	
	public void dispararPapu(){
		if(puedeDisparar){
		disparos.add(new DisparoJugadorLienzo(this.mundo, this.posiciones, this.aceleracion,mundo.ins.angulo,1,mundo.cohete));
		puedeDisparar=false;
		}
		
	}
	
	public void run(){
	 while(vivoHilo){
		 try {
			 
			 float ymap= PApplet.map(mundo.ins.y, -400, 400, -10, 10);
			 float xmap= PApplet.map(mundo.ins.y, -400, 400, -10, 10);
			 aceleracion.y=-mundo.ins.y;
			 aceleracion.x=mundo.ins.x;
			 posiciones.y+=-ymap;
				
				limitesYmovimiento();
				 
				
				
			 
			 
			sleep(33);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
		
	}
	

	
	private void movimiento(){
		
	}
	
	private Runnable hiloSleepDisparar(){
		Runnable r= new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try{
				puedeDisparar=true;
				Thread.sleep(500);
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
				
			}
		};
		
		return r;
	}
	
	private void limitesYmovimiento(){
		
			
			
		 if((posiciones.x>=mundo.app.width-130 && mundo.posMap.x>55) ) {
			
			//mover la posicion del mapa
			mundo.posMap.x-=5;
			
		
			
		} else if((posiciones.x<=145  && mundo.posMap.x<851)){
			mundo.posMap.x+=5;
			
		}
		float xmap= PApplet.map(mundo.ins.x, -400, 400, -10, 10);
		if(mundo.posMap.x-xmap>50 && xmap>0){
			 posiciones.x+=xmap;
			 limiteDer=false;
			 
			
		}else{
			limiteDer=true;
		}
		if (limiteDer && xmap>0){
			if (posiciones.x+xmap<mundo.app.width-130) {
				 posiciones.x+=xmap;
			}
		}
		
		
		
		if(mundo.posMap.x-xmap<841 && xmap<0){
			 posiciones.x+=xmap;
			 limiteIz=true;
			 
			
		}else{
			limiteIz=false;
		}
		if (!limiteIz && xmap<0){
			if (posiciones.x+xmap>50) {
				 posiciones.x+=xmap;
			}
		}
		
	
		
		
		
		if(posiciones.y>70){
			 if(mundo.app.keyCode==PApplet.UP){
				 posiciones.y-=5;
			 }
		} else if(posiciones.y<=70 && mundo.posMap.y<=740){
			mundo.posMap.y+=5;
		}
		
		if(posiciones.y<mundo.app.height-70){
			 if(mundo.app.keyCode==PApplet.DOWN){
				 posiciones.y+=5;
			 }
		}else if(posiciones.y>=mundo.app.height-70 && mundo.posMap.y>-50){
			mundo.posMap.y-=5;
		}
		
		
		
	}
	
	
	

}
