package com.example.hfs

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hfs.ui.theme.HandsfreeScrollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandsfreeScrollerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                    }
            }
        }
    }
}

    @Composable
    fun Calculator(modifier: Modifier = Modifier) {
        val buttonLabels = listOf(
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "+", "-", "/",
            "*", "←", "="
        )
        val operators = listOf(
            "+", "-", "/", "*"
        )
        val finalButtons = listOf("←", "=")

        var calculatorDisplay by remember { mutableStateOf(listOf<Any>("+", 3)) }
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier
                    .then(
                        if (calculatorDisplay.isNotEmpty()) {
                            Modifier.border(
                                color = Color.White,
                                width = 4.dp
                            )
                        } else {
                            Modifier
                        }
                    )

                    .background(color = Color.Gray)
                    .padding(10.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (item in calculatorDisplay) {
                        Text(
                            text = item.toString(),
                            fontSize = 60.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            buttonLabels.chunked(3).forEach { rowLabels ->
                Row (
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowLabels.forEach { label ->
                        Button(
                            onClick = {
                                if (calculatorDisplay.isEmpty()) {
                                    calculatorDisplay = when (label) {
                                        in operators -> calculatorDisplay + label
                                        in finalButtons -> calculatorDisplay
                                        else -> calculatorDisplay + label.toInt()
                                    }
                                } else {
                                    when (label) {
                                        "←" -> calculatorDisplay = calculatorDisplay.dropLast(1)
                                        "=" -> {
                                            for ((i, item) in calculatorDisplay.withIndex()) {

                                            }
                                        }
                                        else -> {
                                            val lastItem = calculatorDisplay.last()
                                            if (label in operators) {
                                                calculatorDisplay = calculatorDisplay + label
                                            } else if (lastItem in operators) {
                                                calculatorDisplay = calculatorDisplay + label.toInt()
                                            } else {
                                                if (lastItem is Int) {
                                                    calculatorDisplay = calculatorDisplay.dropLast(1)
                                                    calculatorDisplay =
                                                        calculatorDisplay + (lastItem * 10 + label.toInt())
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.d("Calculator", "Display: $calculatorDisplay")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text(
                                text = label,
                                fontSize = 24.sp,
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(
        showBackground = true,
        showSystemUi = true,
        device = Devices.PIXEL_7
    )
    @Composable
    fun MyScreenPreview() {
        Calculator()
    }