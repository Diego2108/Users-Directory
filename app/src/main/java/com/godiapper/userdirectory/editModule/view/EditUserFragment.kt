package com.godiapper.userdirectory.editModule.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.godiapper.userdirectory.R
import com.godiapper.userdirectory.common.entities.UserEntity
import com.godiapper.userdirectory.databinding.FragmentEditUserBinding
import com.godiapper.userdirectory.editModule.viewModel.EditUsersViewModel
import com.godiapper.userdirectory.mainModule.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class EditUserFragment : Fragment() {

    private lateinit var mBinding: FragmentEditUserBinding
    //MVVM
    private lateinit var mEditUserViewModel: EditUsersViewModel

    private var mActivity: MainActivity? = null
    private var mIsEditMode: Boolean = false
    private lateinit var mUserEntity: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEditUserViewModel = ViewModelProvider(requireActivity()).get(EditUsersViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        mBinding = FragmentEditUserBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        setupTextFields()
    }

    private fun setupViewModel() {
        mEditUserViewModel.getUserSelected().observe(viewLifecycleOwner,{
            mUserEntity = it
            if (it.id != 0L){
                mIsEditMode =  true
                setUiUser(it)
            } else{
                mIsEditMode = false
            }
            setupActionBar()
        })

        mEditUserViewModel.getResult().observe(viewLifecycleOwner, { result ->
            hideKeyboard()

            when(result){
                is Long -> {
                    mUserEntity.id = result

                    mEditUserViewModel.setUserSelected(mUserEntity)

                    Toast.makeText(mActivity,
                        R.string.edit_store_message_save_success, Toast.LENGTH_SHORT).show()

                    mActivity?.onBackPressed()
                }
                is UserEntity -> {
                    mEditUserViewModel.setUserSelected(mUserEntity)

                    Snackbar.make(mBinding.root,
                        R.string.edit_store_message_update_success,
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = if (mIsEditMode) getString(R.string.edit_store_title_edit)
        else getString(R.string.edit_store_title_add)

        setHasOptionsMenu(true)
    }

    private fun setupTextFields() {
        with(mBinding) {
            etName.addTextChangedListener{validateFields(tilName)}
            etLastName.addTextChangedListener{validateFields(tilLastName)}
            etSecondLastName.addTextChangedListener { validateFields(tilSecondLastName) }
            etPhone.addTextChangedListener { validateFields(tilPhone) }
            etAge.addTextChangedListener { validateFields(tilAge) }
        }
    }

    private fun setUiUser(userEntity: UserEntity) {
        with(mBinding){
            etName.text = userEntity.name.editable()
            etLastName.text = userEntity.lastname.editable()
            etSecondLastName.text = userEntity.secondlastname.editable()
            etAge.text = userEntity.edad.editable()
            etPhone.text = userEntity.phone.editable()
        }

    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                if (validateFields(mBinding.tilLastName, mBinding.tilSecondLastName, mBinding.tilName)) {
                    with(mUserEntity) {
                        name = mBinding.etName.text.toString().trim()
                        lastname = mBinding.etLastName.toString().trim()
                        secondlastname = mBinding.etSecondLastName.toString().trim()
                        phone = mBinding.etPhone.text.toString().trim()
                        edad = mBinding.etAge.text.toString().trim()
                    }

                    if (mIsEditMode) mEditUserViewModel.updateUser(mUserEntity)
                    else mEditUserViewModel.saveUser(mUserEntity)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid =  true

        for (textField in textFields){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
        }

        if (!isValid) Snackbar.make(mBinding.root,
            R.string.edit_store_message_valid,
            Snackbar.LENGTH_SHORT).show()

        return isValid

    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null){
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mEditUserViewModel.setShowFab(true)
        mEditUserViewModel.setResult(Any())

        setHasOptionsMenu(false)
        super.onDestroy()
    }




}


