package suucdcc.hekate;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PinFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PinFragment extends Fragment {

        public PinFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public class Command{
        public String room;

        public Command(String room){
            this.room = room;
        }
    }

    public void sendButton(View view){
        EditText pass = (EditText)findViewById(R.id.password);
        Firebase firebase = new Firebase("https://hekate.firebaseio.com/");
        String value = pass.getText().toString();
        Command c = new Command(value);
        firebase.child("UserWow").setValue(c);
    }

    public void sendPass(View view){
        EditText pass = (EditText)findViewById(R.id.password);
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    Firebase firebase = new Firebase("https://hekate.firebaseio.com/");
                    String value = v.getText().toString();
                    Command c = new Command(value);
                    firebase.child("UserWow").setValue(c);
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void logout(){
        /*
        Firebase ref = new Firebase("https://hekate.firebaseio.com");
        ref.unauth();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        */
    }
}
