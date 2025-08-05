package com.example.myapplication.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colorResource(R.color.black),
            fontSize = 16.sp,
            fontWeight = FontWeight.W800,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1
        )
        Text(
            text = value,
            modifier = Modifier.weight(5f),
            color = colorResource(R.color.black),
            fontSize = 16.sp,
            fontWeight = FontWeight.W800,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.End
        )
    }
}