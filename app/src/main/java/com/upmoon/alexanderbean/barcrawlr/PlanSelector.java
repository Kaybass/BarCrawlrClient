package com.upmoon.alexanderbean.barcrawlr;

import android.support.v4.app.Fragment;
import com.upmoon.alexanderbean.barcrawlr.fragments.PlanSelectorFragment;


public class PlanSelector extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() { return new PlanSelectorFragment(); }
}
