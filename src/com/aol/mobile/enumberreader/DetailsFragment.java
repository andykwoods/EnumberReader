package com.aol.mobile.enumberreader;


import com.aol.mobile.core.moreapps.MoreAppsView;
import com.aol.mobile.enumberreader.model.ENumber;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DetailsFragment extends Fragment {
	
	private ProgressBar progressBar;
	
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailsFragment newInstance(ENumber enumber) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("enumber", enumber);
        f.setArguments(args);

        return f;
    }

    public ENumber getENumber() {
        return (ENumber) getArguments().get("enumber");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        /*
        ENumber enumber = getENumber();
        getActivity().setTitle(enumber.getName());
        getActivity().setProgressBarVisibility(true);
        getActivity().setProgressBarIndeterminate(true);
        getActivity().setProgressBarIndeterminateVisibility(true);
        
        progressBar = new ProgressBar(getActivity());
        progressBar.setIndeterminate(true);
        
        WebView myWebView = new WebView(getActivity());
    	myWebView.setWebViewClient(new MyWebViewClient());
    	myWebView.loadUrl(enumber.getWikipediaUrl());

    	return myWebView;
    	*/
        
        View view = new MoreAppsView(getActivity());
        return view;
    }
    
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().contains("wikipedia")){
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
        
        public void onPageFinished(WebView view, String url) {
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
        }
        
    }
   

}
