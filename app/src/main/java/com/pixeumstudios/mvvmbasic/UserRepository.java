package com.pixeumstudios.mvvmbasic;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
    private static final String TAG = "UserRepository";
    private static UserRepository instance;
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
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

    /**
     *
     * @param url the dir at which we have to find the data
     * @return a dataSnaphsot at that particular position as LiveData
     */
    @NonNull
    public LiveData<DataSnapshot> listeningAtDatabase(String url) {
        liveData = new FirebaseQueryLiveData(database.getReference(url));
        return liveData;
    }

    public void updateUserFromDb(String dir, User user) {
        database.getReference(dir).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, "value updated successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
}
