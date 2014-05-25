package com.possebom.wordsgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ScoreBoard extends WordsScreen {
    private final static int WIDTH = 1920;
    private final static int HEIGHT = 1080;
    private final WordsGDX game;
    private final I18NBundle myBundle;
    private Stage stage;
    private SpriteBatch batch;
    private Label scoresLabel;
    private Preferences prefs;

    public ScoreBoard(final WordsGDX game) {
        prefs = Gdx.app.getPreferences("scoreboard");
        myBundle = game.getMyBundle();
        this.game = game;
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        batch = new SpriteBatch();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        scoresLabel = new Label("",skinScore );


        Label label = new Label(myBundle.get("records") + "\n", skin);

        label.setFontScale(2.0f);

        table.add(label).space(10);
        table.row().space(10);

        table.add(scoresLabel).space(10);

        table.row().space(10);
        table.row().space(10);

        final TextButton button = new TextButton(myBundle.get("back"), skin);
        table.add(button).size(500,100);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(WordsGDX.MAIN_MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        int scoreSize = 0;


        String score = prefs.getString("score");
        if (Score.getHit() > 0 || Score.getMiss() > 0) {
            String line = String.format("%d;%d;%d;%s\n", System.currentTimeMillis(), Score.getHit(), Score.getMiss(),myBundle.get("lang"));
            score = score + line;
            prefs.putString("score", score);
            prefs.flush();
            Score.clear();
        }
        Gdx.app.log(TAG, "Score : " + score);

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        Calendar cal = Calendar.getInstance();

        List<String> listScore = new ArrayList<String>();
        listScore.addAll(Arrays.asList(score.split("\n")));
        List<String> listScoreOrdered = new ArrayList<String>();

        while(!listScore.isEmpty()) {
            int max = -1;
            int min = -1;
            int index = 0;
            for (int i = 0;i<listScore.size();i++){
                String line = listScore.get(i);
                String[] data = line.split(";");
                if (data.length < 4) continue;
                final int hits = Integer.parseInt(data[1].trim());
                final int miss = Integer.parseInt(data[2].trim());
                if(hits > max || (hits == max && miss < min)){
                    max = hits;
                    min = miss;
                    index = i;
                }
            }
            String maxLine = listScore.get(index);
            listScoreOrdered.add(maxLine);
            listScore.remove(index);
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0;i<listScoreOrdered.size() && i < 10;i++){
            String line = listScoreOrdered.get(i);
            String[] data = line.split(";");

            if (data.length < 4) continue;
            scoreSize++;

            String miss = data[2].trim();
            String hits = data[1].trim();
            //align
            if(hits.length() == 1){
                hits = " "+hits;
            }

            if(miss.length() == 1){
                miss = " "+miss;
            }

            cal.setTimeInMillis(Long.parseLong(data[0].trim()));

            Date date = cal.getTime();

            sb.append(myBundle.get("day"));
            sb.append(" : ").append(df.format(date));
            sb.append("   ");
            sb.append(myBundle.get("hits"));
            sb.append(" : ").append(hits);
            sb.append("   ");
            sb.append(myBundle.get("miss"));
            sb.append(" : ").append(miss);
            sb.append("   ");
            sb.append(data[3].trim());
            sb.append("\n");
        }
        sb.append("\n");

        while(scoreSize < 10){
            sb.append("\n");
            scoreSize++;
        }

        scoresLabel.setText(sb.toString());
    }

}
