package com.example.mateuszlopacinskilayoutsandactions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.mateuszlopacinskilayoutsandactions.databinding.ActivityActionsBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityActionsBinding
  private lateinit var user: User

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityActionsBinding.inflate(layoutInflater)
    user = ViewModelProvider(this)[User::class.java]
    user.firstName = MutableLiveData("Alexander")
    user.lastName = MutableLiveData("TheGreat")

    binding.user = user

    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_actions)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    setWriteToLogCatButtonListener()
    setDisplayToastButtonListener()
    setWriteToTextViewButtonListener()
    setUpdateUserDataButtonListener()
  }

  fun writeToLogCat(view: View) {
    Log.i("MyApp", "Message from my App")
  }

  private fun setWriteToLogCatButtonListener() {
    binding.btnWriteToLogcat.setOnClickListener {
      Log.i("MyApp", "Message from the BindListener")
    }
  }

  private fun setDisplayToastButtonListener() {
    binding.btnDisplayToast.setOnClickListener {
      Toast.makeText(this, "This is a Toast message", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setWriteToTextViewButtonListener() {
    binding.btnWriteToTextview.setOnClickListener {
      binding.tvWriteSomething.text = getString(R.string.i_love_this_game)
    }
  }

  private fun setUpdateUserDataButtonListener() {
    binding.btnUpdateUserData.setOnClickListener {
      val fullName = getString(
        R.string.user_full_name,
      user.firstName.value,
        user.lastName.value
      )
      binding.tvWriteSomething.text = fullName
    }
  }
}
