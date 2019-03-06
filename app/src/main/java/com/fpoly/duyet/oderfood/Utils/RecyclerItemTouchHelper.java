package com.fpoly.duyet.oderfood.Utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.fpoly.duyet.oderfood.Adapter.CartAdapter;
import com.fpoly.duyet.oderfood.Adapter.FavoriteAdapter;

/**
 * Created by duyet on 10/22/2018.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListener listener) {

        super(dragDirs, swipeDirs);
        this.listener = listener;;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener!=null)
            listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
       if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
       {
           View foreground =((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
           getDefaultUIUtil().clearView(foreground);

       }else {
           if (viewHolder instanceof CartAdapter.CartViewHolder)
           {
               View foreground =((CartAdapter.CartViewHolder)viewHolder).view_foreground;
               getDefaultUIUtil().clearView(foreground);


           }
       }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null)
        {
            if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder){
                View foregroundView =((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
            else  if (viewHolder instanceof CartAdapter.CartViewHolder){
                View foregroundView =((CartAdapter.CartViewHolder)viewHolder).view_foreground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder){
            View foregroundView =((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }else if (viewHolder instanceof  CartAdapter.CartViewHolder)
        {
            View foregroundView =((CartAdapter.CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
       if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
       {
           View foregroundView =((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
           getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
       }else if (viewHolder instanceof CartAdapter.CartViewHolder)
       {
           View foregroundView =((CartAdapter.CartViewHolder)viewHolder).view_foreground;
           getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
       }
    }
}
