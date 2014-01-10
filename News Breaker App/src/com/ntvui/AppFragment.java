package com.ntvui;

import com.ntvui.R.color;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Font.Style;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class AppFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final int IMAGE_CONTENT = 1;
int resId[] = {R.drawable.abs__ic_voice_search,R.drawable.ic_menu_compose,R.drawable.ntvlogos,R.drawable.icon};
    public static AppFragment newInstance(String content, int image) {
        AppFragment fragment = new AppFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();
        fragment.images=image;

        return fragment;
    }

    private String mContent = "???";
    private int images = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
           // image = savedInstanceState.getInt(IMAGE_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getActivity());
        text.setGravity(Gravity.CENTER);
        text.setText(mContent);
        text.setTextColor(color.themecolor);
        text.setTextSize(12);
        text.setPadding(20, 20, 20, 20);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(layout.VERTICAL);
        layout.addView(text);
        
        //adding imageView
        ImageView image = new ImageView(getActivity());
       image.setImageResource(images);
       image.setPadding(20, 20, 20,40);       
     layout.addView(image);
     
     //Button
     String buttonText ="Click Here To Get Started";
     Button nextButton = new Button(getActivity());
     nextButton.setPadding(20, 20, 20, 20);

     nextButton.setTextColor(Color.WHITE);
     nextButton.setTextSize(12);
     
     
     nextButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
     nextButton.setText(buttonText);
     nextButton.setBackgroundResource(R.drawable.green_grad_btn);
//Add a button on last array item
     if(images == R.drawable.ntvlogos){
    	
    	 layout.addView(nextButton);
    	 nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(getActivity(), HomeActivity.class);
		    	 startActivity(i);
		    	 //getActivity().finish();		
			}
		});
     }
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
        //outState.putInt(IMAGE_CONTENT, images);
        
    }
}
