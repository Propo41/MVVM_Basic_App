package com.pixeumstudios.mvvmbasic;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.google.firebase.database.DataSnapshot;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private UserRepository userRepo;

    /**
     * Needs to call queryRepo() to make the user instance ready.
     * @return the user instance that is ready at the moment.
     */
    public LiveData<User> getUser() {
        return user;
    }

    /**
     * Called from the Activity to get data
     */
    public void queryRepo(String userId) {
        userRepo = UserRepository.getInstance();
        user = (MutableLiveData<User>) userRepo.getUser(userId);
    }

    public void updateUser(User updatedUser) {
        user.postValue(updatedUser);
    }

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData(String uid) {
        return userRepo.listeningAtDatabase(uid);
    }
}


