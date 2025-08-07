package com.example.myapplication.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout.BoxBackgroundMode

@Preview
@Composable
fun BottomSheetMsgComponentPreview() {
    BottomSheetMsgComponent(
        modifier = Modifier,
        title = stringResource(R.string.scan_qr_to_pay)
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun BottomSheetMsgComponent(
    modifier: Modifier,
    background: Color = colorResource(R.color.gray),
    textColor: Color = colorResource(R.color.black),
    title: String,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = background,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(20.dp),
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}