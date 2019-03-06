package com.fpoly.duyet.oderfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fpoly.duyet.oderfood.Model.Drink;
import com.fpoly.duyet.oderfood.R;
import com.fpoly.duyet.oderfood.Utils.Common;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by duyet on 10/19/2018.
 */

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceViewHolder>{
    Context context;
    List<Drink> optionList;

    public MultiChoiceAdapter(Context context, List<Drink> optionList) {
        this.context = context;
        this.optionList = optionList;
    }
    @NonNull
    @Override
    public MultiChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(context).inflate(R.layout.multi_check_layout,null);
        return new MultiChoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MultiChoiceViewHolder holder, final int position) {
        holder.checkBox.setText(optionList.get(position).Name);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
                if (isChecked){
                    Common.toppingAdded.add(buttonview.getText().toString());
                    Common.toppingPrice+=Double.parseDouble(optionList.get(position).Price);
                }else {
                    Common.toppingAdded.remove(buttonview.getText().toString());
                    Common.toppingPrice-=Double.parseDouble(optionList.get(position).Price);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    class MultiChoiceViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public MultiChoiceViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.ckb_topping);

        }
    }
}
