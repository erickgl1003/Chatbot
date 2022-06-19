package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private MaterialButton mbtnSend;
    private EditText etWriteMessage;
    private ScrollView svChatList;

    private RequestQueue requestQueue;

    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;

    private final String USER_KEY = "user";
    private final String CHATBOT_KEY = "chatbot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvChat = findViewById(R.id.rvChat);
        mbtnSend = findViewById(R.id.mbtnSend);
        etWriteMessage = findViewById(R.id.etWriteMessage);
        svChatList = findViewById(R.id.svChatList);

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.getCache().clear();

        messageArrayList = new ArrayList<>();

        mbtnSend.setOnClickListener(view -> {
            if(etWriteMessage.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Your message can't be empty!",Toast.LENGTH_SHORT).show();
                return;
            }
            sendMessage(etWriteMessage.getText().toString());

            etWriteMessage.getText().clear();
        });

        messageAdapter = new MessageAdapter(messageArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);

        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setAdapter(messageAdapter);
    }

    private void sendMessage(String messageText) {
        messageArrayList.add(new Message(messageText, USER_KEY));
        messageAdapter.notifyDataSetChanged();

        String url = getString(R.string.API_URL) + messageText;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String chatbotResponse = response.getString("cnt");
                messageArrayList.add(new Message(chatbotResponse, CHATBOT_KEY));
            } catch (JSONException e){
                e.printStackTrace();
                messageArrayList.add(new Message("I don't have an answer...", CHATBOT_KEY));
            }
            messageAdapter.notifyDataSetChanged();

        }, error -> {
            error.printStackTrace();
            messageArrayList.add(new Message("*ERROR*", CHATBOT_KEY));
            messageAdapter.notifyDataSetChanged();

        });
        queue.add(jsonObjectRequest);

    }
}