package com.example.waitae;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Authenticator;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AIChatBox extends AppCompatActivity {


    private String apiKey = "AIzaSyCWr16SeoZxpkcywNdoSakPPOh3XtPctUM";
    private String urlEndPoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

    private FloatingActionButton sendButton;
    private EditText inputField;

    private RecyclerView chatView;
    private ArrayList<ChatModel> chatModelArrayList;
    private ChatAdapter chatAdapter;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aichat_box);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputField = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendBtn);
        chatView = findViewById(R.id.chatView);
        chatModelArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatView.setLayoutManager(manager);
        chatView.setAdapter(chatAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputField.getText().toString().isEmpty()){
                    Toast.makeText(AIChatBox.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(inputField.getText().toString());
                inputField.setText("");
            }
        });

    }

    private void getResponse(String message){
        chatModelArrayList.add(new ChatModel(message, USER_KEY));


        JSONObject jObj_parts = new JSONObject();

        JSONObject jObj_contents = new JSONObject();

        JSONObject jObjText = new JSONObject();

        try {

            jObjText.put("text", message);
            jObj_parts.put("parts", jObjText);
            jObj_contents.put("contents", jObj_parts);

            //Log.d("TAG", "The value is: " + jObj_contents);

        } catch (JSONException e) {

            throw new RuntimeException(e);

        }

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, urlEndPoint, jObj_contents, response -> {
            //Log.d("TAG", "The value of the request is: " + jsonReq);

            try {
                Log.d("TAG", "The value of response is: " + response);
                JSONArray responseMessage = response.getJSONArray("candidates").getJSONObject(0)
                        .getJSONObject("content").getJSONArray("parts");

                JSONObject output_Obj = responseMessage.getJSONObject(0);

                //Log.d("TAG", "The value of the text is: " + output_Obj.getString("text"));
                MessageModal modal = new MessageModal();
                modal.setMsg(output_Obj.getString("text"));
                chatModelArrayList.add(new ChatModel(modal.getMsg(), BOT_KEY));
                chatAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.d("TAG", "The value of response is: " + e);
                throw new RuntimeException(e);
            }
        }, error -> {
            if (error == null || error.networkResponse == null) {
                Log.d("TAG", "The value of response is: " + error.getCause());
            }

            String body;
            //get status code here
            assert error.networkResponse != null;
            final String statusCode = String.valueOf(error.networkResponse.statusCode);
            //get response body and parse with appropriate encoding
            try {
                Log.d("TAG", "The statusCode response is: " + statusCode);

                body = new String(error.networkResponse.data,"UTF-8");
                Log.d("TAG", "The body response is: " + body);
            } catch (UnsupportedEncodingException e) {
                // exception
                Log.d("TAG", "The exception response is: " + e);

            }
            chatModelArrayList.add(new ChatModel("Please question not clear", BOT_KEY));
            chatAdapter.notifyDataSetChanged();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeader = new HashMap<>();
                mapHeader.put("Content-Type","application/json ");
                return mapHeader;
            }
        };

        int timeoutPeriod = 60000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutPeriod, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonReq.setRetryPolicy(retryPolicy);
        Volley.newRequestQueue(getApplicationContext()).add(jsonReq);

    }

}