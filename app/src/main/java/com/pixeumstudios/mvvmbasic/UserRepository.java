package com.pixeumstudios.mvvmbasic;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Handler;

/**
 * Used for getting the data from a source. This data will be used by the view model
 * singleton pattern used since If a different instance was used inside each and every ViewModel,
 * your application would almost certainly start to struggle.
 */
public class UserRepository {
    private static UserRepository instance;
    private static final DatabaseReference DB_REF =
            FirebaseDatabase.getInstance().getReference().child("users");
    private FirebaseQueryLiveData liveData;

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }


    /**
     * A LiveData object does not have a public setValue method, so
     * I need to set the data using a MutableLiveData object.
     * The method is called from the viewModel
     */
    public LiveData<User> getUser(String userId) {
        final MutableLiveData<User> userData = new MutableLiveData<>();
        userData.setValue(getDummyData(userId));
        return userData;
    }

    private User getDummyData(String uid){
        return new User("Ahnaf", "AUST", R.drawable.ic_launcher_foreground);

    }

    @NonNull
    public LiveData<DataSnapshot> listeningAtDatabase(String uid) {
        liveData = new FirebaseQueryLiveData(DB_REF.child(uid));
        return liveData;
    }

}
