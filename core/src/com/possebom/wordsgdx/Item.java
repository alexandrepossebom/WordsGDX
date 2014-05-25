package com.possebom.wordsgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Item extends Image {
    final MainGame game;
    private String name;

    public Item(final MainGame game, String name) {
        super(new TextureRegion(new Texture("data/" + name + ".png")));
        this.game = game;
        this.name = name;

        addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.touchedItem(Item.this);
            }
        });
    }

    public String getName() {
        return name;
    }

}
