package io.github.kurramkurram.angerlog.ui.screen.license

import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.kurramkurram.angerlog.R
import io.github.kurramkurram.angerlog.ui.component.layout.AngerLogBackButtonLayout
import io.github.kurramkurram.angerlog.ui.component.text.AngerLogLinkText
import kotlinx.serialization.Serializable

private const val TAG = "LicenseScreen"

@Serializable
object License

@Composable
fun LicenseScreen(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
) {
    AngerLogBackButtonLayout(
        onClickBack = onClickButton,
        title = stringResource(R.string.license),
    ) {
        var licenseState by remember { mutableStateOf(emptyList<LibraryLicenseDto>()) }
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            licenseState = LibraryLicenseList.create(context)
        }

        LazyColumn {
            items(licenseState) {
                Column(modifier.padding(horizontal = 5.dp)) {
                    Text(
                        modifier =
                            modifier.padding(
                                start = 0.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 10.dp,
                            ),
                        text = it.name,
                    )

                    val terms = it.terms
                    if (URLUtil.isValidUrl(terms)) {
                        AngerLogLinkText(modifier, link = terms)
                    } else {
                        Text(terms)
                    }
                    Text(text = stringResource(R.string.license_divider))
                }
            }
        }
    }
}
