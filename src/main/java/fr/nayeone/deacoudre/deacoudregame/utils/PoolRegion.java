package fr.nayeone.deacoudre.deacoudregame.utils;

import org.bukkit.Location;

public class PoolRegion {

	private Location pos1;
	private Location pos2;

	 public PoolRegion() {

	 }

	 public PoolRegion(Location pos1, Location pos2) {
	 	this.pos1 = pos1;
	 	this.pos2 = pos2;
	 }

	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}

	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}

	public Location getPos1() {
		return pos1;
	}

	public Location getPos2() {
		return pos2;
	}
}
