package com.saer.telegramnew.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.saer.telegramnew.R
import com.saer.telegramnew.appComponent
import com.saer.telegramnew.base.BaseFragment
import com.saer.telegramnew.databinding.FragmentEnterCodeBinding
import javax.inject.Inject

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    @Inject
    lateinit var viewModel: EnterCodeViewModel

    private val binding: FragmentEnterCodeBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterCodeEditText.doOnTextChanged { code, _, _, _ ->
            viewModel.enterCode(code.toString())
        }

        viewModel.observeEnterCodeUi(viewLifecycleOwner) {
            it.apply(requireContext(), binding, viewModel)
        }
    }
}