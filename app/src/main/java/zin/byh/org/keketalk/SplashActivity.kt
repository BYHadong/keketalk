package zin.byh.org.keketalk

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import zin.byh.org.keketalk.Util.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {

    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val networkCheck = getNetworkCheck()
        //네트워트 상태 체크
        if (networkCheck != null && networkCheck.isConnected) {
            val name = SharedPreferenceUtil.getPreference(applicationContext)
            Handler().postDelayed({
                if (name.equals(""))
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                else
                    startActivity(Intent(applicationContext, ChatActivity::class.java))
                finish()
            },500)
        } else {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("네트워크 연결 상태 확인")
            alertDialog.setMessage("네트워크를 연결하고 다시 시행시켜 주세요.")
            alertDialog.setPositiveButton("확인",  { dialog, which ->
                finish()
            })
            alertDialog.show()
        }
    }

    fun getNetworkCheck(): NetworkInfo? {
        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCheck = connectivityManager.activeNetworkInfo
        return networkCheck
    }
}
