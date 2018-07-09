package com.kotiyaltech.craftsbeer.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotiyaltech.craftsbeer.BR;
import com.kotiyaltech.craftsbeer.R;
import com.kotiyaltech.craftsbeer.model.BeerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 30-06-2018.
 */

public class BeerListAdapter  extends RecyclerView.Adapter<BeerListAdapter.ViewHolder> {

    private List<BeerItem> beerItemList = new ArrayList<>();

    public BeerListAdapter(List<BeerItem> beerItems){
        beerItemList = beerItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BeerItem beerItem = beerItemList.get(position);
        holder.getBinding().setVariable(BR.beerItem, beerItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return beerItemList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }
}
