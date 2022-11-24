package com.example.sfutransiter.views

import android.content.pm.PackageManager
import android.os.Bundle
import com.example.sfutransiter.R
import com.example.sfutransiter.databinding.ActivityMainBinding
import com.example.sfutransiter.util.Util
import com.example.sfutransiter.views.bus_summary.BusSummary
import com.example.sfutransiter.views.components.BaseActivity
import com.example.sfutransiter.views.components.DoNotShowAgainAlertDialog
import com.example.sfutransiter.views.search_by.SearchBy
import com.example.sfutransiter.views.select_bus.SelectBus

class MainActivity : BaseActivity(),
    MainFragment.MainFragmentInterface,
    SearchBy.SearchByFragmentInterface,
    SelectBus.SelectBusInterface {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.checkPermissions(this)
        showDisclaimerDialog()
        addFragment(R.id.mainFragmentContainer, MainFragment.newInstance(), MainFragment.TAG, false)
    }

    private fun showDisclaimerDialog() {
        DoNotShowAgainAlertDialog.Builder().apply {
            setTitle(getString(R.string.disclaimer))
            setMessage(getString(R.string.translink_disclaimer))
            setPositiveButton(getString(R.string.i_understand), null)
        }.create().showIfAllowed(this, supportFragmentManager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Util.PERM_REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Util.checkPermissions(this)
                    return
                }
            }
        }
    }

    override fun swapToSearchBy() {
        replaceFragment(R.id.mainFragmentContainer, SearchBy.newInstance(), SearchBy.TAG, false)
    }

    override fun swapToSelectBus() {
        replaceFragment(R.id.mainFragmentContainer, SelectBus.newInstance(), SelectBus.TAG)
    }

    override fun swapToBusSummary(routeId: String) {
        replaceFragment(R.id.mainFragmentContainer, BusSummary.newInstance(routeId), BusSummary.TAG)
    }
}