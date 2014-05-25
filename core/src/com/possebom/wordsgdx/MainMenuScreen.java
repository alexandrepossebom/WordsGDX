package com.possebom.wordsgdx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainMenuScreen extends WordsScreen {
    private final static int WIDTH = 1920;
    private final static int HEIGHT = 1080;
    private Stage stage;

    public MainMenuScreen(final WordsGDX game) {
        final I18NBundle myBundle = game.getMyBundle();
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        final Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final Label welcomeLabel = new Label(myBundle.get("appname"), skin);

        final Image flag = getImage(game.getMyBundle());
        final Image close = new Image(new TextureRegion(new Texture("data/Close.png")));
        close.setScale(0.5f, 0.5f);
        flag.setScale(0.7f, 0.7f);

        stage.addActor(flag);
        stage.addActor(close);

        stage.addActor(welcomeLabel);

        welcomeLabel.setFontScale(2.0f);

        welcomeLabel.setPosition(WIDTH / 2 - welcomeLabel.getTextBounds().width / 2, HEIGHT - 40 - welcomeLabel.getTextBounds().height);

        flag.setPosition(20, 10);

        close.setPosition(WIDTH - close.getImageWidth() - 148, 25);


        final TextButton buttonNew = new TextButton(myBundle.get("newgame"), skinButton);
        final TextButton buttonScore = new TextButton(myBundle.get("records"), skinButton);
        table.add(buttonNew).size(875, 200);
        table.row().space(75);
        table.add(buttonScore).size(875, 200);


        buttonScore.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(WordsGDX.SCORE_BOARD);
            }
        });


        buttonNew.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(WordsGDX.MAIN_GAME);
            }
        });

        flag.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.changeLanguage();
                game.setScreen(WordsGDX.MAIN_MENU);
            }
        });

        close.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private Image getImage(final I18NBundle myBundle) {
        String flag = "flags/USA.png";
        final String lang = myBundle.get("lang");
        if (lang.equals("EN")) {
            flag = "flags/BRA.png";
        }
        return new Image(new TextureRegion(new Texture(flag)));
    }

}
