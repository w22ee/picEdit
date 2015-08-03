package com.example.administrator.picedit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MoveFreeView extends LinearLayout {


	public MoveFreeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private OnClickReCall mClickReCall;

	public void setLocation(int x, int y,View view) {

		int l = x- (int)((float)this.getWidth()/2);
		int t = y - this.getHeight();
		int r = x +(int)((float)this.getWidth()/2);
		int b = y;

		if (l<view.getLeft()){
			l = view.getLeft();
			r = this.getWidth();
		}

		if (r>view.getRight()){
			r = view.getRight();
			l = view.getRight() - this.getWidth();
		}

		if (t<view.getTop()){
			t = view.getTop();
			b = this.getBottom();
		}

		if (b>view.getBottom()){
			b = view.getBottom();
			t = view.getBottom()-this.getHeight();
		}

		this.layout(l,t,r,b);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("in  onTouchEvent canMove=" + canMove + ",tag" + this.getTag().toString());
		canMove = true;
		setTagId((int) this.getTag());
		if (event.getAction() == MotionEvent.ACTION_DOWN){
//			System.out.println("in ACTION_DOWNx="+event.getX()+",y="+event.getY());
		}

		if (event.getAction() == MotionEvent.ACTION_UP){
//			System.out.println("in ACTION_UPx="+event.getX()+",y="+event.getY());
		}

		return super.onTouchEvent(event);
	}

	public void setClickCall(OnClickReCall onClickReCall){
		mClickReCall = onClickReCall;
	}

	public  void setTagId(int id){
		mClickReCall.setTagId(id);
	}




	public boolean canMove = false;
	// ÒÆ¶¯
	public boolean autoMouse(MotionEvent event,View view) {
		boolean rb = false;
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
//				System.out.println("in  ACTION_MOVE canMove="+canMove);
				if (canMove){
					this.setLocation((int) event.getX(), (int) event.getY(),view);
					rb = false;
				}

				break;
		}
		return rb;
	}
}
