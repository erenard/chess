package com.game.chess.model;

import com.game.model.Color;



public class Order {
	public enum OrderType {
		MOVE, PROMOTE, REMOVE;
	}
	private int srcX;
	private int srcY;
	private int trgX;
	private int trgY;
	private String stringOrder;
	private boolean valid;
	private Order subOrder;
	private OrderType type;
	private PieceType promotionType;
	private Color pieceColor;
	private PieceType pieceType;

	public Order(String input) {
		input = input.toLowerCase();
		stringOrder = input.toUpperCase();
		srcX = input.charAt(0) - 'a';
		srcY = input.charAt(1) - '1';
		trgX = input.charAt(3) - 'a';
		trgY = input.charAt(4) - '1';
		valid = true;
		subOrder = null;
		type = OrderType.MOVE;
	}

	public int getSrcX() {
		return srcX;
	}

	public void setSrcX(int srcX) {
		this.srcX = srcX;
	}

	public int getSrcY() {
		return srcY;
	}

	public void setSrcY(int srcY) {
		this.srcY = srcY;
	}

	public int getTrgX() {
		return trgX;
	}

	public void setTrgX(int trgX) {
		this.trgX = trgX;
	}

	public int getTrgY() {
		return trgY;
	}

	public void setTrgY(int trgY) {
		this.trgY = trgY;
	}

	public String getStringOrder() {
		return stringOrder;
	}

	public void setStringOrder(String order) {
		this.stringOrder = order;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean hasSubOrder() {
		return subOrder != null;
	}

	public Order getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(Order order) {
		this.subOrder = order;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public PieceType getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(PieceType pieceType) {
		this.promotionType = pieceType;
	}
	
	public void setPromotionType(String pieceType) {
		if(pieceType.equals("Q")) {
			setPromotionType(PieceType.QUEEN);
		} else if(pieceType.equals("B")) {
			setPromotionType(PieceType.BISHOP);
		} else if(pieceType.equals("H")) {
			setPromotionType(PieceType.KNIGHT);
		} else if(pieceType.equals("R")) {
			setPromotionType(PieceType.ROOK);
		}
	}

	public Color getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}
}
