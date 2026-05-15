package com.nammashale.inventory

import android.content.ContentValues
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class GeneratePdfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_generate_pdf)

        val btnGeneratePdf =
            findViewById<Button>(R.id.btnGeneratePdf)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        val database =
            AppDatabase.getDatabase(this)

        btnGeneratePdf.setOnClickListener {

            lifecycleScope.launch {

                val assets =
                    database.assetDao().getAllAssets()

                val pdfDocument =
                    PdfDocument()

                val paint =
                    Paint()

                val titlePaint =
                    Paint()

                titlePaint.textSize = 42f

                titlePaint.isFakeBoldText = true

                val pageInfo =
                    PdfDocument.PageInfo.Builder(
                        1200,
                        4000,
                        1
                    ).create()

                val page =
                    pdfDocument.startPage(pageInfo)

                val canvas =
                    page.canvas

                canvas.drawColor(Color.WHITE)

                canvas.drawText(
                    "Namma Shaale Inventory Report",
                    180f,
                    100f,
                    titlePaint
                )

                paint.textSize = 28f

                var y = 180f

                for (asset in assets) {

                    canvas.drawText(
                        "Asset Name: ${asset.assetName}",
                        100f,
                        y,
                        paint
                    )

                    y += 45f

                    canvas.drawText(
                        "Category: ${asset.category}",
                        100f,
                        y,
                        paint
                    )

                    y += 45f

                    canvas.drawText(
                        "Quantity: ${asset.quantity}",
                        100f,
                        y,
                        paint
                    )

                    y += 45f

                    canvas.drawText(
                        "Description: ${asset.description}",
                        100f,
                        y,
                        paint
                    )

                    y += 45f

                    canvas.drawText(
                        "Condition: ${asset.status}",
                        100f,
                        y,
                        paint
                    )

                    y += 45f

                    canvas.drawText(
                        "Uploaded: ${asset.uploadDateTime}",
                        100f,
                        y,
                        paint
                    )

                    try {

                        if (
                            asset.imageUri != null &&
                            asset.imageUri.isNotEmpty() &&
                            asset.imageUri != "null"
                        ) {

                            val inputStream =
                                contentResolver.openInputStream(
                                    Uri.parse(asset.imageUri)
                                )

                            val bitmap =
                                BitmapFactory.decodeStream(inputStream)

                            val scaledBitmap =
                                Bitmap.createScaledBitmap(
                                    bitmap,
                                    250,
                                    250,
                                    false
                                )

                            canvas.drawBitmap(
                                scaledBitmap,
                                800f,
                                y - 150f,
                                null
                            )
                        }

                    } catch (e: Exception) {

                        e.printStackTrace()
                    }

                    y += 320f
                }

                pdfDocument.finishPage(page)

                val resolver =
                    contentResolver

                val contentValues =
                    ContentValues().apply {

                        put(
                            MediaStore.MediaColumns.DISPLAY_NAME,
                            "InventoryReport.pdf"
                        )

                        put(
                            MediaStore.MediaColumns.MIME_TYPE,
                            "application/pdf"
                        )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_DOWNLOADS
                            )
                        }
                    }

                val uri =
                    resolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        contentValues
                    )

                if (uri != null) {

                    val outputStream =
                        resolver.openOutputStream(uri)

                    pdfDocument.writeTo(outputStream!!)

                    outputStream.close()

                    pdfDocument.close()

                    runOnUiThread {

                        Toast.makeText(
                            this@GeneratePdfActivity,
                            "PDF Downloaded",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}