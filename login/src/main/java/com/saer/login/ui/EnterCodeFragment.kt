package com.saer.login.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.saer.base_classes.BaseFragment
import com.saer.core.Resources
import com.saer.login.R
import com.saer.login.databinding.FragmentEnterCodeBinding
import com.saer.login.di.LoginComponentViewModel
import dagger.Lazy
import javax.inject.Inject

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<EnterCodeViewModel.Factory>

    private val viewModel: EnterCodeViewModel by viewModels {
        viewModelFactory.get()
    }

    @Inject
    lateinit var resources: Resources

    private val binding: FragmentEnterCodeBinding by viewBinding(CreateMethod.INFLATE)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<LoginComponentViewModel>()
            .loginComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterCodeEditText.doOnTextChanged { code, _, _, _ ->
            viewModel.enterCode(code = code.toString())
        }

        viewModel.observeEnterCodeUi(viewLifecycleOwner) {
            it.apply(
                resources = resources,
                binding = binding,
                viewModel = viewModel
            )
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}