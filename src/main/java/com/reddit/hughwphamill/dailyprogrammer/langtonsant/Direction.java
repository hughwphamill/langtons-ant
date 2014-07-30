package com.reddit.hughwphamill.dailyprogrammer.langtonsant;

public enum Direction {
	
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public Direction ccw() {
		switch(this) {
		case NORTH : return WEST;
		case SOUTH : return EAST;
		case EAST : return NORTH;
		case WEST : return SOUTH;
		default : return NORTH;
		}
	}
	
	public Direction cw() {
		switch(this) {
		case NORTH : return EAST;
		case SOUTH : return WEST;
		case EAST : return SOUTH;
		case WEST : return NORTH;
		default : return NORTH;
		}
	}
	
	public int moveX() {
		switch(this) {
		case NORTH : return 0;
		case SOUTH : return 0;
		case EAST : return 1;
		case WEST : return -1;
		default : return 0;
		}
	}
	
	public int moveY() {
		switch(this) {
		case NORTH : return -1;
		case SOUTH : return 1;
		case EAST : return 0;
		case WEST : return 0;
		default : return 0;
		}
	}

}
