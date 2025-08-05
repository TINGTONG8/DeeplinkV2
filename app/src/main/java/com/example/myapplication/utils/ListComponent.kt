package com.example.myapplication.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
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
fun ListComponentPreview() {
    Column {
        ListComponent (
            modifier = Modifier,
            title = stringResource(R.string.iphone_16_pro_max),
            iconRight = painterResource(R.drawable.iphone_list),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListComponent (
            modifier = Modifier,
            title = stringResource(R.string.galaxy_s24),
            iconLeft = painterResource(R.drawable.galaxy_s24_list),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListComponentV2 (
            modifier = Modifier,
            title = stringResource(R.string.apple_watch_series_10),
            iconLeft = painterResource(R.drawable.apple_watch_series_10_list),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListComponentV2 (
            modifier = Modifier,
            modifierAlign = Alignment.CenterEnd,
            title = stringResource(R.string.galaxy_buds_3_pro),
            titleAlign = TextAlign.End,
            iconRight = painterResource(R.drawable.galaxy_buds_3_pro_list),
            onClick = {}
        )
    }
}

/**
 * ListComponent
 */
@Composable
fun ListComponent (
    modifier: Modifier,
    title: String,
    titleAlign: TextAlign = TextAlign.Center,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                color = colorResource(R.color.gray),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            if (iconLeft != null) {
                Image(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 15.dp, bottom = 15.dp),
                    painter = iconLeft,
                    contentDescription = "--",
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                title,
                modifier = Modifier
                    .weight(1F),
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.SansSerif,
                textAlign = titleAlign
            )
            if (iconRight != null) {
                Image(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp),
                    painter = iconRight,
                    contentDescription = "--",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

/**
 * ListComponentV2
 */
@Composable
fun ListComponentV2 (
    modifier: Modifier,
    modifierAlign: Alignment = Alignment.CenterStart,
    title: String,
    titleAlign: TextAlign = TextAlign.Center,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick.invoke()
            }
            .background(
                color = colorResource(R.color.gray),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = modifierAlign
    ) {
        if (iconLeft != null) {
            Image(
                painter = iconLeft,
                contentDescription = "--",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 80.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        if (iconRight != null) {
            Image(
                painter = iconRight,
                contentDescription = "--",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 145.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            title,
            modifier = Modifier
                .then(
                    if (iconRight != null) {
                        Modifier.padding(end = 15.dp)
                    } else {
                        Modifier.padding(start = 15.dp)
                    }
                ),
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.SansSerif,
            textAlign = titleAlign
        )
    }
}