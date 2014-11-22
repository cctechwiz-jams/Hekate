package suucdcc.hekate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private final Firebase firebase = new Firebase("https://hekate.firebaseio.com/");

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) getFragmentManager().beginTransaction()
                .add(R.id.drawer_layout, new PinFragment())
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        firebase.child("User/"+firebase.getAuth().getUid()+"/Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] rooms = dataSnapshot.getValue().toString().replaceAll("[{}]", "").split(",");
                String[] roomTitles = new String[rooms.length];
                int i = 0;
                for(String s : rooms){
                    roomTitles[i] = s.split("=")[0];
                    System.out.println(roomTitles[i]);
                    i++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
        String[] roomTitles = {"Kitchen", "Bedroom","Garage"};

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.fragment_navigation_drawer, roomTitles));

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        if (savedInstanceState == null) {
            selectItem(0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0,Menu.FIRST, Menu.NONE, firebase.getAuth().getUid());
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void selectItem(int position) {
        Fragment fragment = new RoomFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public static class RoomFragment extends Fragment {

        public RoomFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        }
    }


    public static class PinFragment extends Fragment {

        public PinFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

    public void submit(){
        firebase.child("User/"+firebase.getAuth().getUid()+ "/Pin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditText pass = (EditText)findViewById(R.id.password);
                String value = pass.getText().toString();
                String pin = dataSnapshot.getValue().toString();
                if(pin.equals(value)) {
                    final String location = "User/"+firebase.getAuth().getUid()+"/Rooms";
                    Map<String, Object> dictionary = new HashMap<String, Object>();
                    String current_room = getString(R.string.current_room);
                    Boolean isLocked = Boolean.parseBoolean(((TextView) findViewById(R.id.isLocked)).getText().toString());
                    if(isLocked){
                        dictionary.put(current_room,"unlocked");
                        ((TextView) findViewById(R.id.isLocked)).setText("false");
                    }
                    else {
                        dictionary.put(current_room, "locked");
                        ((TextView) findViewById(R.id.isLocked)).setText("true");
                    }
                    firebase.child(location).updateChildren(dictionary);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed" + firebaseError.getMessage());
            }
        });
    }

    public void sendButton(View view){
        submit();
    }

    public void sendPass(View view){
        EditText pass = (EditText)findViewById(R.id.password);
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    submit();
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
