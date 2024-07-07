package com.example.fitsync;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Assistant extends AppCompatActivity {

    RecyclerView rView;
    TextView wText;
    EditText etUserInput;
    ImageButton btnSend;
    List<Message> messageList;
    MessageAdapter adapter;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();
    private static final String TAG = "AssistantActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        wText = findViewById(R.id.welcome);
        rView = findViewById(R.id.recylce);
        etUserInput = findViewById(R.id.etUserInput);
        btnSend = findViewById(R.id.btnSend);
        messageList = new ArrayList<>();

        adapter = new MessageAdapter(messageList);
        rView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rView.setLayoutManager(llm);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = etUserInput.getText().toString().trim();
                if (!question.isEmpty()) {
                    addToChat(question, Message.SENT_BY_ME);
                    callAPI(question);
                    etUserInput.setText("");
                    wText.setVisibility(View.GONE);
                }
            }
        });
    }

    /*void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                adapter.notifyDataSetChanged();
                rView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", question)));
            jsonBody.put("max_tokens", 150);
            jsonBody.put("temperature", 0.7); // Adjust temperature as needed
        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception: " + e.getMessage());
            addResponse("JSON creation error: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer sk-proj-1VljHbEznjah9mEBE7gWT3BlbkFJcn3K5oqVyTG50I8vx9lw")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "API Call Failure: " + e.getMessage());
                addResponse("Failed to respond. Please try again.");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String errorResponse = response.body().string();
                    Log.e(TAG, "Unexpected response code: " + response.code() + ", message: " + errorResponse);
                    addResponse("Failed to get response. Please try again.");
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    Log.d(TAG, "Response: " + responseBody); // Log the response body
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("choices");
                    String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                    addResponse(result.trim());
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                    addResponse("Failed to parse response.");
                }
            }
        });
    }*/
}
