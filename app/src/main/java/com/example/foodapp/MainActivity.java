package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Api.MyApi;
import com.example.foodapp.Api.MyRetrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodapp.Api.MyApi;
import com.example.foodapp.Api.MyRetrofit;
import com.example.foodapp.model.MyUserData;
import com.example.foodapp.model.MyUserData;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etemail,etpass;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :
                        Toast.makeText(getApplicationContext(),"Home Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.search :
                        Toast.makeText(getApplicationContext(),"Search Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.cart_view :
                        Toast.makeText(getApplicationContext(),"Cart view Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                   /* case R.id.cart :
                        Toast.makeText(getApplicationContext(),"Cart Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;*/

                    case R.id.menu_call :
                        Toast.makeText(getApplicationContext(),"Call Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                 /*   case R.id.menu_setting :
                        Toast.makeText(getApplicationContext(),"Setting Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;*/
                    case R.id.account :
                        Toast.makeText(getApplicationContext(),"Account Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

                return true;
            }
        });



  TextView createnewac=findViewById(R.id.createnewac);
        etemail=findViewById(R.id.etemail);
        etpass=findViewById(R.id.mypass);
        // final String email=etemail.getText().toString().trim();
        //final String pass=etpass.getText().toString().trim();
        Button btnlogin=findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logIn();
            }
        });




        createnewac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));


            }
        });
    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logIn() {
        String email=etemail.getText().toString().trim();
        String pass=etpass.getText().toString();
        if (TextUtils.isEmpty(email)){
            etemail.setError("Please Enter Email");
            etemail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)){
            etpass.setError("Please Enter Password");
            etpass.requestFocus();
            return;
        }
      /*  final MyApi api = MyRetrofit.getClient().create(MyApi.class);
        Call<ResponseBody> call = api.createNewAcount(name,email,pass);*/
        final MyApi api = MyRetrofit.getClient().create(MyApi.class);
        Call<MyUserData> call = api.logIn(email,pass);
        //Call<MyUserData> call=MyRetrofit.getInstance().getMyApi().logIn(email,pass);
        call.enqueue(new Callback<MyUserData>() {
            @Override
            public void onResponse(Call<MyUserData> call, Response<MyUserData> response) {

                String myname=response.body().getName();
                String email=response.body().getEmail();
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra("name",myname);
                intent.putExtra("email",email);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<MyUserData> call, Throwable t) {

            }
        });

    }
}