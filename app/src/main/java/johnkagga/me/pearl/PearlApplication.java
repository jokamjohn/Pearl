package johnkagga.me.pearl;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class PearlApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Set Firebase offline data persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
