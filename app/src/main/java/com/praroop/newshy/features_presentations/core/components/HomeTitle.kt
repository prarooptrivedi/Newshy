package com.praroop.newshy.features_presentations.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.praroop.newshy.features_presentations.core.ui.theme.NewshyTheme

@Composable
fun HeaderTitle(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = defaultpadding
            )
    ) {
        TitleText(title)
        Spacer(modifier = Modifier.padding(defaultpadding))
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun TitleText(
    text: String
) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun PrevHeaderTitle() {
    NewshyTheme {
        HeaderTitle(
            title = "Title",
            icon = Icons.Filled.Home
        )
    }

}