package asfand.ahmed.code_ezy_.bl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Score {

    public int numOfSteps = 0;
    private final int scorePointOnEachStep = 40;
    private final int levelOneDivider = 1;
    private final int levelTwoDivider = 2;

    public int calculate(String level){
        int divider = (level == "1") ? levelOneDivider : (level == "2") ? levelTwoDivider : 1;
        int score = (numOfSteps * scorePointOnEachStep) / divider;
        return score;
    }

    public boolean setHighScore(Context context, int score){
        SharedPreferences appPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        int highScore = appPrefs.getInt("highScorePref", 0);
        if(highScore < score){
            appPrefs.edit().putInt("highScorePref", score).commit();
            return true;
        }
        return false;
    }

}
