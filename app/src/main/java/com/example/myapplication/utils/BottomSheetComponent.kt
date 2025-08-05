package com.example.myapplication.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
fun BottomSheetComponentPreview() {
    BottomSheetComponent(
        modifier = Modifier,
        orderNo = "0123456789",
        model = R.string.iphone_16_pro_max,
        qty = 50,
        amount = 5000,
        timeLeftInSeconds = 150,
        onDismiss = { },
        onRepay = { }
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun BottomSheetComponent(
    modifier: Modifier,
    qrBitmap: ImageBitmap? = null,
    orderNo: String,
    model: Int,
    qty: Int,
    amount: Int,
    timeLeftInSeconds: Int,
    onDismiss: () -> Unit,
    onRepay: () -> Unit,
) {
    val minutes = timeLeftInSeconds / 60
    val seconds = timeLeftInSeconds % 60
    val timeString = String.format("%d:%02d", minutes, seconds)

    Box(
        modifier = modifier
            .imePadding()
            .fillMaxSize()
            .background(color = colorResource(R.color.black_50))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onDismiss.invoke() }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(start = 15.dp, end = 15.dp, top = 30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.qr),
                modifier = Modifier.padding(bottom = 20.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight(800),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                if (qrBitmap != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = qrBitmap,
                        contentDescription = "QR Code",
                        contentScale = ContentScale.Fit
                    )
                }
                if (timeLeftInSeconds == 0) {
                    Image(
                        modifier = Modifier
                            .size(200.dp),
                        painter = painterResource(R.drawable.ic_x),
                        contentDescription = "Expired",
                        contentScale = ContentScale.Fit
                    )
                }
            }
            IntentComponent (
                modifier = Modifier,
                orderNo = orderNo,
                model = model,
                qty =  qty,
                amount = amount
            )
            Text(
                if (timeLeftInSeconds == 0) stringResource(R.string.expired) else "${stringResource(R.string.expire_in)} $timeString ${stringResource(R.string.sec)}",
                modifier = Modifier
                    .then(
                        if (timeLeftInSeconds == 0) {
                            Modifier.padding(top = 20.dp, bottom = 20.dp)
                        } else {
                            Modifier.padding(top = 20.dp, bottom = 50.dp)
                        }
                    ),
                fontSize = 18.sp,
                fontWeight = FontWeight(800),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            if (timeLeftInSeconds == 0)
                ButtonComponent(
                    modifier = Modifier
                        .padding(bottom = 30.dp),
                    buttonText = stringResource(R.string.repay),
                    buttonColor = colorResource(R.color.gray),
                    onClick = {
                        onRepay.invoke()
                    }
                )
        }
    }
}