package com.example.lifemaster_xml.total.detox.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifemaster_xml.R
import com.example.lifemaster_xml.databinding.DialogDetoxTimeLockAllowServiceBinding
import com.example.lifemaster_xml.total.detox.adapter.DetoxServiceSettingAdapter
import com.example.lifemaster_xml.total.detox.viewmodel.DetoxTimeLockViewModel

class DetoxTimeLockAllowServiceDialog: DialogFragment(R.layout.dialog_detox_time_lock_allow_service) {

    private lateinit var binding: DialogDetoxTimeLockAllowServiceBinding
    private val viewModel: DetoxTimeLockViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogDetoxTimeLockAllowServiceBinding.bind(view)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.recyclerview.layoutManager = GridLayoutManager(context, 5)
        binding.recyclerview.adapter = DetoxServiceSettingAdapter(viewModel.allowServiceApplications)
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnApply.setOnClickListener {
            val allowServices = viewModel.allowServiceApplications.filter { app -> app.isClicked }
            viewModel.updateAllowServices(allowServices)
            dismiss()
        }
    }

    companion object {
        const val TAG = "DetoxRepeatLockBlockServiceDialog"
    }
}