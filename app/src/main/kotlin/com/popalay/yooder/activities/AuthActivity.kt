package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.firebase.client.AuthData
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.snackbar
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.AnkoLogger
import java.util.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), AnkoLogger, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val RC_SIGN_IN = 0
    }

    @Inject lateinit var ref: Firebase

    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Application.graph.inject(this)
        init()
        initUI()
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1081807612231-0vu5eb6n72n3a632llil3avu4eejh03l.apps.googleusercontent.com")
                .requestEmail().build()
        googleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
    }

    private fun initUI() {
        Log.d("dd", "initUi")
        btnLogin.setOnClickListener {
            signOut()
            ref.child("test").setValue("testing")
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("s", "fail")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            Log.d("s", "result")
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d("s", "handle" + result.status)
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            if (acct != null) {
                loginUser(acct)
            }
        }
    }

    private fun loginUser(acct: GoogleSignInAccount?) {

        val authResultHandler = object : Firebase.AuthResultHandler {
            override fun onAuthenticated(authData: AuthData) {
                var map: HashMap<String, String> = HashMap()
                map.put("provider", authData.provider);
                if (authData.providerData?.containsKey("displayName") as Boolean) {
                    map.put("displayName", authData.providerData["displayName"].toString());
                }
                ref.child("users").child(authData.uid).setValue(map);
            }

            override fun onAuthenticationError(error: FirebaseError) {
                snackbar(coordinator, error.message, Snackbar.LENGTH_LONG)
            }

        }
        Log.d("s", "login " + acct?.idToken)
        ref.authWithOAuthToken("google", acct?.idToken, authResultHandler);//todo
    }


    private fun signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->  // ...
        }
    }
}
