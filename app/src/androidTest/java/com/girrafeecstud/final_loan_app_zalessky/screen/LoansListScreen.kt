package com.girrafeecstud.final_loan_app_zalessky.screen

import android.view.View
import com.girrafeecstud.final_loan_app_zalessky.R
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object LoansListScreen: Screen<LoansListScreen>() {

    val loansRecView = KRecyclerView (
        builder = { withId(R.id.loansRecView) },
        itemTypeBuilder = { itemType(::LoanItem) }
            )

    class LoanItem(parent: Matcher<View>): KRecyclerItem<LoanItem>(parent) {
        val loanItemTitle = KTextView(parent) { withId(R.id.loanItemTitleTxt) }
        val loanItemLoanAmount = KTextView(parent) { withId(R.id.loanItemLoanAmountTxt) }
        val loanItemLoanState = KTextView(parent) { withId(R.id.loanItemLoanStateTxt) }
    }

}