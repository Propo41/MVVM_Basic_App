package com.pixeumstudios.mvvmbasic;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixeumstudios.mvvmbasic.databinding.ItemDataBinding;

import java.util.ArrayList;

public class DataRecyclerAdapter extends RecyclerView.Adapter<DataRecyclerAdapter.DataRecyclerAdapterViewHolder> {

    private ArrayList<User> mUserList;
    private OnItemClickListener mListener;

    public DataRecyclerAdapter(ArrayList<User> userList){
        mUserList = userList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public static class DataRecyclerAdapterViewHolder extends RecyclerView.ViewHolder{
        private ItemDataBinding mBinding;
        private OnItemClickListener mListener;

        public DataRecyclerAdapterViewHolder(ItemDataBinding binding, final OnItemClickListener customListener){
            super(binding.getRoot());
            mBinding = binding;
            mListener = customListener;

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    @NonNull
    @Override
    public DataRecyclerAdapter.DataRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataBinding binding = ItemDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DataRecyclerAdapterViewHolder(binding, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull DataRecyclerAdapter.DataRecyclerAdapterViewHolder holder, int position) {
        User currentItem = mUserList.get(position);
        holder.mBinding.textView3.setText(currentItem.getName());
        holder.mBinding.textView3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.mListener.onItemClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}