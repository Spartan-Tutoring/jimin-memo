package com.jimin.memoria

import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment(), View.OnClickListener,BaseDialog.BaseDialogClickListener {
    override fun onClick(v: View?) {

    }

    override fun onOKClicked() {

        
    }
}