package com.example.barscanner.util

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

class BarcodeScanner(appContext: Context) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .allowManualInput()
        .enableAutoZoom()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options.build())
    val barCodeResults = MutableStateFlow<String?>(null)

    fun startScan() {
        try {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    barCodeResults.value = barcode.rawValue
                }
                .addOnCanceledListener {
                    barCodeResults.value = "canceled"
                }
                .addOnFailureListener { _ ->
                    barCodeResults.value = "failed"
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}