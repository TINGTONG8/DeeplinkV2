package com.example.myapplication.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun AddMinusComponentComponentPreview() {
    AddMinusComponent (
        modifier = Modifier
            .padding(vertical = 15.dp),
        onMinusClick = {},
        qtyText = 5,
        onAddClick = {}
    )
}

@Composable
fun AddMinusComponent (
    modifier: Modifier,
    onMinusClick: () -> Unit,
    qtyText: Int,
    onAddClick: () -> Unit,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .clickable {
                    onMinusClick.invoke()
                },
            painter = painterResource(R.drawable.icon_minus),
            contentDescription = "--",
            contentScale = ContentScale.Fit
        )
        Text(
            qtyText.toString(),
            modifier = Modifier
                .weight(5F),
            fontSize = 30.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .clickable {
                    onAddClick.invoke()
                },
            painter = painterResource(R.drawable.icon_add),
            contentDescription = "--",
            contentScale = ContentScale.Fit
        )
    }
}