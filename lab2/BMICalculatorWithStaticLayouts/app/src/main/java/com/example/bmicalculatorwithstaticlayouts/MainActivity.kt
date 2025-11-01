package com.example.bmicalculatorwithstaticlayouts

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmicalculatorwithstaticlayouts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BmiViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = BmiViewModel()
        
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        
        setContentView(binding.root)
        
        setupWindowInsets()
        observeBmiChanges()
    }
    
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun observeBmiChanges() {
        viewModel.bmiResultText.observe(this) { updateBmiIndicator() }
    }
    
    private fun updateBmiIndicator() {
        val bmi = viewModel.getCurrentBmi() ?: run {
            binding.bmiIndicator.visibility = View.GONE
            return
        }
        
        binding.bmiIndicator.visibility = View.VISIBLE
        binding.bmiMeterContainer.post {
            binding.bmiIndicator.translationX = calculateIndicatorPosition(bmi)
        }
    }
    
    private fun calculateIndicatorPosition(bmi: Double): Float {
        val density = resources.displayMetrics.density
        val padding = 8 * density
        val barWidth = binding.bmiMeterContainer.width - (2 * padding)
        val normalized = (bmi.coerceIn(0.0, BmiViewModel.BMI_MAX) / BmiViewModel.BMI_MAX).toFloat()
        val indicatorRadius = 8 * density
        
        return padding + (barWidth * normalized).coerceIn(indicatorRadius, barWidth - indicatorRadius) - indicatorRadius
    }
}
