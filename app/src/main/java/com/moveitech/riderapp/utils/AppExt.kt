package com.moveitech.riderapp.utils


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import com.moveitech.riderapp.R
import com.moveitech.riderapp.databinding.CustomAlertDialogBinding
import com.moveitech.riderapp.utils.Constants.Companion.LOCATION_PERMISSION_CODE
import java.text.SimpleDateFormat
import java.util.*


fun Fragment.showToast(message:String)
{
    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message:String)
{
    val snackbar = Snackbar
        .make(this.requireView(), message, Snackbar.LENGTH_LONG)
    snackbar.show()}

fun Fragment.showAlertDialog(msg: String) {
    var newMessage = msg
    if (newMessage.isEmpty()) {
        newMessage = "Unable to process your request \nPlease try again later !!"
    }
//    AlertMessageDialog.newInstance(newMessage)
//        .show(requireActivity().supportFragmentManager, AlertMessageDialog.TAG)
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun getDate(): String {
    val calendar = Calendar.getInstance()

    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"	)
    return format.format(calendar.time)
}

fun Context.getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
    var strAdd = ""
    val geocoder = Geocoder(this, Locale.getDefault())
    try {
        val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
        if (addresses != null) {
            val returnedAddress: Address = addresses[0]
            val strReturnedAddress = StringBuilder("")
            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
            }
            strAdd = strReturnedAddress.toString()
            Log.e("MyCurrentloctionaddress", strReturnedAddress.toString())
        } else {
            Log.e("MyCurrentloctionaddress", "No Address returned!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("MyCurrentloctionaddress", "Canont get Address!")
    }
    return strAdd
}

fun Fragment.checkPermission():Boolean
{
    return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.takePermission() {
    val permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val dialogBinding = CustomAlertDialogBinding.inflate(requireActivity().layoutInflater)
    val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).setCancelable(true).create()
    dialogBinding.title.text = getString(R.string.location_permission)
    dialogBinding.body.text = getString(R.string.permission_dialog_description)
    alertDialog.show()

    dialogBinding.btnYes.setOnClickListener()
    {
        ActivityCompat.requestPermissions(requireActivity(), permissionArray, LOCATION_PERMISSION_CODE)
        alertDialog.dismiss()
    }
    dialogBinding.btnClose.setOnClickListener()
    {
        alertDialog.dismiss()
    }


}

fun Fragment.hideToolbar()
{
    ( requireActivity() as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.showToolbar()
{
    ( requireActivity() as AppCompatActivity).supportActionBar?.show()
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun View.preventTwoClick() {
    this.isEnabled = false
    this.postDelayed({ this.isEnabled = true }, 500)
}

