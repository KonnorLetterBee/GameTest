package com.kngames.gametest.redata.CardInfo;

import com.kngames.gametest.redata.BaseInfoFragmentActivity;
import com.kngames.gametest.redata.CardData;
import android.os.Bundle;

public class ActionSelectorActivity extends BaseSelectorActivity {
	
	public ActionSelectorActivity() {
		super(BaseInfoFragmentActivity.ACTION_FRAG, CardData.Actions);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
