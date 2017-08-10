package myk.project.Activities;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import myk.project.FootballTickets.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        configSplash.setLogoSplash(R.drawable.football);


        //Customize Title
        configSplash.setTitleSplash("Football Ticketing");
        configSplash.setTitleTextColor(R.color.colorWhite);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FadeInUp);
        //configSplash.setTitleFont("fonts/Roboto-Regular.ttf"); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        finish();
    }
}
