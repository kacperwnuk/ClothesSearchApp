package com.example.clothessearchapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.activities.SignInActivity;
import com.example.clothessearchapp.materialux.cairoButton;
import com.example.clothessearchapp.materialux.cairoEditText;
import com.example.clothessearchapp.materialux.cairoTextView;
import com.example.clothessearchapp.network.GetDataService;
import com.example.clothessearchapp.network.RetrofitClientInstance;
import com.example.clothessearchapp.structure.UserCredentials;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * for more visit http://materialuiux.com
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {
//Material UXUI code

    cairoEditText _loginEditText, _emailEditText, _passwordEditText;
    cairoButton _signUpButton;
    cairoTextView _privicePolice;

    public SignUpFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        /////*     initialize view   */////
        _loginEditText = view.findViewById(R.id.id_fullName_EditText);
        _emailEditText = view.findViewById(R.id.id_email_EditText);
        _passwordEditText = view.findViewById(R.id.id_password_EditText);
        _signUpButton = view.findViewById(R.id.id_signUp_Button);
//        _privicePolice = view.findViewById(R.id.id_privacyPolicy_TextView);


        /////*     On Click         */////
        _signUpButton.setOnClickListener(this);
//        _privicePolice.setOnClickListener(this);

        return view;
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
    public void onClick(View v)
    {
        if (v==_signUpButton)
        {
            signupfunction();
        }
        if (v== _privicePolice)
        {
            showprivicypolicy();
        }

    }



    private void signupfunction()
    {

        /////*   Get  Email  && Name  && Password    */////
        String fullName = _loginEditText.getText().toString();
        String email = _emailEditText.getText().toString();
        String password = _passwordEditText.getText().toString();

        /////*   Check if email and password written and valid   */////
        if (!validate())
        {
            return;
        }else
        {
            signup(fullName,email,password);
        }
    }


    public boolean validate()
    {
        boolean valid = true;

        String login = _loginEditText.getText().toString();
        String email = _emailEditText.getText().toString();
        String password = _passwordEditText.getText().toString();

        if (login.isEmpty())
        {
            _loginEditText.setError(getString(R.string.enteryourname));
            valid = false;
        } else
        {
            _loginEditText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _emailEditText.setError(getString(R.string.validemail));
            valid = false;
        } else
        {
            _emailEditText.setError(null);
        }

        if (password.isEmpty())
        {
            _passwordEditText.setError(getString(R.string.validpassword));
            valid = false;
        } else
        {
            _passwordEditText.setError(null);
        }

        return valid;
    }



    private void signup(String login, String email, String password)
    {
        /////*   Sign Up : success  */////
//        Toast.makeText(getContext(),"Sign Up : success" + login +" " + email +" " + password ,Toast.LENGTH_LONG).show();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance(false).create(GetDataService.class);
        Call<UserCredentials> call = service.register(login, email, password);
        call.enqueue(new Callback<UserCredentials>() {
            @Override
            public void onResponse(Call<UserCredentials> call, Response<UserCredentials> response) {
                progressDialog.dismiss();
                int code = response.code();
                if (code == 201) {
                    System.out.println(code);
                    Toast.makeText(getContext(),"Zarejestrowano, przejdź do logowania!" ,Toast.LENGTH_LONG).show();
                } else {
//                    errorText.setText(getString(R.string.login_error));
                    _loginEditText.setError(getString(R.string.username_exists));
                }

            }

            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
            }
        });
    }



    private void showprivicypolicy()
    {
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(getContext(), R.style.DialogTheme);

        alertDialogBuilder.setTitle(getString(R.string.privacypolicy));
        alertDialogBuilder
                .setMessage(getString(R.string.privacypolicyterms))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.OK),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        dialog.cancel();
                    }
                });


        /////*    create alert dialog   */////
        AlertDialog alertDialog = alertDialogBuilder.create();

        /////*    show it   */////
        alertDialog.show();
    }

}
