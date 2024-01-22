package com.example.arfoodordering

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.arfoodordering.databinding.ActivityQrgenBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QRGen : AppCompatActivity() {

    private  lateinit var ivQRGen: ImageView
    private  lateinit var btnGen: FloatingActionButton
    private  lateinit var qrData: TextView
    private  lateinit var scan: Button
    var data:String? = null
    var view:String? = null
    private var barcodeScannerOptions: BarcodeScannerOptions? = null
    private var barcodeScanner:BarcodeScanner? = null

    private  val cameraPermission = android.Manifest.permission.CAMERA
    private lateinit var binding: ActivityQrgenBinding
    private  val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted){
            startScanner()
        }
    }
    //animations
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrgenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener{
            onAddButtonClicked()
        }
        binding.scanCam.setOnClickListener(){
            requestCameraAndStartScanner()
        }
        ivQRGen = findViewById(R.id.ivQRCode)
        btnGen = findViewById(R.id.btnGen)
        qrData = findViewById(R.id.qrData)
        scan = findViewById(R.id.scan)

        //QR Gen algorithm
        val receivedList = intent.getStringArrayListExtra("data")
        data = receivedList.toString()
        if (data != null) {
            if(data!!.isEmpty()){
                Toast.makeText(this,"Some thing went wrong",Toast.LENGTH_SHORT).show()
            }else{
                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(data,BarcodeFormat.QR_CODE,1012,1012)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                    for(x in 0 until width){
                        for (y in 0 until height){
                            bmp.setPixel(x,y, if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    ivQRGen.setImageBitmap(bmp)
                }catch (e: WriterException){

                }
            }
        }else{
            Toast.makeText(this,"Some thing went wrong",Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show()
        btnGen.setOnClickListener{

        }

        barcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()

        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions!!)
        //galleryActivityResultLauncher.launch(intent)

        scan.setOnClickListener{

            val intent = Intent(this,MainActivity::class.java).also {
                it.putExtra("data",data)
                startActivity(it)
            }
            Toast.makeText(this,data,Toast.LENGTH_SHORT).show()

        }

    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.btnGen.startAnimation(fromBottom)
            binding.scanCam.startAnimation(fromBottom)
            binding.addBtn.startAnimation(rotateOpen)
        }else
        {
            binding.btnGen.startAnimation(toBottom)
            binding.scanCam.startAnimation(toBottom)
            binding.addBtn.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.btnGen.visibility = android.view.View.VISIBLE
            binding.scanCam.visibility = android.view.View.VISIBLE
        }
        else{
            binding.btnGen.visibility = android.view.View.INVISIBLE
            binding.scanCam.visibility = android.view.View.INVISIBLE
        }
    }

    private fun requestCameraAndStartScanner(){
        if(isPermissionGranted(cameraPermission)){
            startScanner()
        }else{
            requestCameraPermission()
        }
    }

    private fun startScanner(){
        //transfer the this activity data to MainActivity
        ScannerActivity.startScanner(this){ barcodes ->
            barcodes.forEach{ barcode ->
                val model = barcode.rawValue.toString()
                Toast.makeText(this,model,Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java).also {
                    it.putExtra("model",model)
                    startActivity(it)
                }
                //startActivity(intent)
            }

        }
    }

    private fun requestCameraPermission() {
        when{
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openPermissionSetting()
                }
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}