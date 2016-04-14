package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import java.util.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val RC_SIGN_IN = 0
    }

    @Inject lateinit var ref: Firebase

    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Application.graph.inject(this)
        initUI()
        init()
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
    }

    private fun initUI() {
        btnLogin.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
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

        ref.authWithOAuthToken("google", "<oauth-token>", authResultHandler);//todo
    }

}
