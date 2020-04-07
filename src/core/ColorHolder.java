/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;
import java.awt.Color;


public class ColorHolder {

	private float x1;
	private float y1;
	private Color color1;
	private float x2;
	private float y2;
	private Color color2;
	
	public ColorHolder(float value1, float value2, Color col1, float value3, float value4, Color col2){
		x1 = value1;
		y1 = value2;
		color1 = col1;
		x2 = value3;
		y2 = value4;
		color2 = col2;
	}
	
	public float getX1(){
		return x1;
	}
	public float getY1(){
		return y1;
	}
	public Color getColor1(){
		return color1;
	}
	public float getX2(){
		return x2;
	}
	public float getY2(){
		return y2;
	}
	public Color getColor2(){
		return color2;
	}
}
