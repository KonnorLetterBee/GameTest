package com.kngames.gametest.redata.Info.SelectorFrags;

import com.kngames.gametest.cards.structures.BaseSelectorFragment;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardData;

public class ItemSelectorFragment extends BaseSelectorFragment {
	public ItemSelectorFragment() {
		super(BaseSingleFragmentActivity.ITEM_FRAG, CardData.Items);
	}
}
