package cj1098.animeshare;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import cj1098.animeshare.userList.ListItem;
import cj1098.animeshare.userList.UserList;


public class MainActivity extends Activity {
    private ArrayList<ListItem> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task task = new task();
        task.execute();

        ActionBar ab = getActionBar();
        ab.setLogo(R.drawable.logo);

        String[] elements = new String[10];
        for (int i = 0; i< 10; i++) {
            elements[i] = String.valueOf(i);
        }

        HomeScreenAdapter adapter = new HomeScreenAdapter(this,elements);


        final ListView list = (ListView) findViewById(R.id.list3d);
        list.setDivider(null);
        list.setAdapter(adapter);
        /**list.setOverscrollHeader();
        list.setOverscrollFooter();*/
        list.setSelector(new ColorDrawable(0x0));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //launch the user into their "list" so they can add/edit it.
                        Log.d("Size", Integer.toString(userList.size()));
                        Intent userList = new Intent(MainActivity.this, UserList.class);
                        userList.putParcelableArrayListExtra("list", MainActivity.this.userList);
                        startActivity(userList);

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData() {


        try{
            HttpClient httpclient = new DefaultHttpClient();

            HttpGet request = new HttpGet();
            URI website = new URI("https://hummingbirdv1.p.mashape.com/anime/steins-gate");
            request.setHeader("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
            request.setURI(website);
            HttpResponse response = httpclient.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line = in.readLine();

            URI website2 = new URI("https://hummingbirdv1.p.mashape.com/anime/1");
            request.setHeader("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
            request.setURI(website2);
            HttpResponse response2 = httpclient.execute(request);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(
                    response2.getEntity().getContent()));

            String line2 = in2.readLine();

            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibilityChecker(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            userList.add(mapper.readValue(line, ListItem.class));
            userList.add(mapper.readValue(line2, ListItem.class));
            Log.d("TEST", mapper.writeValueAsString(userList));

        }catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
    }


    public class task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
