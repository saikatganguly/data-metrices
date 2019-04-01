package com.barclays.metrics.charts;

public class PieceOfPie {

	private String name;
	private int value;
	
	public PieceOfPie(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
