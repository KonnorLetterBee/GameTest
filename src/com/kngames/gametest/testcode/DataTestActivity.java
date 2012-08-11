package com.kngames.gametest.testcode;

import java.io.File;
import java.util.Hashtable;

import com.kngames.gametest.engine.data.DataOps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataTestActivity extends Activity {
	
	private static final String TAG = DataTestActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout shell = new LinearLayout(this);
        shell.setOrientation(LinearLayout.VERTICAL);
        
        TextView warning = new TextView(this);
        warning.setText("WARNING:\n\nrunning these tests will delete all external data for this program " +
        		"(everything in /mnt/sdcard/ResidentEvilDBG)\n\nonly proceed if you don't care about this data");
        warning.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shell.addView(warning);
        
        Button proceedButton = new Button(this);
        proceedButton.setText("Proceed with Tests");
        proceedButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				performAllTests();
			}
		});
        proceedButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shell.addView(proceedButton);
        
        Button finishButton = new Button(this);
        finishButton.setText("Finished tests");
        finishButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finishTests();
			}
		});
        finishButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shell.addView(finishButton);
        
        TextView results = new TextView(this);
        results.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shell.addView(results);
        
        shell.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(shell);
    }
	
	private void performAllTests() {
		DataOps.eraseAppExtData();
		
		doRelativeFolderTest("/scenario");
	    doRelativeFileTest("/scenario/test_scen.scen");
	    doRelativeFileWriteTest("/scenario/test_scen.scen", "name;test data\ndate;whenever the hell i feel like it\nother random crap;random crap");
	    doRelativeFileReadTest("/scenario/test_scen.scen");
	}
	
	private void finishTests() {
		Log.d(TAG, "Exiting...");
        this.finish();
	}
	
	private void doRelativeFileTest(String path) {
		File f;
		try {
			f = DataOps.getFile(DataOps.TYPE_FILE, path, true);
			Log.d(TAG, String.format("Absolute path: \"%s\"", f.getAbsolutePath()));
		} catch (Exception e) {
			Log.e(TAG, String.format("Error: exception: %s", path));
		}
	}
	
	private void doRelativeFolderTest(String path) {
		File f;
		try {
			f = DataOps.getFile(DataOps.TYPE_FOLDER, path, true);
			Log.d(TAG, String.format("Absolute path: \"%s\"", f.getAbsolutePath()));
		} catch (Exception e) {
			Log.e(TAG, String.format("Error: exception: %s", path));
		}
	}
	
	private void doRelativeFileWriteTest(String path, String data) {
		try {
			DataOps.exportToFile(path, data);
			Log.d(TAG, "Write complete");
		} catch (Exception e) {
			Log.e(TAG, String.format("Error: exception: %s", path));
		}
	}
	
	private void doRelativeFileReadTest(String path) {
		try {
			Hashtable<String,String> data = DataOps.importFromFile(path);
			Object[] keys = data.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				String key = (String)keys[i];
				String val = data.get(key);
				Log.d(TAG, String.format("(%d: %s, %s)", i, key, val));
			}
			Log.d(TAG, "Read complete");
		} catch (Exception e) {
			Log.e(TAG, String.format("Error: exception: %s", path));
		}
	}
}
