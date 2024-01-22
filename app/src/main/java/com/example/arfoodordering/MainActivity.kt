package com.example.arfoodordering

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode

class MainActivity : AppCompatActivity() {

    lateinit var sceneView: ArSceneView
    lateinit var placeButtom: Button
    lateinit var modelNode: ArModelNode

    //AR view using arsceneview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.sceneView)
        placeButtom = findViewById(R.id.place)

        placeButtom.setOnClickListener{
            placeModel()
        }

 //AR Scene View
        val receivedList = intent.getStringExtra("data")
        Toast.makeText(this, "Test $receivedList",Toast.LENGTH_LONG).show()
        val modelFiles = mutableListOf("models/pizza.glb","models/Coffee.glb")
        //Toast.makeText(this, "val "+intent.getStringExtra("data"),Toast.LENGTH_SHORT).show()
        for (modelFile in modelFiles) {
            modelNode = ArModelNode().apply {
                if (receivedList != null) {
                    loadModelGlbAsync(
                        glbFileLocation = receivedList
                    )
                    {
                        sceneView.planeRenderer.isVisible = true
                    }
                }
                onAnchorChanged = {
                    placeButtom.isGone
                }
            }
            sceneView.addChild(modelNode)
        }

    }
    //AR model place btn
    private fun placeModel(){
        try {
            if (::modelNode.isInitialized) {
                modelNode?.anchor()
                sceneView.planeRenderer.isVisible = false
            } else {
                // Handle the case where the modelNode is not initialized
                Toast.makeText(this, "Something Went Wrong",Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}