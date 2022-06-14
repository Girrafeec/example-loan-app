package com.girrafeecstud.final_loan_app_zalessky.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoansViewModel

class LoansFragment : Fragment() {

    private lateinit var loansRecView: RecyclerView

    private val loansAdapter = LoansAdapter()

    private val loansViewModel: LoansViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loans_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loansRecView = view.findViewById(R.id.loansRecView)

        loansRecView.adapter = loansAdapter
        loansRecView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        loansViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is LoansViewModel.LoansFragmentState.IsLoading -> handleLoading(state.isLoading)
                is LoansViewModel.LoansFragmentState.SuccessResult -> handleSuccessResult(loans = state.loans)
            }
        })
    }

    private fun handleLoading(isLoading: Boolean) {

    }

    private fun handleSuccessResult(loans: List<Loan>) {
        loansAdapter.refreshLoansList(loans = loans)
    }
}