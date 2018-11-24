package zin.byh.org.keketalk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import zin.byh.org.keketalk.Util.SharedPreferenceUtil
import zin.byh.org.keketalk.data.User

class LoginActivity : AppCompatActivity() {

    lateinit var editId: EditText
    lateinit var editPassword: EditText
    lateinit var loginButton: Button
    lateinit var singUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editId = findViewById(R.id.editId)
        editPassword = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            login()
        }
        singUpButton = findViewById(R.id.singUpButton)
        singUpButton.setOnClickListener {
            startActivity(Intent(this, SingUpActivity::class.java))
        }
    }

    fun login() {
        val id = editId.text.toString()
        val passwd = editPassword.text.toString()
        val database = FirebaseDatabase.getInstance().getReference()
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("LoginActivityCancelled", "onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("LoginActivityDataChange", "onDataChange")
                if (id.equals("") && passwd.equals("")) {
                    Snackbar.make(window.decorView.rootView, "아이디와 비밀번호의 값을 입력하세요.", Toast.LENGTH_SHORT).show()
                    return
                }
                for (data in p0.children) {
                    if (data.getValue(User::class.java)!!.userId.equals(id)
                            && data.getValue(User::class.java)!!.userPassword.equals(passwd)) {
                        SharedPreferenceUtil.savePreference(this@LoginActivity, data.getValue(User::class.java)!!.userName)
                        startActivity(Intent(this@LoginActivity, ChatActivity::class.java))
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                Snackbar.make(window.decorView.rootView, "아이디또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}