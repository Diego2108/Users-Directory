package com.godiapper.userdirectory.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.godiapper.userdirectory.R
import com.godiapper.userdirectory.common.entities.UserEntity
import com.godiapper.userdirectory.databinding.ActivityMainBinding
import com.godiapper.userdirectory.editModule.view.EditUserFragment
import com.godiapper.userdirectory.editModule.viewModel.EditUsersViewModel
import com.godiapper.userdirectory.mainModule.adapter.UsersAdapter
import com.godiapper.userdirectory.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: UsersAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mEditUsersViewModel: EditUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.fab.setOnClickListener { launchEditFragment() }

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.getUsers().observe(this, { users ->
            mAdapter.setUsers(users)
        })

        mEditUsersViewModel = ViewModelProvider(this).get(EditUsersViewModel::class.java)
        mEditUsersViewModel.getShowFab().observe(this, { isVisible ->
            if (isVisible) mBinding.fab.show() else mBinding.fab.hide()
        })

        mEditUsersViewModel.getUserSelected().observe(this, { userEntity ->
            mAdapter.add(userEntity)
        })
    }

    private fun launchEditFragment(userEntity: UserEntity = UserEntity()) {
        mEditUsersViewModel.setShowFab(false)
        mEditUsersViewModel.setUserSelected(userEntity)

        val fragment = EditUserFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.recyclerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupRecyclerView() {
        mAdapter = UsersAdapter(mutableListOf(),this)
        mLayoutManager = LinearLayoutManager(this,)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mAdapter
        }

    }
    fun onClick(userEntity: UserEntity) {
        launchEditFragment(userEntity)
    }

    private fun onDeleteUser(userEntity: UserEntity){
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_delete_title))
            .setPositiveButton(R.string.dialog_delete_confirm, {dialogInterface,i ->
                mMainViewModel.deleteUsers(userEntity)
            })
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()
    }
}