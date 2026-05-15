package com.nammashale.inventory

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ViewAssetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_asset)

        val layoutAssets =
            findViewById<LinearLayout>(R.id.layoutAssets)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val database =
            AppDatabase.getDatabase(this)

        loadAssets(layoutAssets, database)
    }

    private fun loadAssets(
        layoutAssets: LinearLayout,
        database: AppDatabase
    ) {

        lifecycleScope.launch {

            val assets =
                database.assetDao().getAllAssets()

            runOnUiThread {

                layoutAssets.removeAllViews()

                if (assets.isEmpty()) {

                    val tv =
                        TextView(this@ViewAssetActivity)

                    tv.text = "No Assets Added"

                    tv.textSize = 22f

                    tv.setTextColor(Color.BLACK)

                    layoutAssets.addView(tv)
                }

                for (asset in assets) {

                    val card =
                        CardView(this@ViewAssetActivity)

                    val params =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                    params.setMargins(
                        0,
                        0,
                        0,
                        30
                    )

                    card.layoutParams = params

                    card.radius = 20f

                    card.cardElevation = 8f

                    val innerLayout =
                        LinearLayout(this@ViewAssetActivity)

                    innerLayout.orientation =
                        LinearLayout.VERTICAL

                    innerLayout.setPadding(
                        40,
                        40,
                        40,
                        40
                    )

                    val image =
                        ImageView(this@ViewAssetActivity)

                    image.layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            500
                        )

                    image.scaleType =
                        ImageView.ScaleType.CENTER_CROP

                    try {

                        if (
                            asset.imageUri != null &&
                            asset.imageUri.isNotEmpty() &&
                            asset.imageUri != "null"
                        ) {

                            image.setImageURI(
                                Uri.parse(asset.imageUri)
                            )

                        } else {

                            image.setImageResource(
                                android.R.drawable.ic_menu_gallery
                            )
                        }

                    } catch (e: Exception) {

                        image.setImageResource(
                            android.R.drawable.ic_menu_gallery
                        )
                    }

                    val name =
                        TextView(this@ViewAssetActivity)

                    name.text =
                        "Asset Name: ${asset.assetName}"

                    name.textSize = 20f

                    name.setTextColor(Color.BLACK)

                    val category =
                        TextView(this@ViewAssetActivity)

                    category.text =
                        "Category: ${asset.category}"

                    category.textSize = 16f

                    val quantity =
                        TextView(this@ViewAssetActivity)

                    quantity.text =
                        "Quantity: ${asset.quantity}"

                    quantity.textSize = 16f

                    val description =
                        TextView(this@ViewAssetActivity)

                    description.text =
                        "Description: ${asset.description}"

                    description.textSize = 16f

                    val status =
                        TextView(this@ViewAssetActivity)

                    status.text =
                        "Condition: ${asset.status}"

                    status.textSize = 18f

                    if (asset.status == "Working") {

                        status.setTextColor(
                            Color.parseColor("#2E7D32")
                        )

                    } else {

                        status.setTextColor(Color.RED)
                    }

                    val date =
                        TextView(this@ViewAssetActivity)

                    date.text =
                        "Uploaded: ${asset.uploadDateTime}"

                    val btnEdit =
                        Button(this@ViewAssetActivity)

                    btnEdit.text = "Edit"

                    btnEdit.setBackgroundColor(
                        Color.parseColor("#FB8C00")
                    )

                    btnEdit.setTextColor(Color.WHITE)

                    val btnDelete =
                        Button(this@ViewAssetActivity)

                    btnDelete.text = "Delete"

                    btnDelete.setBackgroundColor(Color.RED)

                    btnDelete.setTextColor(Color.WHITE)

                    btnDelete.setOnClickListener {

                        lifecycleScope.launch {

                            database.assetDao()
                                .deleteAsset(asset)

                            runOnUiThread {

                                Toast.makeText(
                                    this@ViewAssetActivity,
                                    "Asset Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                loadAssets(
                                    layoutAssets,
                                    database
                                )
                            }
                        }
                    }

                    btnEdit.setOnClickListener {

                        val editLayout =
                            LinearLayout(this@ViewAssetActivity)

                        editLayout.orientation =
                            LinearLayout.VERTICAL

                        val etName =
                            EditText(this@ViewAssetActivity)

                        etName.hint = "Asset Name"

                        etName.setText(asset.assetName)

                        val etQuantity =
                            EditText(this@ViewAssetActivity)

                        etQuantity.hint = "Quantity"

                        etQuantity.setText(asset.quantity)

                        val etDescription =
                            EditText(this@ViewAssetActivity)

                        etDescription.hint = "Description"

                        etDescription.setText(asset.description)

                        val etCategory =
                            EditText(this@ViewAssetActivity)

                        etCategory.hint = "Category"

                        etCategory.setText(asset.category)

                        val etStatus =
                            EditText(this@ViewAssetActivity)

                        etStatus.hint = "Condition"

                        etStatus.setText(asset.status)

                        editLayout.addView(etName)

                        editLayout.addView(etQuantity)

                        editLayout.addView(etDescription)

                        editLayout.addView(etCategory)

                        editLayout.addView(etStatus)

                        AlertDialog.Builder(this@ViewAssetActivity)

                            .setTitle("Edit Asset")

                            .setView(editLayout)

                            .setPositiveButton(
                                "Update"
                            ) { _, _ ->

                                val updatedAsset =
                                    asset.copy(

                                        assetName =
                                            etName.text.toString(),

                                        quantity =
                                            etQuantity.text.toString(),

                                        description =
                                            etDescription.text.toString(),

                                        category =
                                            etCategory.text.toString(),

                                        status =
                                            etStatus.text.toString()
                                    )

                                lifecycleScope.launch {

                                    database.assetDao()
                                        .updateAsset(updatedAsset)

                                    runOnUiThread {

                                        Toast.makeText(
                                            this@ViewAssetActivity,
                                            "Updated Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        loadAssets(
                                            layoutAssets,
                                            database
                                        )
                                    }
                                }
                            }

                            .setNegativeButton(
                                "Cancel",
                                null
                            )

                            .show()
                    }

                    innerLayout.addView(image)

                    innerLayout.addView(name)

                    innerLayout.addView(category)

                    innerLayout.addView(quantity)

                    innerLayout.addView(description)

                    innerLayout.addView(status)

                    innerLayout.addView(date)

                    innerLayout.addView(btnEdit)

                    innerLayout.addView(btnDelete)

                    card.addView(innerLayout)

                    layoutAssets.addView(card)
                }
            }
        }
    }
}