package zin.byh.org.keketalk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import zin.byh.org.keketalk.Util.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {

    val SPLASH_ACTIVITY_LODING_TIME : Long = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(object : Runnable{
            override fun run() {
                val name = SharedPreferenceUtil.getPreference(this@SplashActivity)
                if (name != ""){
                    startActivity(Intent(this@SplashActivity, ChatActivity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }, SPLASH_ACTIVITY_LODING_TIME)
    }
}
