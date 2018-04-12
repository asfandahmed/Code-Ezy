package asfand.ahmed.code_ezy_;

import asfand.ahmed.code_ezy_.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Settings extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        addPreferencesFromResource(R.xml.app_preferences);
    }
}
