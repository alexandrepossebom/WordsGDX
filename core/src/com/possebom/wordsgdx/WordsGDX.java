package com.possebom.wordsgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class WordsGDX extends Game {
    public static final int MAIN_MENU = 1;
    public static final int MAIN_GAME = 2;
    public static final int SCORE_BOARD = 3;

    private I18NBundle myBundle;

    @Override
    public void create() {
        myBundle = I18NBundle.createBundle(Gdx.files.internal("i18n/MyBundle"));
        setScreen(MAIN_MENU);
    }

    public void changeLanguage(){
        Locale locale;
        String lang = myBundle.get("lang");
        if(lang.equals("EN")){
            locale = new Locale("PT");
        }else{
            locale = new Locale("EN");
        }
        myBundle = I18NBundle.createBundle(Gdx.files.internal("i18n/MyBundle"),locale);
    }

    public I18NBundle getMyBundle(){
        return myBundle;
    }

    public void setScreen(final int screenId) {

        switch (screenId) {
            case MAIN_GAME:
                setScreen(new MainGame(this));
                break;
            case SCORE_BOARD:
                setScreen(new ScoreBoard(this));
                break;
            default:
                setScreen(new MainMenuScreen(this));
                break;
        }

    }

}

