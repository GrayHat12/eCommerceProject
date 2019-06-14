package in.ac.sharda.ecommerceproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductDetail extends AppCompatActivity {

    TextView textView;
    Button button;
    private AsyncHttpClient client;
    byte[] response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        textView=(TextView)findViewById(R.id.detail);
        button=(Button)findViewById(R.id.buy);
        Intent intent=getIntent();
        final int id=Integer.parseInt(""+intent.getExtras().get("id"));
        client = new AsyncHttpClient();
        client.get("https://gray-application.herokuapp.com/product/detail/" + id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try
                {
                    response=responseBody;
                    String data=new String(responseBody);
                    Log.d("recievedData",data);
                    JSONObject jsonObject=new JSONObject(data);
                    int id = jsonObject.getInt("id");
                    int calories=jsonObject.getInt("calories");
                    int price=jsonObject.getInt("price");
                    String name =jsonObject.getString("name");
                    String desc=jsonObject.getString("desc");
                    textView.setText(name+"\n"+calories+"kcal\n$"+price+"\n"+desc);
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(ProductDetail.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedBuy(response);
            }
        });
    }

    private void clickedBuy(byte[] id) {
        Toast.makeText(ProductDetail.this,"Buy",Toast.LENGTH_SHORT).show();
        ProductList.cartcount+=1;
        client = new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("product",response);
        client.post("https://gray-application.herokuapp.com1",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String data=new String(responseBody);
                Log.d("paramS",data);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String data=new String(responseBody);
                Log.d("params",data);
                error.printStackTrace();
            }
        });
    }
}
