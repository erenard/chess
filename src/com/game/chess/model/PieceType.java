package com.game.chess.model;

public enum PieceType {
	KING("k", "king"),
	QUEEN("q", "queen"),
	BISHOP("b", "bishop"),
	KNIGHT("h", "knight"),
	ROOK("r", "rook"),
	PAWN("p", "pawn");
	
	private String shortName;
	private String longName;
	private PieceType(String shortName, String longName) {
		this.shortName = shortName;
		this.longName = longName;
	}
	public String getShortName() {
		return shortName;
	}
	public String getLongName() {
		return longName;
	}
	public static PieceType getByShortName(String shortName) {
		shortName = shortName.toLowerCase();
		if(shortName.equals(KING.shortName)) {
			return KING;
		} else if(shortName.equals(QUEEN.shortName)) {
			return QUEEN;
		} else if(shortName.equals(BISHOP.shortName)) {
			return BISHOP;
		} else if(shortName.equals(KNIGHT.shortName)) {
			return KNIGHT;
		} else if(shortName.equals(ROOK.shortName)) {
			return ROOK;
		} else {
			return PAWN;
		}		
	}
}
