package com.nammashale.inventory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val btnAddAsset =
            findViewById<Button>(R.id.btnAddAsset)

        val btnViewAssets =
            findViewById<Button>(R.id.btnViewAssets)

        val btnGeneratePdf =
            findViewById<Button>(R.id.btnGeneratePdf)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        val tvTotalAssets =
            findViewById<TextView>(R.id.tvTotalAssets)

        val tvRepairAssets =
            findViewById<TextView>(R.id.tvRepairAssets)

        val database =
            AppDatabase.getDatabase(this)

        lifecycleScope.launch {

            val assets =
                database.assetDao().getAllAssets()

            val totalAssets =
                assets.size

            val repairAssets =
                assets.count {
                    it.status == "Not Working"
                }

            runOnUiThread {

                tvTotalAssets.text =
                    totalAssets.toString()

                tvRepairAssets.text =
                    repairAssets.toString()
            }
        }

        btnAddAsset.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddAssetActivity::class.java
                )
            )
        }

        btnViewAssets.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ViewAssetActivity::class.java
                )
            )
        }

        btnGeneratePdf.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    GeneratePdfActivity::class.java
                )
            )
        }

        btnLogout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}