package com.rubenquadros.yourplaces.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class ApplicationUtility {

    companion object {
        fun showSnack(msg: String, view: View, action: String){
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction(action) {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }
}