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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import johnkagga.me.pearl.models.Post;
import johnkagga.me.pearl.models.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private TextView mTextView;

    private String mUserId = "bnsdbbabdafbdasbasdf";
    private UUID mUUID;


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
     * Write to simultaneous paths the same data.
     *
     * Using updateChildren method.
     */
    private void writeNewPost() {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        mUUID = new UUID(10,5);
        String key = getDatabaseReference().child(Constants.POSTS).push().getKey();
        String userId = "user-id00000000-0000-000a-0000-00000000000c";
        Post post = new Post(userId,"john kagga","The Last ship escape", "Lorem lorem lorem isup lorem isup");
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(Constants.POSTS_PATH + key, postValues);
        childUpdates.put(Constants.USER_POSTS_PATH + userId + "/" + key, postValues );

        getDatabaseReference().updateChildren(childUpdates);
    }


    /**
     * Update the username of the user.
     */
    private void updateUsername() {
        mDatabaseReference = getDatabaseReference();
        mDatabaseReference.child(Constants.USERS).child(mUserId).child(Constants.USERNAME).setValue("Aysher Abbas");
    }

    /**
     * Add User profile data at a users node in the database.
     * UserId is the unique key for the child.
     * user is the value of the data.
     */
    private void setUserProfile() {
        mDatabaseReference = getDatabaseReference();
        User user = new User("John Kagga", "Johnkagga@gmail.com");
        mDatabaseReference.child(Constants.USERS).child(mUserId).setValue(user);
    }

    /**
     * Get the reference to the Firebase database.
     *
     * @return DatabaseReference
     */
    private DatabaseReference getDatabaseReference() {
        return mFirebaseDatabase.getReference();
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
