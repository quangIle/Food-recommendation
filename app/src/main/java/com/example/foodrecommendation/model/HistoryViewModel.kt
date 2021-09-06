package com.example.foodrecommendation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class HistoryViewModel : ViewModel() {
    val COMPLETED = "Completed"
    val LOADING = "Loading"

    private val databaseUrl =
        "https://cs426-food-recommendation-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseReference = Firebase.database(databaseUrl).reference
    private val historyRef = databaseReference.child("User History")
    private var userHistoryRef =
        historyRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())

    val historyList: MutableList<UserHistory> = ArrayList()
    private val _historyState = MutableLiveData(false)
    val historyState: LiveData<String> = _historyState.map { value ->
        if (value)
            COMPLETED
        else
            LOADING
    }

    lateinit var todayList: MutableSet<String?>
    lateinit var yesterdayList: MutableSet<String?>
    lateinit var weekList: MutableSet<String?>
    lateinit var olderList: MutableSet<String?>

    fun loadUserHistory() {
        _historyState.value = false
        userHistoryRef =
            historyRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
        userHistoryRef.addValueEventListener(historyListRefListener)

    }

    fun clearUserHistory() {
        userHistoryRef =
            historyRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
        userHistoryRef.removeValue()
    }

    private val historyListRefListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            historyList.clear()
            Log.d("Qhis", "${dataSnapshot.value}")
            for (child in dataSnapshot.children) {
                val userHistory =
                    UserHistory(
                        child.value.toString(),
                        LocalDateTime.parse(child.key.toString().replace('I', '.'))
                    )
                historyList.add(userHistory)
            }
            filterData()
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    data class UserHistory(val name: String, val date: LocalDateTime)

    fun writeUserHistory(foodName: String) {
        userHistoryRef =
            historyRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
        userHistoryRef.child(LocalDateTime.now().toString().replace('.', 'I')).setValue(foodName)
    }

    fun filterData() {
        todayList = historyList.map { it ->
            if (it.date.dayOfYear == LocalDateTime.now().dayOfYear)
                it.name
            else
                "null"
        }.toMutableSet()
        todayList.remove("null")
        yesterdayList = historyList.map { it ->
            if (it.date.dayOfYear == LocalDateTime.now().dayOfYear - 1)
                it.name
            else
                "null"
        }.toMutableSet()
        yesterdayList.remove("null")
        weekList = historyList.map { it ->
            if (it.date.dayOfYear < LocalDateTime.now().dayOfYear - 1 && it.date.dayOfYear > LocalDateTime.now().dayOfYear - 8)
                it.name
            else
                "null"
        }.toMutableSet()
        weekList.remove("null")
        olderList = historyList.map { it ->
            if (it.date.dayOfYear < LocalDateTime.now().dayOfYear - 7)
                it.name
            else
                "null"
        }.toMutableSet()
        olderList.remove("null")
        _historyState.value = true
        userHistoryRef.removeEventListener(historyListRefListener)

    }
}