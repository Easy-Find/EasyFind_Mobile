package com.easy.myapplication.screens.Compra

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.ui.theme.Primary

@Composable
fun ProgressBar(currentStep: Int, totalStep: Int) {
    val activeColor = Primary
    val inactiveColor = Color.White
    val activeTextColor = Color.Black
    val inactiveTextColor = Color.White
    val calculo = remember {
        mutableFloatStateOf(0.0f)
    }


    LaunchedEffect(key1 = currentStep) {
       calculo.value= (((currentStep-1)*43.23)/100).toFloat();
    }

    Log.e("rr",calculo.toString())
            Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 15.dp)
        ) {


            HorizontalDivider(modifier = Modifier
                .fillMaxWidth(calculo.value)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(Primary)
                .size(5.dp)
                .align(Alignment.CenterStart)
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            for (i in 1..totalStep) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                        CircleNumber(
                            number = i,
                            isSelected = i <= currentStep,
                            activeColor = activeColor,
                            inactiveColor = inactiveColor,
                            activeTextColor = activeTextColor,
                            inactiveTextColor = inactiveTextColor,
                        )



                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }

    }
}

@SuppressLint("Range")
@Composable
fun CircleNumber(
    number: Int,
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    activeTextColor: Color,
    inactiveTextColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp),
    ) {

        Canvas(modifier = Modifier.size(40.dp)) {
            if (isSelected) {
                drawCircle(
                    color = activeColor,
                    radius = size.minDimension / 2,
                )
            } else {
                drawCircle(
                    color = inactiveColor,
                    radius = size.minDimension / 2,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
        Text(
            text = number.toString(),
            color = if (isSelected) activeTextColor else inactiveTextColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SelectableOptionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val colors = RadioButtonDefaults.colors(
        selectedColor = Primary,
        unselectedColor = Color.Black
    )

    Row(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(42.dp))
            .width(500.dp)
            .height(50.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = colors
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 12.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
