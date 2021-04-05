package fr.nayeone.deacoudre.deacoudregame.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PoolRegion {

	private Location pos1;
	private Location pos2;

	 public PoolRegion() {

	 }

	 public PoolRegion(Location pos1, Location pos2) {
	 	this.pos1 = pos1;
	 	this.pos2 = pos2;
	 }

	 public boolean hasWaterInside() {
		 List<Location> poolLocations = new ArrayList<>();
		 World world = this.pos1.getWorld();
	 	int pos1X = this.pos1.getBlockX();
	 	int pos1Y = this.pos1.getBlockY();
	 	int pos1Z = this.pos1.getBlockZ();
	 	int pos2X = this.pos2.getBlockX();
	 	int pos2Y = this.pos2.getBlockY();
	 	int pos2Z = this.pos2.getBlockZ();
	 	int y = pos2Y - pos1Y;
	 	while (true) {
			poolLocations.add(new Location(world, pos1X, pos1Y + y, pos1Z));
	 		int x = pos2X - pos1X;
	 		while (true) {
				poolLocations.add(new Location(world, pos1X + x, pos1Y + y, pos1Z));
	 			int z = pos2Z - pos1Z;
	 			while (true) {
					poolLocations.add(new Location(world, pos1X + x, pos1Y + y, pos1Z + z));
	 				if (z < 0) {
	 					z++;
					} else if (z == 0) {
	 					break;
					} else {
	 					z--;
					}
				}
	 			if (x < 0) {
	 				x++;
				} else if (x == 0) {
					break;
				} else {
	 				x--;
				}
			}
	 		if (y < 0) {
	 			y++;
			} else if (y == 0) {
				break;
			} else {
	 			y--;
			}
		}
	 	for (Location loc : poolLocations) {
	 		if (loc.getBlock().getType().equals(Material.WATER)) {
	 			return true;
			}
		}
	 	return false;
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
