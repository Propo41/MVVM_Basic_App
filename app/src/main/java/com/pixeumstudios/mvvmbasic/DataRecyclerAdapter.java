package com.pixeumstudios.mvvmbasic;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixeumstudios.mvvmbasic.databinding.ItemDataBinding;

import java.util.ArrayList;

public class DataRecyclerAdapter extends RecyclerView.Adapter<DataRecyclerAdapter.DataRecyclerAdapterViewHolder> {

    private ArrayList<User> mExampleItems;

    public DataRecyclerAdapter(ArrayList<User> exampleItems){
        mExampleItems = exampleItems;
    }

    public static class DataRecyclerAdapterViewHolder extends RecyclerView.ViewHolder{
        private ItemDataBinding mBinding;

        public DataRecyclerAdapterViewHolder(ItemDataBinding binding){
            super(binding.getRoot());
            mBinding = binding;

        }
    }



    @NonNull
    @Override
    public DataRecyclerAdapter.DataRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataBinding binding = ItemDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DataRecyclerAdapterViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull DataRecyclerAdapter.DataRecyclerAdapterViewHolder holder, int position) {
        User currentItem = mExampleItems.get(position);
        holder.mBinding.textView3.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mExampleItems.size();
    }
}