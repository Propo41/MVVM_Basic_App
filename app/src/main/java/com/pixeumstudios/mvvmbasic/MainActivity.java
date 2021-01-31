package com.pixeumstudios.mvvmbasic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.pixeumstudios.mvvmbasic.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;
    private DataRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> mUsersList;
    private ArrayList<String> mUidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        setupRecyclerView();

       // FirebaseDatabase.getInstance().getReference().child("users/user1").setValue(new User("Arnob", "AUST", 1234));

        mViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);

        mViewModel.initializeRepo();
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.button.setEnabled(false);

                // observing a single user
                LiveData<DataSnapshot> liveData = mViewModel.getDataSnapshotLiveData("users/user1");
                liveData.observe(MainActivity.this, new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            User user = dataSnapshot.getValue(User.class);
                            if(user!=null){
                                mBinding.textView.setText(user.getName());
                                mBinding.textView2.setText(user.getUniversity());
                            }
                        }
                    }
                });

                // observing user list
                LiveData<DataSnapshot> liveDataList = mViewModel.getDataSnapshotLiveData("/users/");
                liveDataList.observe(MainActivity.this, new Observer<DataSnapshot>() {
                    @Override
                    public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                User user = snapshot.getValue(User.class);
                                mUsersList.add(user);
                                mUidList.add(snapshot.getKey());
                            }
                        }
                    }
                });
            }
        });
    }

    private void setupRecyclerView() {
        mUidList = new ArrayList<>();
        mUsersList = new ArrayList<>();
        mBinding.recyclerView.setHasFixedSize(true); // this will lock the scrolling. We cant scroll
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DataRecyclerAdapter(mUsersList);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DataRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                User user = new User("Sucker", "Sucker", 696969);
                String userUidClicked = mUidList.get(position);
                mUsersList.set(position, user);
                mViewModel.updateUser("/users/" + userUidClicked, user);
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}