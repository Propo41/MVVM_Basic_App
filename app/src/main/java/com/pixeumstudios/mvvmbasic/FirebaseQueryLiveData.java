package com.pixeumstudios.mvvmbasic;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * With FirebaseQueryLiveData, whenever the data from the Query given in the constructor changes,
 * MyValueEventListener triggers with a new DataSnapshot, and it notifies any observers
 * of that using the setValue() method on LiveData
 *
 * LiveData considers an observer to be in an active state if its lifecycle is in the STARTED or
 * RESUMED state. The observer transitions to an inactive state if its lifecycle is in the DESTROYED state.
 *
 * REF: https://firebase.googleblog.com/2017/12/using-android-architecture-components.html
 *      https://medium.com/globallogic-latinoamerica-mobile/viewmodel-firebase-database-3cc708044b5d
 */

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref;
    }

    /**
     * NOTE:
     */
    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive: " + query.getRef().toString());
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive: " + query.getRef().toString());
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.i(LOG_TAG, dataSnapshot.getRef() + "");
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}