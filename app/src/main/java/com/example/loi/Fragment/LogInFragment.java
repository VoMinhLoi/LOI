package com.example.loi.Fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.loi.Activity.HomeActivity;
import com.example.loi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Context context = getContext();
    private SharedPreferences sharedPreferences;
    public LogInFragment() {
    }

    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        EditText nameET = view.findViewById(R.id.nameETLG);
        EditText passET = view.findViewById(R.id.passETLG);
        Button loginBT = view.findViewById(R.id.logInBTLG);
        CheckBox checkBox = view.findViewById(R.id.rememberCB);

        nameET.setText(sharedPreferences.getString("name",""));
        passET.setText(sharedPreferences.getString("pass", ""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked",false));
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString().trim();
                String pass = passET.getText().toString().trim();
                if(name.isEmpty()){
                    nameET.setError("Trống");
                    nameET.requestFocus();
                }
                else
                    if(pass.isEmpty()){
                        passET.setError("Trống");
                        passET.requestFocus();
                    }
                    else{
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.customor_dialog_loading);
                                    dialog.setTitle(Window.FEATURE_NO_TITLE);
                                    dialog.show();
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    if(checkBox.isChecked()){
                                        editor.putString("name",name);
                                        editor.putString("pass", pass);
                                        editor.putBoolean("checked", true);
                                        editor.commit();
                                    }
                                    else{
                                        editor.remove("name");
                                        editor.remove("pass");
                                        editor.remove("checked");
                                        editor.commit();
                                    }
                                    dialog.hide();
                                    startActivity(intent);
                                }
                                else{
                                    nameET.setError("Sai");
                                    passET.setError("Sai");
                                    passET.requestFocus();
                                    Log.w(TAG, "", task.getException());
                                }
                            }
                        });
                    }
            }
        });
        return view;
    }
}