package com.possebom.wordsgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainGame extends WordsScreen {
    private final static int WIDTH = 1920;
    private final static int HEIGHT = 1080;
    private static final int SPRITE_SIZE = 256;
    private static final float yTop = HEIGHT / 2 + (HEIGHT / 4 - SPRITE_SIZE / 2);
    private static final float yBottom = (HEIGHT / 4) - (SPRITE_SIZE / 2);
    private static final float xLeft = (WIDTH / 4) - (SPRITE_SIZE / 2);
    private static final float xRight = WIDTH / 2 + (WIDTH / 4 - SPRITE_SIZE / 2);
    private final Label timeLabel = new Label("", skin);
    private final Label scoreLabel = new Label("0 / 0", skin);
    private String stringTime;
    private WordsGDX game;
    private Stage stage;
    private Item item[] = new Item[4];
    private TextButton button;
    private Database database;
    private long time = 120;
    private long start;

    public MainGame(final WordsGDX game) {
        this.game = game;

        stringTime = game.getMyBundle().get("time");
        database = new Database(game.getMyBundle());


        Score.clear();

        start = System.currentTimeMillis();

        stage = new Stage(new FitViewport(WIDTH, HEIGHT)) {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    game.setScreen(WordsGDX.MAIN_MENU);
                }
                return super.keyDown(keyCode);
            }
        };
        showStage();
    }

    @Override
    public void render(final float delta) {
        long elapsed = (System.currentTimeMillis() - start) / 1000;
        long rest = (time - elapsed);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);

        Gdx.input.setCatchBackKey(true);

        timeLabel.setText(stringTime + " :  " + rest);

        if (rest == 0) {
            endGame();
        }
    }

    public void endGame() {
        game.setScreen(WordsGDX.SCORE_BOARD);
    }

    public void touchedItem(final Item touchedItem) {
        if (touchedItem.getName().equalsIgnoreCase(database.getSortedKey())) {
            Score.hit();
            button.setVisible(true);
            showStage();
        } else {
            touchedItem.addAction(Actions.sequence(Actions.fadeOut(0.25f), new ActionColor(touchedItem), Actions.delay(0.15f), Actions.fadeIn(0.75f)));
            Score.miss();
        }

        scoreLabel.setText(Score.getHit() + " / " + Score.getMiss());
        scoreLabel.setPosition(WIDTH - scoreLabel.getTextBounds().width - 20, 30);

//        Gdx.app.log("MyWords", "Touched in Item  :  " + touchedItem.getName() + " Sorted is : " + database.getSortedKey());
    }

    private void showStage() {

        if (database.size() == 1) {
            game.setScreen(WordsGDX.SCORE_BOARD);
        }
//        Gdx.app.log("MyWords", "Total : " + database.getTotal() + " left : " + database.size());

        final String sorted[] = database.getItems();
        stage.clear();

        button = new TextButton(database.getSortedName(), skinButton);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                button.addAction(Actions.sequence(Actions.fadeOut(0.5f), new Action() {
                    public boolean act(final float delta) {
                        button.setVisible(false);
                        return true;
                    }
                }));
                for (int i = 0; i < item.length; i++) {
                    item[i].addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(1f)));
                    stage.addActor(item[i]);
                }
            }

            ;
        });
        centerButton();


        button.addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(1f)));
        stage.addActor(button);

        stage.addActor(timeLabel);
        timeLabel.setPosition(10, 30);

        stage.addActor(scoreLabel);


        scoreLabel.setPosition(1700, 30);


        for (int i = 0; i < item.length; i++) {
            if (item[i] != null) item[i] = null;
            item[i] = new Item(this, sorted[i]);
        }

        item[0].setPosition(xLeft, yBottom);
        item[1].setPosition(xRight, yBottom);
        item[2].setPosition(xLeft, yTop);
        item[3].setPosition(xRight, yTop);
    }

    private void centerButton() {
        CharSequence text = button.getText();
        float x = WIDTH / 2 - fontButton.getBounds(text).width / 2;
        float y = HEIGHT / 2 - fontButton.getBounds(text).height / 2;
        button.setPosition(x, y);
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }


}
