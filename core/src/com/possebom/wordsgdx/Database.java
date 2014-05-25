package com.possebom.wordsgdx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Database {
    private final String[] words;
    private final List<String> keys = new ArrayList<String>();
    private final I18NBundle myBundle;
    private String sorted;

    public Database(final I18NBundle bundle) {
        myBundle = bundle;

        final String wordsEnabled = myBundle.get("wordsEnabled");

        words = wordsEnabled.split(",");

        keys.addAll(Arrays.asList(words));

        Collections.shuffle(keys);
    }

    public int getTotal() {
        return words.length;
    }

    public int size() {
        return keys.size();
    }

    public String getName(String key) {
        return myBundle.get(key);
    }

    public String[] getItems() {
        if (keys.size() == 0) return null;

        sorted = keys.get(0);
        keys.remove(0);

        final List<String> items = new ArrayList<String>(Arrays.asList(words));
        Collections.shuffle(items);

        String[] str = new String[4];

        items.remove(sorted);
        for (int i = 0; i < str.length; i++) {
            str[i] = items.get(i);
        }
        int r = MathUtils.random(0, 3);
        str[r] = sorted;
        return str;
    }

    public String getSortedKey() {
        return sorted;
    }

    public String getSortedName() {
        final String name = "  " + getName(sorted).toUpperCase() + "  ";
        return name;
    }

}
