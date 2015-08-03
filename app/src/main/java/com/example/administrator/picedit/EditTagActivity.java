package com.example.administrator.picedit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EditTagActivity extends Activity implements OnClickReCall {

    private RelativeLayout myRelativelayout;
    private ImageView imageView;
    private Context mContext;
    private List<Tag> myTaglist;
    private Button addButton;
    private Button comfrimButton;
    private EditText editText;
    private LayoutInflater inflater;
    private int picW;
    private int picH;
    private RelativeLayout buttomEditLayout;
    private InputMethodManager inputManager;
    public int crentTagId = 999;
    private RelativeLayout buttomBtnLayout;
    float x1 = 0;
    float y1 = 0;
    float x2 = 0;
    float y2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTaglist = new ArrayList<Tag>();

        setViews();
    }

    @Override
    protected void onResume() {
        setLocation();
        setListener();
        super.onResume();
    }

    private void setViews() {
        mContext = this;
        inflater = LayoutInflater.from(mContext);
        myRelativelayout = (RelativeLayout) findViewById(R.id.parent_layout);
        buttomEditLayout = (RelativeLayout) findViewById(R.id.buttom_layout);
        imageView = (ImageView) findViewById(R.id.pic_inside);
        addButton = (Button) findViewById(R.id.add_button);
        editText = (EditText) findViewById(R.id.editText);
        comfrimButton = (Button) findViewById(R.id.ok_button);
        buttomBtnLayout = (RelativeLayout) findViewById(R.id.buttom_btn_layout);
        inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Button button = (Button) findViewById(R.id.tag_tab_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EditTagActivity.this,TagPicActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLocation() {

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        picW = point.x;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aj4);

        int bitmaph = bitmap.getHeight();

        int bitmapw = bitmap.getWidth();

        picH = (int) ((((float) bitmaph / (float) bitmapw)) * (float) picW);

        RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, picH);

        imageView.setImageBitmap(bitmap);

        imageView.setLayoutParams(paramsImg);


    }

    private void setListener() {

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTaglist.size() == 33) {
                    return;
                }
                crentTagId = 999;
                showEdit();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        comfrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    return;
                }
                if (crentTagId == 999) {
                    createTagView(editText.getText().toString());
                } else {
                    setTagViewText();
                }

                hideEdit();
            }
        });


    }

    private void showEdit() {
        buttomEditLayout.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        inputManager.showSoftInput(editText, 0);
        addButton.setVisibility(View.GONE);
        buttomBtnLayout.setVisibility(View.GONE);

    }

    private void hideEdit() {
        editText.setText("");
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        buttomEditLayout.setVisibility(View.GONE);
        addButton.setVisibility(View.VISIBLE);
        buttomBtnLayout.setVisibility(View.VISIBLE);
    }


    private void createTagView(String tagContent) {
        MoveFreeView moveFreeView = (MoveFreeView) inflater.inflate(R.layout.text_layout, null);

        TextView textView = (TextView) moveFreeView.findViewById(R.id.textView);
        textView.setText(tagContent);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        moveFreeView.measure(w, h);
        int height = moveFreeView.getMeasuredHeight();
        int width = moveFreeView.getMeasuredWidth();
        Random random = new Random();
        moveFreeView.getBackground().setAlpha(180);
        int randomWdithRange = picW - width;
        if (randomWdithRange < 0) {
            randomWdithRange = 0;
        }

        int randomHeightRange = picH - height;
        if (randomHeightRange < 0) {
            randomHeightRange = 0;
        }
        int randomW = random.nextInt(randomWdithRange);
        int randomH = random.nextInt(randomHeightRange);
        Tag taga = new Tag();
        taga.tagId = myTaglist.size();
        taga.x = (float) randomW / (float) picW;
        taga.y = (float) randomH / (float) picH;
        taga.tagContent = tagContent;
        myTaglist.add(taga);

        System.out.println("create id =" + taga.tagId);
        moveFreeView.setTag(taga.tagId);
        moveFreeView.setClickCall(EditTagActivity.this);
        moveFreeView.invalidate();
        myRelativelayout.addView(moveFreeView, getParams(taga));
    }

    private RelativeLayout.LayoutParams getParams(Tag tag) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (tag.y * (float) picH);
        params.leftMargin = (int) (tag.x * (float) picW);
        return params;
    }

    private void setTagViewText() {
        MoveFreeView moveFreeView = (MoveFreeView) myRelativelayout.findViewWithTag(crentTagId);
        TextView textView = (TextView) moveFreeView.findViewById(R.id.textView);
        textView.setText(editText.getText().toString());
        for (Tag tag : myTaglist) {
            if (tag.tagId == crentTagId) {
                tag.tagContent = editText.getText().toString();
            }
        }
    }


    private void editTagCotent(View view) {
        TextView textView = (TextView) view.findViewById(R.id.textView);
        String text = textView.getText().toString();
        editText.setText(text);
        int tag = (int) view.getTag();
        crentTagId = tag;
        showEdit();
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("out onTouchEvent");
        System.out.println("out onTouchEvent crentTagId=" + crentTagId);
        MoveFreeView moveFreeView = (MoveFreeView) myRelativelayout.findViewWithTag(crentTagId);

        if (moveFreeView != null) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = 0;
                y1 = 0;
                x2 = 0;
                y2 = 0;
                x1 = event.getX();
                y1 = event.getY();
                System.out.println("out ACTION_DOWNx=" + x1 + ",y=" + y1);
            }


            if (event.getAction() == MotionEvent.ACTION_UP) {

                x2 = event.getX();
                y2 = event.getY();
                System.out.println("x1=" + x1 + "y1=" + y1 + "x2=" + x2 + "y2=" + y2);
                System.out.println("Math.abs(x1 - x2)=" + Math.abs(x1 - x2) + ",Math.abs(y1 - y2)=" + Math.abs(y1 - y2));
                for (Tag tag : myTaglist) {
                    if (tag.tagId == crentTagId) {
                        tag.x = (float) moveFreeView.getLeft() / (float) picW;
                        tag.y = (float) moveFreeView.getRight() / (float) picH;
                    }
                }
                if (Math.abs(x1 - x2) < 3 && Math.abs(y1 - y2) < 3) {
//                    moveFreeView.setLayoutParams(getParams(myTaglist.get(crentTagId)));
                    editTagCotent(moveFreeView);


                }
                crentTagId = 999;
                moveFreeView.canMove = false;

            }
            if (moveFreeView.canMove) {
                moveFreeView.autoMouse(event, imageView);
            }
        }


        return super.onTouchEvent(event);
    }

    @Override
    public void setTagId(int id) {
        crentTagId = id;
    }


}

