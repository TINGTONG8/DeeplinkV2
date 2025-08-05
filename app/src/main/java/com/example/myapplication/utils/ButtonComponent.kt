package com.example.myapplication.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun ButtonComponentPreview() {
    Column {
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.buy_deep_link),
            buttonColor = colorResource(R.color.gray),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.buy_qr),
            buttonColor = colorResource(R.color.gray),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.done),
            buttonColor = colorResource(R.color.gray),
            onClick = {}
        )
    }
}

@Composable
fun ButtonComponent (
    modifier: Modifier,
    isEnabled: Boolean? = true,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled == true,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        contentPadding = PaddingValues(20.dp)
    ) {
        Text(
            text = buttonText,
            color = colorResource(R.color.black),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}