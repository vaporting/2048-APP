package com.test.dean.testapp;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;


/**
 * Created by DeanTing on 2016/3/8.
 */

enum Direction{
    UP, DOWN, RIGHT, LEFT;
}

public class CustomListener implements View.OnTouchListener{
    private GridView main_view;
    private MainActivity mainAct;
    float begin_x, begin_y;
    Direction direction;
    Vector<Integer> empty_textview;
    Random random = new Random();
    public CustomListener(MainActivity mainAct){
        this.mainAct = mainAct;
        this.main_view = (GridView)mainAct.findViewById(R.id.main_gridview);
        this.empty_textview = new Vector<Integer>();
        for(int i= 0;i<16;i++)
            ((TextView) main_view.getItemAtPosition(i)).setText("");
    }
    public boolean onTouch(View v, MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        float move_x, move_y;
        switch (action) {
            case MotionEvent.ACTION_UP:
                move_x = event.getX() - begin_x;
                move_y = event.getY() - begin_y;
                if (move_x < 0)
                    direction = Direction.LEFT;
                else
                    direction = Direction.RIGHT;
                if (Math.abs(move_x) < Math.abs(move_y)) {
                    if (move_y > 0)
                        direction = Direction.DOWN;
                    else
                        direction = Direction.UP;
                }
                update(direction);
                String result = check_result();
                if(result!="")
                    final_step(result);
                break;
            case MotionEvent.ACTION_DOWN:
                begin_x = event.getX();
                begin_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return false;
    }
    private String check_result(){
       String temp;
        for(int i = 0;i<empty_textview.size();i++){
            temp = ((TextView) main_view.getItemAtPosition(i)).getText().toString();
            if(temp=="2048")
                return "Win";
        }
        if(this.empty_textview.size()==0)
            return  "Lose";
        return "";
    }
    private void final_step(String result){
        ((TextView) mainAct.findViewById(R.id.result_textView)).setText(result);
        main_view.setOnTouchListener(null);
        ((Button)mainAct.findViewById(R.id.start_button)).setEnabled(true);
        ((Button)mainAct.findViewById(R.id.stop_button)).setEnabled(false);
    }
    private void update(Direction direction) {
        switch (direction) {
            case UP:
                handle_up();
                break;
            case DOWN:
                handle_down();
                break;
            case RIGHT:
                handle_right();
                break;
            case LEFT:
                handle_left();
                break;
            default:
                break;

        }
    }

    private void find_empty_textview(){
        int i;
        String temp_str;
        System.out.print("test");
        if(!empty_textview.isEmpty())
            empty_textview.clear();
        for(i=0;i<16;i++){
            temp_str = "";
            temp_str = ((TextView) main_view.getItemAtPosition(i)).getText().toString();
            if(temp_str == ""){
                empty_textview.add(i);
            }
        }
    }

    private void radom_set_value_to_textview(){
        int i;
        if(!empty_textview.isEmpty()){
            if(empty_textview.size()>1)
                i =  random.nextInt(empty_textview.size()-1);
            else
                i = 0;
            ((TextView) main_view.getItemAtPosition(empty_textview.get(i))).setText(Integer.toString(2));
        }
    }

    private void handle_up() {
        int[] line = new int[4];
        int[]position = new int[4];
        int i = 0;
        String temp_str = "";
        for (i = 0; i < 4; i++) {
            //if string is null, set the value be zero
            //initial value
            this.initial_line(line);
            position[0]=i;
            position[1]=i+4;
            position[2]=i+8;
            position[3]=i+12;
            //set value
            this.set_line_by_textview(line, position);
            handle_line(line);
            set_textview_value(line, position);
        }
        this.find_empty_textview();
        radom_set_value_to_textview();
    }

    private void handle_down(){
        int[] line = new int[4];
        int[]position = new int[4];
        int i = 0;
        for(i=0;i<4;i++){
            this.initial_line(line);
            position[3]=i;
            position[2]=i+4;
            position[1]=i+8;
            position[0]=i+12;
            //set value
            this.set_line_by_textview(line, position);
            handle_line(line);
            set_textview_value(line, position);
        }
        find_empty_textview();
        radom_set_value_to_textview();
    }

    private void handle_left(){
        int[] line = new int[4];
        int[]position = new int[4];
        int i = 0;
        for(i=0;i<13;i+=4){
            this.initial_line(line);
            position[0]=i;
            position[1]=i+1;
            position[2]=i+2;
            position[3]=i+3;
            //set value
            this.set_line_by_textview(line, position);
            handle_line(line);
            set_textview_value(line, position);
        }
        find_empty_textview();
        radom_set_value_to_textview();
    }

    private void handle_right(){
        int[] line = new int[4];
        int[]position = new int[4];
        int i = 0;
        for(i=0;i<13;i+=4){
            this.initial_line(line);
            position[3]=i;
            position[2]=i+1;
            position[1]=i+2;
            position[0]=i+3;
            //set value
            this.set_line_by_textview(line, position);
            handle_line(line);
            set_textview_value(line, position);
        }
        find_empty_textview();
        radom_set_value_to_textview();
    }

    private void initial_line(int[] line){
        int i;
        for(i=0;i<line.length;i++)
            line[i]=0;
    }

    private void set_line_by_textview(int[] line, int[] position){
        String temp_str = "";
        temp_str = ((TextView) main_view.getItemAtPosition(position[0])).getText().toString();
        if(temp_str != "")
            line[0] = Integer.parseInt(temp_str);
        temp_str = ((TextView) main_view.getItemAtPosition(position[1])).getText().toString();
        if(temp_str != "")
            line[1] = Integer.parseInt(temp_str);
        temp_str = ((TextView) main_view.getItemAtPosition(position[2])).getText().toString();
        if(temp_str != "")
            line[2] = Integer.parseInt(temp_str);
        temp_str = ((TextView) main_view.getItemAtPosition(position[3])).getText().toString();
        if(temp_str != "")
            line[3] = Integer.parseInt(temp_str);
    }
    private void set_textview_value(int[] line, int[] position){
        int i;
        for(i=0;i<4;i++) {
            if(line[i]!=0)
                ((TextView) main_view.getItemAtPosition(position[i])).setText(Integer.toString(line[i]));
            else
                ((TextView) main_view.getItemAtPosition(position[i])).setText("");
        }
    }

    private void handle_line(int[] line) {
        int i = 0;
        push_line_up(line);
        while (i < 3) {
            if(line[i]==line[i+1]&&line[i]!=0){
                line[i]=line[i]*2;
                line[i+1]=0;
                push_line_up(line);
                i=0;
            }
            else
                i++;
        }
    }

    private void push_line_up(int[] line) {
        int i = 0, null_position = 0;
        while (i < 4) {
            if (line[i] != 0) {
                line[null_position] = line[i];
                if(i != null_position)
                    line[i]=0;
                null_position++;
            }
            i++;
        }
    }
}
