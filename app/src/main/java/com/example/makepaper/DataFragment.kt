package com.example.makepaper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_data.*

class DataFragment : Fragment() {

    companion object {
        fun newInstance(): DataFragment = DataFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)

        rv_questions?.setHasFixedSize(true)
        rv_questions?.layoutManager = LinearLayoutManager(context)
        val listData = mutableListOf<ListQuestions>()

        val nm = FirebaseDatabase.getInstance().getReference("data")
        nm.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (que in dataSnapshot.children) {
                        val l: ListQuestions? = que.getValue(ListQuestions::class.java)
                        if (l != null) {
                            listData.add(l)
                            Log.i("Data:", "found")
                        }
                        else {
                            Log.i("Data:","Data not found")
                        }
                    }
                    rv_questions?.adapter = QuestionsAdapter(listData)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        return view
    }
}
