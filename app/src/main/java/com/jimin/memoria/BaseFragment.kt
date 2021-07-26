package com.jimin.memoria

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment(fragmentActivity: FragmentActivity) : Fragment(), View.OnClickListener,BaseDialog.BaseDialogClickListener {
    override fun onClick(v: View?) {

    }

    override fun onOKClicked() {


    }
}