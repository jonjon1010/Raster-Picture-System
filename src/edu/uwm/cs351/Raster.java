/*
 * CST 751 Homework 1
 * Name: Jonathan Nguyen 
 *
 * I got help with Dan a Tutor from Tutoring center with writing the equals method 
 * for the Raster.java. He checked why my work and saw that I was passing the tests for 
 * the equals method except test17. He told a look at my pixel equals method and saw that I
 * had used p.color == color instead p.color.equals(color). After doing this I passed test17.
 * Also, I got help from Natasha the TA at her office hours with writing the getPixel method. She 
 * told me that there could be problems with my method, if I had not thrown an exception. By using 
 * her feedback, I was able to throw the correct exceptions to pass all my test for this method.
 * For the draw method, I was about to get this method to work from Henry guidance on piazza 
 * 
 * */


package edu.uwm.cs351;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Arrays;

public class Raster {
	private Pixel[][] pixels;
	private final int x;
	private final int y;
	
	// Construct an x by y raster
	// It should be filled with white (default) pixels
	public Raster(int x, int y) {
		//TODO
		if(x < 0 || y < 0) throw new IllegalArgumentException("can't have negative rows and/or columns");
		this.x = x;
		this.y = y;
		pixels = new Pixel[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				pixels[i][j] = new Pixel(i,j,Color.WHITE);
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		//TODO
		if(!(o instanceof Raster)) {
			return false;
		}
		Raster r = (Raster)o;
		boolean check = true;
		for(int i = 0; i < x; i++) {
			if(check == false) {
				break;
			}
			for(int j = 0; j < y; j++) {
				if(r.getPixel(i,j).equals(this.getPixel(i,j))) {
					check = true;
				}
				else{
					check = false;
					break;
				}
			}
		}
		return r.x == x && r.y == y && check;
	}
	
	@Override
	public String toString() {
		//TODO return a String representation of the raster
		String temp = "";
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				temp = temp +  " " + new Pixel(i,j).toString();	
			}
		}
		return  x + " by " + y + " raster:" + temp;
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(pixels);
	}
	
	/** Add a Pixel to the raster
	 * Don't allow adding a null
	 * @param p
	 */
	public boolean addPixel(Pixel p) {
		if(p == null)throw new IllegalArgumentException("can't add a null pixel to raster"); 
		if((x <= 1 && y == 0) || (x == 0 && y <= 1))throw new IllegalArgumentException("cooridnate is out of bound"); 
		if(p.loc().x == x || p.loc().y == y || p.loc().x < 0 || p.loc().y < 0)throw new IllegalArgumentException("coordinate is out of bound");
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if (getPixel(i,j).loc().equals(p.loc())) {
					if(!getPixel(i,j).color().equals(p.color())) {
						pixels[i][j] = p;
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	/** Get a pixel from the raster
	 * @return the pixel at x,y
	 */
	public Pixel getPixel(int x, int y) {
		//TODO return the pixel at x, y
		//Throw IllegalArgumentException if necessary
		if(x < 0 || x >= this. x || y < 0 || y >= this.y)throw new IllegalArgumentException("coordinate is out of bound");
		return pixels[x][y];
	}
	
	/** Draw the raster using a graphics context and a width for pixels
	 * @param g
	 * @param width
	 */
	public void draw(Graphics g, int width) {
		//TODO have all the pixels draw themselves
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				Polygon square = pixels[i][j].toPolygon(width);
				g.setColor(pixels[i][j].color());
				g.fillPolygon(square);
			}
		}
	}
}
