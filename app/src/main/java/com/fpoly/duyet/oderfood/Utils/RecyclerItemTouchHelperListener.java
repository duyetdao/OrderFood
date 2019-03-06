package com.fpoly.duyet.oderfood.Utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by duyet on 10/22/2018.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction,int position);

}
