package com.together.nosheng;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Button join, login, reset;
    private EditText email_login;
    private EditText pwd_login;
    private FirebaseAuth firebaseAuth;

    private SignInButton btn_google; // 구글 로그인 버튼
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        join = (Button) findViewById(R.id.join);
        login = (Button) findViewById(R.id.login);
        reset = (Button) findViewById(R.id.Info_Submit);
        email_login = (EditText) findViewById(R.id.email);
        pwd_login = (EditText) findViewById(R.id.log_password);

        btn_google = findViewById(R.id.btn_google);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String pwd = pwd_login.getText().toString().trim();

                if(email.length()>0 && pwd.length()>0) {

                    firebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startToast("로그인 성공!");
                                        startMyActivity(MainActivity.class);
                                    } else {
                                        startToast(task.getException().toString());
                                    }
                                }
                            });
                }else {
                    if(email.length()==0) {
                        startToast("이메일을 입력해주세요");
                    }
                    if(pwd.length()==0){
                        startToast("패스워드를 입력해주세요");
                    }
                }

            }
        });


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(RegisterActivity.class);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(PasswordResetActivity.class);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("568463453042-k859qs4r1cp5km05r9jali44tmo8ln4f.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // 구글 로그인 버튼 클릭 시
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
    }

    // 구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) { // 인증결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); // account 라는 데이터는 구글 로그인 정보를 담고 있다 (닉네임, 프로필사진url, 이메일주소 등등 ..)
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            } else {
                startToast("인증 실패");
            }
        }

    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // 로그인 성공 시
                            startToast("로그인 성공");
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

//                            intent.putExtra("nickName", account.getDisplayName());
//                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); // String.valueof : 특정 자료형을 String 형태로 반환

//                            startActivity(intent);
                            startMyActivity(MainActivity.class);
                        } else { // 로그인 실패 시
                            startToast("로그인 실패");
                        }
                    }
                });
    }

    //    private void signIn() {
//        Intent signInIntent = mG
//    }

    private void startMyActivity(Class c){
        Intent intent = new Intent (LoginActivity.this, c);
        startActivity(intent);
    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_LONG).show();}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
