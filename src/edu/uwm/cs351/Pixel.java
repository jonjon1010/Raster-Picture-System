/*
 * CST 751 Homework 1
 * Name: Jonathan Nguyen 
 * I got help with Natasha the TA at her office hours with writing the hashcode method
 * for the Pixel.java. She expain that for my test29 to pass I had to drop the 3 least significant bits for my values for red, green, and blue. I
 * I did this by using >>3, when getting my values for red, blue, and green. After doing this my hashcode method passed successfully for test29. 
 * 
 * */

package edu.uwm.cs351;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;


/**
 * A single pixel
 * <dl>
 * <dt>loc<dd> an immutable field holding a Point representing the Pixel's location, cannot be null
 * <dt>color<dd> the pixel's color, cannot be null
 * </dl>
 */
public class Pixel {
	private Color color;
	private final Point loc;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	
	//TODO Finish the constructors for the Pixel objects
	//make sure you throw IllegalArgumentExceptions where appropriate
	// if no Color is given, use the default color
	/**
	 * Create a pixel by specifying two coordinates
	 * @param x
	 * @param y
	 */
	public Pixel(int x, int y) {
		// TODO: very little needed
		this(x,y,DEFAULT_COLOR);
	}

	/**
	 * Create a pixel by specifying two coordinates and a color
	 * @param x
	 * @param y
	 * @param color
	 */
	public Pixel(int x, int y, Color color) {
		// TODO: very little needed
		this(new Point(x,y),color);
	}
	
	/**
	 * Create a pixel by specifying a point
	 * @param loc
	 */
	public Pixel(Point loc) {
		// TODO: very little needed
		if(loc == null)throw new IllegalArgumentException("can't have coordinates for a pixel be null");
		this.loc = loc;
		this.color = DEFAULT_COLOR; 
	}
	
	//Recommendation: use the following constructor in the other constructors
	/**
	 * Create a pixel by specifying a point and a color
	 * @param loc
	 * @param color
	 */
	public Pixel(Point loc, Color color){
		// TODO: very little needed. don't forget exceptions
			if(color == null || loc == null) throw new IllegalArgumentException("can't have coordinates and/or color be null for a pixel");
			this.loc = loc;
			this.color = color;
	}
	
	/// Overrides
	// no need to give a documentation comment if overridden documentation still is valid.
	
	@Override
	public boolean equals(Object o) {
		// TODO
		if(!(o instanceof Pixel))
			return false;
		Pixel p = (Pixel)o;
		return loc.x == p.loc.x && loc.y == p.loc.y && p.color.equals(color);
	}
	
	@Override
	public int hashCode() {
		int red = color.getRed() >> 3;
		int green = color.getGreen() >> 3 ;
		int blue = color.getBlue() >> 3;
		int lesser =  loc.y ^ (loc.x << 8);
		return lesser ^ (red << 26) ^ (green << 21) ^ (blue << 16);
		
		// TODO: Return some combination of x and y that distinguishes similar coordinates
		// We included an unfinished implementation. It's not enough just to
		// use the r g b values from the Color directly. What does the handout
		// say to do?
	}
	

	@Override
	public String toString() {
		// TODO: Return a string of the form <3,2,red>,
		// assuming "red" might be the return from a Color's toString method
		return "<" + loc.x + "," + loc.y + "," + color + ">";
	}
	
	
	/// three simple methods
	/** Return the Point location, but a copy, so it can't be changed
	 * @return location of the pixel
	 * 
	 */
	public Point loc()
	{
		return new Point(loc.x,loc.y);
	}
	
	/** Return the Java Color value of the pixel
	 * Color is immutable, so we don't have to worry about it being changed
	 * @return the color value
	 */
	public Color color()
	{
		return color;
	}
	
	/** Set the Java Color value of the pixel
	 * Don't allow setting to null
	 * @param color
	 */
	public void setColor(Color color)
	{
		if(color == null)throw new IllegalArgumentException("can't set a color to be null for a pixel");
		this.color = color; 
	}

	/**
	 * Invert the r,g,b values of the pixel
	 */
	public void invert()
	{
		//You can invert a color by subtracting each of its red, green, and blue components from 255.
	    int r = 255 - color.getRed();
	    int g = 255 - color.getGreen();
	    int b = 255 - color.getBlue();
	    this.color = new Color(r,g,b);
	}

	
	/**
	 * Create a polygon (for rendering in AWT) for the pixel based on the loc field
	 * @param width of the pixel
	 * @return polygon for the pixel
	 */
	public Polygon toPolygon(int width) {
		Point[] ps = {
				new Point(loc.x*width,loc.y*width),
				new Point(loc.x*width + width,loc.y*width),
				new Point(loc.x*width + width,loc.y*width + width),
				new Point(loc.x*width,loc.y*width + width)			
		};
		Polygon result = new Polygon();
		for (Point p : ps)
			result.addPoint(p.x,p.y);
		
		return result;
	}
	
	/**
	 * Render the tile in a graphics context.
	 * fill the Pixel with its color
	 * @param g context to use.
	 */
	public void draw(Graphics g, int width) {
		Polygon square = toPolygon(width);
		g.setColor(color);
		g.fillPolygon(square);
	}
	
}
