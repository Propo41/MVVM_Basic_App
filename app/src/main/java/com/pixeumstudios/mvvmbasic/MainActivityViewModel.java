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
     * Called from the Activity to initialize the repository
     */
    public void initializeRepo() {
        userRepo = UserRepository.getInstance();
    }

    public void updateUser(String dir, User user) {
        userRepo.updateUserFromDb(dir, user);
    }

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData(String url) {
        return userRepo.listeningAtDatabase(url);
    }
}


