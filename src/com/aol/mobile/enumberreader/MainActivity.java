package com.aol.mobile.enumberreader;

import java.util.List;
import java.util.Vector;

import com.aol.mobile.core.moreapps.MoreAppsListFragment;
import com.aol.mobile.core.workwithus.WorkWithUsListFragment;
import com.aol.mobile.enumberreader.NumbersFragment.EnumberAssets;
import com.aol.mobile.enumberreader.adapter.PagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private PagerAdapter mPagerAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        this.initialisePaging();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    /**
	 * Initialise the fragments to be paged
	 */
	private void initialisePaging() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(NumbersFragment.newInstance(EnumberAssets.Enumbers_colors));
		fragments.add(NumbersFragment.newInstance(EnumberAssets.Enumbers_preservatives));
		fragments.add(Fragment.instantiate(this, WorkWithUsListFragment.class.getName()));
		//fragments.add(Fragment.instantiate(this, DetailsFragment.class.getName()));
		this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
		//
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
		/*
		TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
		titleIndicator.setViewPager(pager);
		*/
	}
	
	
}
