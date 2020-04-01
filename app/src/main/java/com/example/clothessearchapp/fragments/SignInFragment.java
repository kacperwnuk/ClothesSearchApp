package com.example.clothessearchapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.activities.ChooseTypeActivity;
import com.example.clothessearchapp.activities.MainActivity;
import com.example.clothessearchapp.activities.SignInActivity;
import com.example.clothessearchapp.adapters.TypesRecyclerAdapter;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.Type;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * for more visit http://materialuiux.com
 */

public class SignInFragment extends Fragment implements View.OnClickListener {

//Material UXUI code

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    EditText _Login_editText, _Password_editTextsd;
    Button _Signin_Button;
    TextView errorText;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        /////*     initialize view   */////

        _Login_editText = v.findViewById(R.id.id_Login_editText);
        _Password_editTextsd = v.findViewById(R.id.id_Password_editTextsd);
//        _ForgetPassword_TextView =  v.findViewById(R.id.id_forgotPassword_textView);
        _Signin_Button = v.findViewById(R.id.id_Signin_Button);
        /////*     On Click         */////
        _Signin_Button.setOnClickListener(this);

        errorText = v.findViewById(R.id.errorText);
//        _ForgetPassword_TextView.setOnClickListener(this);


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View v) {

        if (v == _Signin_Button) {
            signinfunction();
        }
    }

    private void signinfunction() {
        /////*   Get  Email && Password    */////
        String _emailS = _Login_editText.getText().toString();
        String _passwordS = _Password_editTextsd.getText().toString();
        /////*   Check if email and password written  valid   */////
        if (!validate(_emailS, _passwordS)) {
            return;
        } else {
            Login(_emailS, _passwordS);
        }

    }

    public boolean validate(String login, String password) {
        boolean valid = true;

        if (login.isEmpty()) {
            _Login_editText.setError("Login cannot be empty");
            valid = false;
        } else {
            _Login_editText.setError(null);
        }

        if (password.isEmpty()) {
            _Password_editTextsd.setError(getString(R.string.validpassword));
            valid = false;
        } else {
            _Password_editTextsd.setError(null);
        }

        return valid;
    }

    private void Login(String login, String password) {
//        Toast.makeText(getContext(), "sign in : success" + login + " " + password, Toast.LENGTH_LONG).show();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);
        Call<String> call = service.getToken(login, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                String token = response.body();
                if (token != null) {
                    saveToken(token);
                    goToMenu();
                    System.out.println(token);
                } else {
                    errorText.setText(getString(R.string.login_error));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });
    }

    private void goToMenu() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(getString(R.string.data), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.token), token);
        editor.apply();
    }
}
