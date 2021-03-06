package com.aol.mobile.enumberreader;

import java.util.List;

import com.aol.mobile.enumberreader.data.ENumbersParser;
import com.aol.mobile.enumberreader.model.ENumber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NumbersFragment extends ListFragment {

	boolean mDualPane;
    int mCurCheckPosition = 0;

    enum EnumberAssets {Enumbers_colors, Enumbers_preservatives};
    
    
    public static NumbersFragment newInstance(EnumberAssets type) {
    	NumbersFragment f = new NumbersFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        String asset = type.name()+".json";
        args.putString("asset", asset);
        f.setArguments(args);

        return f;
    }
    
    
    public String getAssetFile() {
        return (String) getArguments().get("asset");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ENumbersParser parser = new ENumbersParser(getActivity());
        List<ENumber> enumbers = parser.parseFromAssets(getAssetFile());
        
        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<ENumber>(getActivity(),
                android.R.layout.simple_list_item_activated_1, enumbers));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        ENumber enumberSelected = (ENumber)getListView().getItemAtPosition(index);
        
        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
            if (details == null || !(enumberSelected.getNumber().equals(details.getENumber().getNumber()))) {
                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(enumberSelected);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("enumber", enumberSelected);
            startActivity(intent);
        }
    }
    
}
