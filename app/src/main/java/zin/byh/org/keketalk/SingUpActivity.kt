package zin.byh.org.keketalk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import zin.byh.org.keketalk.data.User

class SingUpActivity : AppCompatActivity() {

    lateinit var editName : TextInputEditText
    lateinit var editId : TextInputEditText
    lateinit var editPassword : TextInputEditText
    lateinit var editRePassword : TextInputEditText
    lateinit var singUpButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        editName = findViewById(R.id.editName)
        editId = findViewById(R.id.editId)
        editPassword = findViewById(R.id.editPassword)
        editRePassword = findViewById(R.id.editRePassword)
        singUpButton = findViewById(R.id.singUpButton)
        singUpButton.setOnClickListener {
            view ->
            if(editPassword.text.toString().equals(editRePassword.text.toString())){
                singUp()
                startActivity(Intent(this@SingUpActivity, LoginActivity::class.java))
                Toast.makeText(this@SingUpActivity, "회원가입이 되셨습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Snackbar.make(view, "비밀번호가 다릅니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun singUp(){
        val userDatabase = FirebaseDatabase.getInstance().getReference()
        val user = User(editName.text.toString(), editId.text.toString(), editPassword.text.toString())
        userDatabase.child("users").child(editId.text.toString()).setValue(user)
    }
}
