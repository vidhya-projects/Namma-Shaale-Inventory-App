package com.nammashale.inventory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddAssetActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_asset)

        val etName =
            findViewById<EditText>(R.id.etName)

        val etQuantity =
            findViewById<EditText>(R.id.etQuantity)

        val etDescription =
            findViewById<EditText>(R.id.etDescription)

        val spCategory =
            findViewById<Spinner>(R.id.spCategory)

        val spCondition =
            findViewById<Spinner>(R.id.spCondition)

        val btnChooseImage =
            findViewById<Button>(R.id.btnChooseImage)

        val btnSave =
            findViewById<Button>(R.id.btnSave)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        val imagePreview =
            findViewById<ImageView>(R.id.imagePreview)

        val database =
            AppDatabase.getDatabase(this)

        spCategory.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(
                    "Furniture",
                    "Lab",
                    "Sports",
                    "Others"
                )
            )

        spCondition.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(
                    "Working",
                    "Not Working"
                )
            )

        val imagePicker =
            registerForActivityResult(
                ActivityResultContracts.OpenDocument()
            ) { uri ->

                if (uri != null) {

                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    selectedImageUri = uri

                    imagePreview.setImageURI(uri)
                }
            }

        btnChooseImage.setOnClickListener {

            imagePicker.launch(
                arrayOf("image/*")
            )
        }

        btnBack.setOnClickListener {

            finish()
        }

        btnSave.setOnClickListener {

            val currentDateTime =
                SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.getDefault()
                ).format(Date())

            val asset =
                SchoolAssetEntity(

                    assetName =
                        etName.text.toString(),

                    quantity =
                        etQuantity.text.toString(),

                    category =
                        spCategory.selectedItem.toString(),

                    status =
                        spCondition.selectedItem.toString(),

                    description =
                        etDescription.text.toString(),

                    imageUri =
                        selectedImageUri?.toString(),

                    uploadDateTime =
                        currentDateTime
                )

            lifecycleScope.launch {

                database.assetDao()
                    .insertAsset(asset)

                runOnUiThread {

                    Toast.makeText(
                        this@AddAssetActivity,
                        "Asset Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }
}