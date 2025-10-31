package com.example.mateuszlopacinskilayoutsandactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class User: ViewModel() {
  var firstName = MutableLiveData<String>()
  var lastName = MutableLiveData<String>()
}