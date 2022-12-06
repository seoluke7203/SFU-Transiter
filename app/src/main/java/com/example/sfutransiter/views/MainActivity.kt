package com.example.sfutransiter.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.sfutransiter.R
import com.example.sfutransiter.databinding.ActivityMainBinding
import com.example.sfutransiter.util.Util
import com.example.sfutransiter.views.bus_summary.BusSummary
import com.example.sfutransiter.views.comment_board.CommentBoard
import com.example.sfutransiter.views.components.BaseActivity
import com.example.sfutransiter.views.components.DoNotShowAgainAlertDialog
import com.example.sfutransiter.views.register.Register
import com.example.sfutransiter.views.search_by.SearchBy
import com.example.sfutransiter.views.select_bus.SelectBus
import com.example.sfutransiter.views.select_station.SelectStation

private var shown = false

class MainActivity : BaseActivity(),
    MainFragment.MainFragmentInterface,
    SearchBy.SearchByFragmentInterface,
    SelectBus.SelectBusInterface,
    SelectStation.SelectStationInterface,
    BusSummary.BusSummaryInterface,
    Register.RegisterInterface {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.checkPermissions(this)
        if (!checkGPSPermission()) {
            requestGPSPermission()
        }
        showDisclaimerDialog()
        if (!shown)
            addFragment(
                R.id.mainFragmentContainer,
                MainFragment.newInstance(),
                MainFragment.TAG,
                false
            )
        shown = true
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

    private fun checkGPSPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
    }

    private fun requestGPSPermission() {
        return ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0
        )
    }

    override fun swapToSearchBy() {

        replaceFragment(R.id.mainFragmentContainer, SearchBy.newInstance(), SearchBy.TAG, false)
    }

    override fun swapToRegister() {
        replaceFragment(R.id.mainFragmentContainer, Register.newInstance(), Register.TAG)
    }

    @SuppressLint("ResourceType")
    override fun swapToSelectBus() {
        val transaction: FragmentTransaction =
            this.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter,   // new fragment entering
                    R.anim.exit,    // existing fragment
                    R.anim.enter,    // remaining fragment when exiting
                    R.anim.exit)    // animation when exisitng
                .replace(R.id.mainFragmentContainer, SelectBus.newInstance(), SelectBus.TAG)
        transaction.addToBackStack(null).commitAllowingStateLoss()
//        replaceFragment(R.id.mainFragmentContainer, SelectBus.newInstance(), BusSummary.TAG)
    }

    override fun swapToSelectStation() {
        overridePendingTransition(R.anim.enter, R.anim.exit)
        val transactions: FragmentTransaction =
            this.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.right_enter,
                    R.anim.left_exit,
                    R.anim.left_enter,
                    R.anim.right_exit
                )
                .replace(R.id.mainFragmentContainer, SelectStation.newInstance(), BusSummary.TAG)
        transactions.addToBackStack(null).commitAllowingStateLoss()
//        replaceFragment(R.id.mainFragmentContainer, SelectStation.newInstance(), BusSummary.TAG)

    }

    override fun swapToBusSummary(routeId: String) {
        replaceFragment(R.id.mainFragmentContainer, BusSummary.newInstance(routeId), BusSummary.TAG)
    }

    override fun swapToCommentBoard(routeNo: String) {
        replaceFragment(
            R.id.mainFragmentContainer,
            CommentBoard.newInstance(routeNo),
            CommentBoard.TAG
        )
    }

    override fun popBackToMain() {
        popBackStack()
    }
}