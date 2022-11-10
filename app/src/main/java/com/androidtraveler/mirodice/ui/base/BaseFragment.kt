package com.androidtraveler.mirodice.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding> @JvmOverloads constructor(
    @LayoutRes private val layoutResId: Int = -1,
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, vg: ViewGroup?, sis: Bundle?) =
        if (layoutResId > 0) {
            _binding = inflate.invoke(inflater, vg, false)
            binding.root
        } else null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun navigateSafe(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination) }
    }
}