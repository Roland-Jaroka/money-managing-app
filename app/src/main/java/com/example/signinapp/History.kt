package com.example.signinapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class History: Fragment (R.layout.fragment_history) {

       val db = FirebaseFirestore.getInstance()
       val auth = FirebaseAuth.getInstance()
       val uid = auth.currentUser?.uid

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


          val listView= view.findViewById<ListView>(R.id.listView)


        if (uid!=null) {

            db.collection("users")
                .document(uid)
                .collection("transactions")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                   val transactionsList =  mutableListOf<Transactions>()

                    for (document in result) {

                        val transactions = document.toObject(Transactions::class.java)

                        transactionsList.add(transactions)


                    }

                   val displayList = transactionsList.map{

                       val t = it


                       " Amount: ${t.ts_amount} \n Name: ${t.ts_name} \n Type:  ${t.ts_type} \n Date: ${t.ts_date}"
                   }

                    val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, displayList)
                    listView.adapter= adapter

                }

        }




    }
}