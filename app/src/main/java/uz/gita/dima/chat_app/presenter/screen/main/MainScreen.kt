package uz.gita.dima.chat_app.presenter.screen.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.chat_app.R
import uz.gita.dima.chat_app.data.common.User
import uz.gita.dima.chat_app.databinding.ScreenMainBinding
import uz.gita.dima.chat_app.presenter.adapter.UserAdapter

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main){

    private val binding: ScreenMainBinding by viewBinding()


    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mDbRef: DatabaseReference

    private lateinit var mAuth: FirebaseAuth

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        userList = ArrayList()

        adapter = UserAdapter(requireContext(), userList)

        userRecyclerView = binding.recyclerview
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        userRecyclerView.adapter = adapter

        binding.logOut.setOnClickListener {
//            mAuth.signOut()
//            startActivity(Intent(this, LogIn::class.java))
//            finish()
        }


        mDbRef.child("users").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}