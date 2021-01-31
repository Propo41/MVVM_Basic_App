package com.pixeumstudios.mvvmbasic;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pixeumstudios.mvvmbasic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private MainActivityViewModel viewModel;
    private DataRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final DatabaseReference DB_REF =
            FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mBinding.recyclerView.setHasFixedSize(true); // this will lock the scrolling. We cant scroll
        mLayoutManager = new LinearLayoutManager(this);
        // mAdapter = new DataRecyclerAdapter(exampleItemsList);

        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

       // FirebaseDatabase.getInstance().getReference().child("users/user1").setValue(new User("Arnob", "AUST", 1234));

        viewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);
        viewModel.queryRepo("someUserId");
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    mBinding.textView.setText(user.getName());
                    mBinding.textView2.setText(user.getUniversity());
                    //mBinding.imageView.setImageResource(user.getImgRes());
                }
            }
        });

        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData("user1");
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