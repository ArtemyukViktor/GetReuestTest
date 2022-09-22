package com.example.getreuesttest.interceptors

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(private val context: Context) : Interceptor {

    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,"intercept: no connection",Toast.LENGTH_SHORT).show()
                showDialog(context)

            }
            Log.d("weatherTag", "intercept: no connection" )
            throw IOException()

        }else{
            return chain.proceed(chain.request())

        }
    }

    private fun isOnline():Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val  networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }

    private fun showDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("dialogTitle")
        builder.setMessage("dialogMessage")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(context,"clicked yes",Toast.LENGTH_LONG).show()
        }
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(context,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(context,"clicked No",Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}