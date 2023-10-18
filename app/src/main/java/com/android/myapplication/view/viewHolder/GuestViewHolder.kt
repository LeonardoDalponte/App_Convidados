package com.android.myapplication.view.viewHolder

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.databinding.RowGuestBinding
import com.android.myapplication.model.GuestModel
import com.android.myapplication.view.Listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        bind.textName.setOnClickListener {
            listener.onclick(guest.id)
        }

        bind.textName.setOnLongClickListener(View.OnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover?")

                .setPositiveButton(
                    "Sim"
                ) { dialog, which -> listener.onDelete(guest.id) }

                .setNegativeButton(
                    "Não"
                ) { dialog, which -> null }
                .create()
                .show()


            true
        })
    }
}