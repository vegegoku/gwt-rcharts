package com.rcharts.client;

import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;

public class SingletonSpeechBubble extends SpeechBubble {
	
	
	@Override
	public void drawBubble(){
		RaphaelObject speechSet = getSpeechSet();
		BBox sbox = speechSet.getBBox();
		Point tx = getTranslationPoint(sbox);
		speechSet.translate(tx.getX(), tx.getY());
	}

	/* (non-Javadoc)
	 * @see com.google.chart_gwt.client.SpeechBubble#getTranslationPoint(com.hydro4ge.raphaelgwt.client.BBox)
	 */
	@Override
	protected Point getTranslationPoint(BBox sbox) {
		double sx = 0;
		double sy = 0;
		Position pos = getPos();
		int stick = getStick();
		if(pos == Position.NORTH  || pos == Position.SOUTH){
			sx = anchor.getX() - sbox.width()/2;			
			if(pos == Position.NORTH){
				stick = -stick;
				sy = anchor.getY() - sbox.height();
				
			}
			else if(pos == Position.SOUTH){
				sy = anchor.getY();
			}
			sy = sy + stick;
		}
		else{
			sy = anchor.getY() - sbox.height()/2;
			if(pos == Position.WEST){
				stick = -stick;
				sx = anchor.getX() - sbox.width();			
			}
			else if(pos == Position.EAST){
				sx = anchor.getX();
			}
			sx = sx + stick;
		}
		double dx = sx - sbox.x();
		double dy = sy - sbox.y();

		return new Point(dx, dy);
	}
	
	
}
