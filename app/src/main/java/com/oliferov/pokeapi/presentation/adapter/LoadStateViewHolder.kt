package com.oliferov.pokeapi.presentation.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oliferov.pokeapi.databinding.PartDefaultLoadStateBinding

typealias TryAgainAction = () -> Unit

class LoadStateViewHolder(
    private val binding: PartDefaultLoadStateBinding,
    private val swipeRefreshLayout: SwipeRefreshLayout?,
    private val tryAgainAction: TryAgainAction
): RecyclerView.ViewHolder(
    binding.root
) {

    init {
        binding.buttonTryAgain.setOnClickListener {
            tryAgainAction
        }
    }

    fun bind(loadState: LoadState) = with(binding){
        tvMessageError.isVisible = loadState is LoadState.Error
        buttonTryAgain.isVisible = loadState is LoadState.Error
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
            progressBar.isVisible = false
        } else {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

}