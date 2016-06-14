package johnkagga.me.pearl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johnkagga.me.pearl.models.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;

    private TextView mTextView;
    private DatabaseReference mDatabaseReference;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.firebase_text);

        //Get a reference to the real time Firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        return rootView;
    }

    /**
     * Add User profile data at a users node in the database.
     * UserId is the unique key for the child.
     * user is the value of the data.
     */
    private void setUserProfile() {
        mDatabaseReference = mFirebaseDatabase.getReference();
        User user = new User("John Kagga", "Johnkagga@gmail.com");
        String userId = "bnsdbbabdafbdasbasdf";
        mDatabaseReference.child(Constants.USERS).child(userId).setValue(user);
    }

    /**
     * Basic data saving
     */
    private void basicWriteAndRead() {
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.MESSAGES_NODE);
        mDatabaseReference.setValue("Kagga John");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                mTextView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
