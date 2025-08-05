package com.example.myapplication.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Preview
@Composable
fun IntentComponentPreview() {
    IntentComponent (
        modifier = Modifier,
        orderNo = "aBcDe12345",
        model = R.string.iphone_16_pro_max,
        qty = 1,
        amount = 5000
    )
}

@Composable
fun IntentComponent(
    modifier: Modifier,
    orderNo: String,
    model: Int,
    qty: Int,
    amount: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.gray),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            InfoRow(
                label = stringResource(R.string.order_no_colon),
                value = orderNo
            )
            InfoRow(
                modifier = Modifier.padding(top = 10.dp),
                label = stringResource(R.string.model_colon),
                value = stringResource(model)
            )
            InfoRow(
                modifier = Modifier.padding(top = 10.dp),
                label = stringResource(R.string.qty_colon),
                value = qty.toString(),
            )
            InfoRow(
                modifier = Modifier.padding(top = 10.dp),
                label = stringResource(R.string.amount_colon),
                value = "$ ${amount}.00",
            )
        }
    }
}