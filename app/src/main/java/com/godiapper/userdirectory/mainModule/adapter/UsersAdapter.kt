package com.godiapper.userdirectory.mainModule.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godiapper.userdirectory.R
import com.godiapper.userdirectory.common.entities.UserEntity
import com.godiapper.userdirectory.databinding.ItemUserBinding
import com.godiapper.userdirectory.mainModule.view.MainActivity

class UsersAdapter(private var users: MutableList<UserEntity>, private var listener: MainActivity):
RecyclerView.Adapter<UsersAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users =  users.get(position)

        with(holder){
            setListener(users)

            binding.textName.text = users.name
            binding.textLastName.text = users.lastname
            binding.textLastNameMother.text = users.secondlastname
            binding.textAge.text = users.edad
            binding.textPhone.text = users.phone
            //  Aqui ira la seccion para la carga de imagenes
        }
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(userEntity: List<UserEntity>){
        this.users = users as MutableList<UserEntity>
        notifyDataSetChanged()
    }

    fun add(userEntity: UserEntity){
        if (userEntity.id != 0L)
            if (!users.contains(userEntity)){
                users.add(userEntity)
                notifyItemInserted(users.size-1)
            } else{
                update(userEntity)
            }
    }

    private fun update (userEntity: UserEntity){
        val index = users.indexOf(userEntity)
        if (index != -1){
            users.set(index, userEntity)
            notifyItemChanged(index)
        }
    }


    inner class ViewHolder (view:View):RecyclerView.ViewHolder(view){
        val binding = ItemUserBinding.bind(view)

        fun setListener(userEntity: UserEntity){
            with(binding.root) {
                setOnClickListener { listener.onClick(userEntity) }
                setOnLongClickListener {
                    listener.onDeleteUser(userEntity)
                    true
                }
            }
        }
    }
}