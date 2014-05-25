package com.possebom.wordsgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;

public class ActionColor extends Action{
	private Item item;
	private Color color;
	
	public ActionColor(Item item) {
		this.item = item;
		color = Color.RED;
	}
	
	public ActionColor(Item item, Color color) {
		this.item = item;
		this.color = color;
	}
	
	@Override
	public boolean act(float delta) {
		item.setColor(color.r, color.g, color.b, 0);
		return true;
	}

}
