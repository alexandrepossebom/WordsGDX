package com.possebom.wordsgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public abstract class WordsScreen implements Screen {
    protected final String TAG = "WordsGDX";
    protected Skin skin;
    protected Skin skinButton;
    protected Skin skinScore;
    protected BitmapFont font;
    protected BitmapFont fontButton;
    protected BitmapFont fontScore;

    public WordsScreen() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/MICKEY.ttf"));
        font = generator.generateFont(parameter);
        parameter.size = 128;
        fontButton = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/LiberationMono.ttf"));
        parameter.size = 60;
        fontScore = generator.generateFont(parameter);
        generator.dispose();


        skin = new Skin();
        skinButton = new Skin();
        skinScore = new Skin();



        Pixmap pixmap = new Pixmap(4, 4, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));
        skin.add("default", font);


        skinScore.add("default", fontScore);
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = skinScore.getFont("default");
        skinScore.add("default", labelStyle);


        TextButtonStyle textButtonStyle = new TextButtonStyle();
        skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = new NinePatchDrawable(processNinePatchFile("data/btn_white_matte.9.png"));
        textButtonStyle.up = new NinePatchDrawable(processNinePatchFile("data/btn_green_matte.9.png"));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        labelStyle = new LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);


        skinButton.add("white", new Texture(pixmap));
        skinButton.add("default", fontButton);

        TextButtonStyle textButtonStyleButton = new TextButtonStyle();
        skinButton.newDrawable("white", Color.DARK_GRAY);
        textButtonStyleButton.down = new NinePatchDrawable(processNinePatchFile("data/btn_white_matte.9.png"));
        textButtonStyleButton.up = new NinePatchDrawable(processNinePatchFile("data/btn_green_matte.9.png"));
        textButtonStyleButton.font = skinButton.getFont("default");
        skinButton.add("default", textButtonStyleButton);

        LabelStyle labelStyleButton = new LabelStyle();
        labelStyleButton.font = skinButton.getFont("default");
        skinButton.add("default", labelStyleButton);
    }

    private static NinePatch processNinePatchFile(final String fileName) {
        final Texture texture = new Texture(Gdx.files.internal(fileName));
        final int width = texture.getWidth() - 2;
        final int height = texture.getHeight() - 2;
        return new NinePatch(new TextureRegion(texture, 1, 1, width, height), 3, 3, 3, 3);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }


}
