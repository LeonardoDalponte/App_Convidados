package com.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.myapplication.constants.DataBaseConstants
import com.android.myapplication.databinding.ActivityGuestFormBinding
import com.android.myapplication.model.GuestModel
import com.android.myapplication.viewModel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewmodel: GuestFormViewModel
    private var guestid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()

    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked


            val model = GuestModel().apply {
                this.id = guestid
                this.name = name
                this.presence = presence
            }

            viewmodel.save(model)

        }
    }

    fun observe() {
        viewmodel.guests.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewmodel.saveGuest.observe(this, Observer {
            if (it != "") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }


    fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestid = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewmodel.get(guestid)
        }
    }
}