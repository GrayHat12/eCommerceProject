package in.ac.sharda.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProductList extends AppCompatActivity {

    ListView list;
    ArrayList listData;
    private AsyncHttpClient client;
    private RequestParams params;
    TextView cred;
    public static int cartcount=0;
    TextView cart;
    List<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        list=(ListView)findViewById(R.id.listViewhere);
        cred=(TextView)findViewById(R.id.cred);
        cart=(TextView)findViewById(R.id.cart);
        ids = new LinkedList<>();
        Intent intent=getIntent();
        String uname=intent.getStringExtra("id");
        cred.setText(uname);
        listData= new ArrayList();
        client = new AsyncHttpClient();
        params = new RequestParams();
        client.get("https://gray-application.herokuapp.com/product/list",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String data=new String(responseBody);
                Log.d("recievedData",data);
                try{
                    JSONArray array=new JSONArray(data);
                    for(int i=0;i<array.length();i++) {
                        JSONObject jsnobj = array.getJSONObject(i);
                        int id = jsnobj.getInt("id");
                        ids.add(id);
                        int calories=jsnobj.getInt("calories");
                        int price=jsnobj.getInt("price");
                        String name =jsnobj.getString("name");
                        listData.add(name+"\n$"+price+"\n"+calories+"kcal\n");
                    }
                    ArrayAdapter adapter = new ArrayAdapter(ProductList.this,android.R.layout.simple_list_item_1,listData);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            clickedItem(ids.get(position));
                        }
                    });
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(ProductList.this,"Save",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickedItem(Integer integer) {
        Intent detail=new Intent(this,ProductDetail.class);
        detail.putExtra("id",integer);
        startActivity(detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cart.setText("Cart : "+cartcount);
    }
}
