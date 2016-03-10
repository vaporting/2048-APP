package com.test.dean.testapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView main_view = (GridView)findViewById(R.id.main_gridview);
        GV_adapter gv_adapter = new GV_adapter(this);
        main_view.setAdapter(gv_adapter);
        //main_view.setOnTouchListener(new CustomListener(main_view));
        final Button start_button = (Button)findViewById(R.id.start_button);
        final Button stop_button = (Button)findViewById(R.id.stop_button);
        start_button.setEnabled(true);
        stop_button.setEnabled(false);
        start_button.setOnClickListener(new View.OnClickListener() {
            Random random = new Random();
            GridView main_view = (GridView)findViewById(R.id.main_gridview);
            @Override
            public void onClick(View v) {
                int i,n;
                main_view.setOnTouchListener(new CustomListener(MainActivity.this));
                i =  random.nextInt(15);
                n = random.nextInt(15);
                while(n==i)
                    n = random.nextInt(15);
                ((TextView) main_view.getItemAtPosition(i)).setText(Integer.toString(2));
                ((TextView) main_view.getItemAtPosition(n)).setText(Integer.toString(2));
                start_button.setEnabled(false);
                stop_button.setEnabled(true);
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            GridView main_view = (GridView)findViewById(R.id.main_gridview);
            @Override
            public void onClick(View v) {
                main_view.setOnTouchListener(null);
                start_button.setEnabled(true);
                stop_button.setEnabled(false);
            }
        });
    }



    private class GV_adapter extends BaseAdapter{
        private LayoutInflater factory;
        private int mheight;
        View EntryView;
        private ArrayList<TextView> textview_list;
       public GV_adapter(Context c){
           textview_list = new ArrayList<TextView>();
           factory = getLayoutInflater();
           textview_update();
       }

        @Override
       public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                EntryView = factory.inflate(R.layout.item, parent, false);
                TextView block_view = (TextView) EntryView.findViewById(R.id.block_view);
                textview_list.add(block_view);
                return block_view;
            }
            return convertView;

       }
       @Override
       public long getItemId(int position) {
           //System.out.println("getItemId = " + position);
           return 0;
       }
       @Override
       public Object getItem(int position) {
           return textview_list.get(position);
       }
       public int getCount() {
           return 16;
       }
        private void textview_update(){
            GridView main_view = (GridView)findViewById(R.id.main_gridview);
            ViewTreeObserver vto = main_view.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    GridView main_view = (GridView)findViewById(R.id.main_gridview);
                    mheight = main_view.getHeight();
                    TextView temp;
                    int odd_color=Color.LTGRAY, even_color=Color.GRAY, temp_color;
                    for(int i=0;i<textview_list.size();i++){
                        temp = textview_list.get(i);
                        temp.setHeight(mheight / 4);
                        if(i%2==0)
                            temp.setBackgroundColor(odd_color);
                        else
                            temp.setBackgroundColor(even_color);
                        if(i%4==3){
                            temp_color = odd_color;
                            odd_color = even_color;
                            even_color = temp_color;
                        }
                        temp.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, mheight / 4/*GridView.LayoutParams.MATCH_PARENT*/));
                    }
                    main_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            });
        }
    }

}
