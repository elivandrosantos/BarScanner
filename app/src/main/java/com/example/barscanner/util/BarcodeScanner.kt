package com.example.barscanner.util

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

class BarcodeScanner(
    appContext: Context
) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        ).build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan() {
        try {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    barCodeResults.value = barcode.displayValue
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