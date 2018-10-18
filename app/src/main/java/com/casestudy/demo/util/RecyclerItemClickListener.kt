package com.casestudy.demo.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListener : RecyclerView.OnItemTouchListener {
    private var mListener: OnItemClickListener? = null
    private var recyclerViewItemClickListener: OnRecyclerViewItemClickListener? = null

    internal var mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnRecyclerViewItemClickListener {
        fun onItemClick(parentView: View, childView: View, position: Int)
    }

    constructor(context: Context, listener: OnItemClickListener) {
        mListener = listener
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    constructor(context: Context, listener: OnRecyclerViewItemClickListener) {
        recyclerViewItemClickListener = listener
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener!!.onItemClick(childView, view.getChildPosition(childView))
        } else if (childView != null && recyclerViewItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
            recyclerViewItemClickListener!!.onItemClick(view, childView, view.getChildPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {

    }
}