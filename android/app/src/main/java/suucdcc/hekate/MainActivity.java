package suucdcc.hekate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class MainActivity extends Activity {

    private String[] user_opts;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private final Firebase firebase = new Firebase("https://hekate.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.drawer_layout, new PinFragment())
                    .commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0,Menu.FIRST, Menu.NONE, firebase.getAuth().getUid());
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
        String value = pass.getText().toString();
        Command c = new Command(value);
        firebase.child(firebase.getAuth().getUid()).setValue(c);
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
                    String uid = firebase.getAuth().getUid();
                    firebase.child("User/"+uid).setValue(c);
                    handled = true;
                }
                return handled;
            }
        });
    }

    public boolean logout(MenuItem item){
        firebase.unauth();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
