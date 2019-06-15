package in.ac.sharda.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Cart extends AppCompatActivity {

    ListView list;
    ArrayList listData;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        list=(ListView)findViewById(R.id.list);
        listData= new ArrayList();
        Intent intent=getIntent();
        final ArrayList<Integer> ids=intent.getIntegerArrayListExtra("ids");
        client=new AsyncHttpClient();
        for(int i=0;i<ids.size();i++)
        {
            client.get("https://gray-application.herokuapp.com/pdroduct/detail/" + ids.get(i), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try
                    {
                        String data=new String(responseBody);
                        Log.d("recievedData",data);
                        JSONObject jsonObject=new JSONObject(data);
                        int id = jsonObject.getInt("id");
                        int calories=jsonObject.getInt("calories");
                        int price=jsonObject.getInt("price");
                        String name =jsonObject.getString("name");
                        String desc=jsonObject.getString("desc");
                        listData.add(name+"\n"+calories+"kcal\n$"+price+"\n"+desc);
                        ArrayAdapter adapter = new ArrayAdapter(Cart.this,android.R.layout.simple_list_item_1,listData);
                        list.setAdapter(adapter);
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(Cart.this,"Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
