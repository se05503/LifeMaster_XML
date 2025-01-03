package com.example.lifemaster_xml.total.detox.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.lifemaster_xml.R
import com.example.lifemaster_xml.databinding.DialogDetoxRepeatLockBinding
import com.example.lifemaster_xml.total.detox.model.DetoxRepeatLockItem
import com.example.lifemaster_xml.total.detox.model.DetoxTargetApp
import com.example.lifemaster_xml.total.detox.viewmodel.DetoxRepeatLockViewModel

class DetoxRepeatLockDialog: DialogFragment(R.layout.dialog_detox_repeat_lock) {

    private lateinit var binding: DialogDetoxRepeatLockBinding
    private lateinit var targetApp: DetoxTargetApp
    private val viewModel: DetoxRepeatLockViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogDetoxRepeatLockBinding.bind(view)
        setupListeners()
        observing()
    }

    private fun setupListeners() {
        binding.ivOpenMaxTimeSetting.setOnClickListener {
            binding.llMaxTimeClose.visibility = View.GONE
            binding.llMaxTimeOpen.visibility = View.VISIBLE
        }
        binding.ivCloseMaxTimeSetting.setOnClickListener {
            binding.llMaxTimeOpen.visibility = View.GONE
            binding.llMaxTimeClose.visibility = View.VISIBLE
        }
        binding.tvSelectTargetApp.setOnClickListener {
            dismiss()
            val dialog = DetoxRepeatLockTargetAppDialog()
            dialog.isCancelable = false
            dialog.show(parentFragmentManager, DetoxRepeatLockTargetAppDialog.TAG)
        }
        binding.ivSelectTargetApp.setOnClickListener {
            dismiss()
            val dialog = DetoxRepeatLockTargetAppDialog()
            dialog.isCancelable = false
            dialog.show(parentFragmentManager, DetoxRepeatLockTargetAppDialog.TAG)
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnAdd.setOnClickListener {
            if(binding.tvSelectTargetApp.visibility == View.VISIBLE) {
                Toast.makeText(context, "앱을 선택해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                binding.apply {
                    val appIcon = targetApp.appIcon
                    val appName = targetApp.appName
                    val useTime = etUseTimeHour.text.toString().toInt()*60 + etUseTimeMinutes.text.toString().toInt()
                    val lockTime = etLockTimeHour.text.toString().toInt()*60 + etLockTimeMinutes.text.toString().toInt()
                    val maxTime = etMaxTimeHour.text.toString().toInt()*60 + etMaxTimeMinutes.text.toString().toInt()
                    val isMaxTimeLimitSet = etMaxTimeHour.text.isNotBlank() || etMaxTimeMinutes.text.isNotBlank() // 1차 작성 코드
                    viewModel.addRepeatLockApp(DetoxRepeatLockItem(
                        appIcon, appName, useTime, lockTime, maxTime, isMaxTimeLimitSet
                    ))
                }
            }
        }
    }

    private fun observing() {
        viewModel.repeatLockTargetApp.observe(viewLifecycleOwner) {
            targetApp = it
            binding.tvSelectTargetApp.visibility = View.GONE // 리팩토링 필요 (불필요)
            binding.ivSelectTargetApp.setImageResource(it.appIcon)
            binding.ivSelectTargetApp.visibility = View.VISIBLE // 리팩토링 필요 (불필요)
        }
    }

    companion object {
        const val TAG = "DetoxRepeatLockDialog"
    }
}