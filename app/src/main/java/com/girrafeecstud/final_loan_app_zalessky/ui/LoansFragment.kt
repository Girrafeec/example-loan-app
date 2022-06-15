package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoansViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity.LoanActivity

class LoansFragment :
    Fragment(),
    LoansAdapterViewHolder.OnLoanItemClickListener {

    private lateinit var loansRecView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val loansAdapter = LoansAdapter(listener = this)

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
        progressBar = requireActivity().findViewById(R.id.mainActivityProgressBar)

        loansRecView.adapter = loansAdapter
        loansRecView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        loansViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(loans = state.data as List<Loan>)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
    }

    override fun onLoanItemBodyClickListener(loanId: Long) {
        val intent = Intent(activity?.applicationContext, LoanActivity::class.java)
        intent.putExtra("LOAN_ID", loanId)
        startActivity(intent)
    }

    private fun handleLoading(isLoading: Boolean) {
        when (isLoading) {
            false -> {
                view?.alpha = (1).toFloat()
                view?.isEnabled = true
                progressBar.alpha = (0).toFloat()
            }
            true -> {
                view?.alpha = (0).toFloat()
                view?.isEnabled = false
                progressBar.alpha = (1).toFloat()
            }
        }
    }

    private fun handleSuccessResult(loans: List<Loan>) {
        loansAdapter.refreshLoansList(loans = loans)
    }

    private fun handleError(apiError: ApiError) {

        var errorMessage = apiError.errorType.name

        when (apiError.errorType) {
            ApiErrorType.BAD_REQUEST_ERROR -> {

            }
            ApiErrorType.UNAUTHORIZED_ERROR -> {

            }
            ApiErrorType.RESOURCE_FORBIDDEN_ERROR -> {

            }
            ApiErrorType.NOT_FOUND_ERROR -> {

            }
            ApiErrorType.NO_CONNECTION_ERROR -> {

            }
            ApiErrorType.TIMEOUT_EXCEEDED_ERROR -> {

            }
            ApiErrorType.UNKNOWN_ERROR -> {

            }
        }
        Toast.makeText(activity?.applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

}