package com.example.sportsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private CredentialManager credentialManager;
    private FirebaseAuth auth;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) { goToMain(); return; }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        credentialManager = CredentialManager.create(this);
        buttonCreateAccount.setOnClickListener(v -> signIn());
    }


    private void signIn()
    {
        GetSignInWithGoogleOption googleOption = new GetSignInWithGoogleOption.Builder(getString(R.string.default_web_client_id)).build();
        GetCredentialRequest request = new GetCredentialRequest.Builder().addCredentialOption(googleOption).build();

        credentialManager.getCredentialAsync(
            this,
            request,
            new CancellationSignal(),
            ContextCompat.getMainExecutor(this),
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(GetCredentialResponse result)
                {
                    handleCredential(result.getCredential());
                }

                @Override
                public void onError(GetCredentialException e)
                {
                    Log.e(TAG, "Sign-in failed", e);
                    Toast.makeText(LoginActivity.this, "Sign-in cancelled or failed", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void saveUserProfile(FirebaseUser user)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("name", user.getDisplayName());
        data.put("photoUrl", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.getUid())
                .set(data, SetOptions.merge());
    }
    private void handleCredential(Credential credential)
    {
        if (credential instanceof CustomCredential && credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL))
        {
            GoogleIdTokenCredential google = GoogleIdTokenCredential.createFrom(((CustomCredential) credential).getData());
            AuthCredential firebaseCred = GoogleAuthProvider.getCredential(google.getIdToken(), null);

            auth.signInWithCredential(firebaseCred).addOnCompleteListener(this, task -> {
                if (task.isSuccessful())
                {
                    saveUserProfile(auth.getCurrentUser());
                    goToMain();
                } else {
                    Log.e(TAG, "Firebase auth failed", task.getException());
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "Unexpected credential type: " + credential.getType());
        }
    }
    void goToMain()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

