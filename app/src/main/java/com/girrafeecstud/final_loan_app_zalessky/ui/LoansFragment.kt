package com.girrafeecstud.final_loan_app_zalessky.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoansViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity.LoanActivity
import com.girrafeecstud.final_loan_app_zalessky.utils.BundleConfig

class LoansFragment :
    Fragment(),
    LoansAdapterViewHolder.OnLoanItemClickListener,
    View.OnClickListener {

    private lateinit var requestLoan: Button
    private lateinit var loansRecView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout

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

        requestLoan = view.findViewById<Button>(R.id.getLoanFromLoansFragmentBtn)
        loansRecView = view.findViewById(R.id.loansRecView)
        progressBar = requireActivity().findViewById(R.id.mainActivityProgressBar)
        refreshLayout = view.findViewById(R.id.refreshLoansLayout)

        refreshLayout.setColorSchemeColors(requireActivity().resources.getColor(R.color.purple_700))

        requestLoan.setOnClickListener(this)

        loansRecView.adapter = loansAdapter
        loansRecView.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )

        subscribeObservers()

        refreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                loansViewModel.getRemoteLoansList()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.getLoanFromLoansFragmentBtn -> {
                openRequestLoanActivity()
            }
        }
    }

    override fun onLoanItemBodyClickListener(actionBarTitle: String, loanId: Long) {
        val intent = Intent(activity?.applicationContext, LoanActivity::class.java)
        intent.putExtra(BundleConfig.LOAN_ID_BUNDLE, loanId)
        intent.putExtra(BundleConfig.ACTION_BAR_TITLE_BUNDLE, actionBarTitle)
        startActivity(intent)
    }

    private fun openRequestLoanActivity() {
        val intent = Intent(activity, LoanRequestActivity::class.java)
        startActivity(intent)
    }

    private fun subscribeObservers() {

        loansViewModel.getLoans().observe(viewLifecycleOwner, { loans->
            loansAdapter.refreshLoansList(loans = loans)
        })

        loansViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(loans = state.data as List<Loan>)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })

    }

    private fun handleLoading(isLoading: Boolean) {
        when (isLoading) {
            false -> {
                view?.alpha = (1).toFloat()
                view?.isEnabled = true
                progressBar.alpha = (0).toFloat()
                refreshLayout.isRefreshing = false
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

        var errorMessage = ""
        var errorTitle = ""

        when (apiError.errorType) {
            ApiErrorType.BAD_REQUEST_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
            ApiErrorType.UNAUTHORIZED_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.auth_error_title)
                errorMessage = requireActivity().resources.getString(R.string.auth_error_message)
            }
            ApiErrorType.RESOURCE_FORBIDDEN_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.forbidden_error_title)
                errorMessage = requireActivity().resources.getString(R.string.forbidden_error_message)
            }
            ApiErrorType.NOT_FOUND_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
            ApiErrorType.NO_CONNECTION_ERROR -> {
                Toast.makeText(
                    activity?.applicationContext,
                    activity?.resources?.getString(R.string.no_connection_error),
                    Toast.LENGTH_SHORT)
                    .show()
                return
            }
            ApiErrorType.TIMEOUT_EXCEEDED_ERROR -> {
                Toast.makeText(
                    activity?.applicationContext,
                    activity?.resources?.getString(R.string.connection_timeout_error),
                    Toast.LENGTH_SHORT)
                    .show()
                return
            }
            ApiErrorType.UNKNOWN_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
        }
        showErrorDialog(errorTitle = errorTitle, errorMessage = errorMessage)
    }

    private fun showErrorDialog(errorTitle: String, errorMessage: String) {
        AlertDialog.Builder(context)
            .setTitle(errorTitle)
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

}