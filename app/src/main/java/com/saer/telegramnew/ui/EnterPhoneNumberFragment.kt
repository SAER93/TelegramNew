package com.saer.telegramnew.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.saer.telegramnew.R
import com.saer.telegramnew.appComponent
import com.saer.telegramnew.common.setPhoneNumberMask
import com.saer.telegramnew.databinding.FragmentEnterPhoneNumberBinding
import com.saer.telegramnew.utils.showKeyboard
import javax.inject.Inject


class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {

    @Inject
    lateinit var viewModel: EnterPhoneNumberFragmentViewModel

    private val binding: FragmentEnterPhoneNumberBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputPhoneNumber.doOnTextChanged { text, _, _, _ ->
            viewModel.inputPhoneNumber(text.toString())
        }

        viewModel.observeEnterPhoneUi(viewLifecycleOwner) {
            it.apply(
                binding.sendCodeButton,
                binding.enterPhoneNumberTitle,
                requireContext(),
                findNavController()
            )
        }

        setPhoneNumberMask(binding.inputPhoneNumber, requireContext())
        showKeyboard()

        binding.sendCodeButton.setOnClickListener {
            viewModel.sendCode()
        }
    }
}