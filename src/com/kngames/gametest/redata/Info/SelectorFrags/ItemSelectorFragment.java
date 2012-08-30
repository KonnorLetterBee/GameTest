package com.kngames.gametest.redata.Info.SelectorFrags;

import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.data.GameData;

public class ItemSelectorFragment extends BaseRESelectorFragment {
	public ItemSelectorFragment() {
		super(BaseSingleFragmentActivity.ITEM_FRAG, GameData.Items);
	}
}
