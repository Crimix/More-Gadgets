package com.black_dog20.moregadgets.item.gadgets;

public class Location {
	
	private String name;
	private int dim;
	private int x;
	private int y;
	private int z;

	public Location(String name, int dim, int x, int y, int z) {
		this.name = name;
		this.dim = dim;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getName() {
		return name;
	}
	
	public void teleport() {
		System.out.println("tp " + name);
	}
}