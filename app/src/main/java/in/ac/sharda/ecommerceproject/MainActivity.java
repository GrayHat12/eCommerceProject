package in.ac.sharda.ecommerceproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText editTextUserName, textPassword;
    Button btnSubmit;
    ListView list;
    ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        textPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        list=(ListView)findViewById(R.id.list);
        listData= new ArrayList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextUserName.getText().toString())) {
                    editTextUserName.setError("Enter Name");
                } else if (TextUtils.isEmpty(textPassword.getText().toString())) {
                    textPassword.setError("Enter Password");
                } else
                {
                    Intent prodList=new Intent(MainActivity.this, ProductList.class);
                    prodList.putExtra("id",editTextUserName.getText()+"");
                    prodList.putExtra("pass",textPassword.getText()+"");
                    startActivity(prodList);
                }
            }
        });
    }
}