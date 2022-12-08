package com.example.loi.Fragment;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loi.Activity.HomeActivity;
import com.example.loi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Context context = getContext();

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        EditText nameET = view.findViewById(R.id.nameET);
        EditText passET = view.findViewById(R.id.passET);
        EditText confirmET = view.findViewById(R.id.passConfirmET);
        Button signUpBT = view.findViewById(R.id.signUpBT);
        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString().trim();
                String pass = passET.getText().toString().trim();
                String confirm = confirmET.getText().toString().trim();
                if (name.isEmpty()) {
                    nameET.setError("Trống");
                    nameET.requestFocus();
                } else if (pass.isEmpty()) {
                    passET.setError("Trống");
                    passET.requestFocus();
                } else
                    if(confirm.equals(pass)){
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.customor_dialog_loading);
                                    dialog.setTitle(Window.FEATURE_NO_TITLE);
                                    dialog.show();
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.hide();
                                        }
                                    };
                                    handler.postDelayed(runnable, 3000);
                                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "", task.getException());
                                }
                            }
                        });
                    }
                    else{
                        confirmET.setError("Mật khẩu không trùng khớp");
                        confirmET.requestFocus();
                    }
            }
        });
        return view;
    }
}