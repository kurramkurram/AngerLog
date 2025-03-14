package io.github.kurramkurram.angerlog.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import io.github.kurramkurram.angerlog.util.L

private const val TAG = "AngerLogLinkText"

@Composable
fun AngerLogLinkText(
    modifier: Modifier = Modifier,
    preText: String = "",
    linkText: String = "",
    link: String,
    onClickLink: (() -> Unit)? = null,
    suffixText: String = "",
    linkColor: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    val uriHandler = LocalUriHandler.current
    Text(
        modifier = modifier,
        text =
            buildAnnotatedString {
                append(preText)
                val l =
                    LinkAnnotation.Url(
                        link,
                        TextLinkStyles(
                            SpanStyle(
                                color = linkColor,
                                textDecoration = TextDecoration.Underline,
                            ),
                        ),
                    ) {
                        if (onClickLink == null) {
                            try {
                                val url = (it as LinkAnnotation.Url).url
                                uriHandler.openUri(url)
                            } catch (e: IllegalArgumentException) {
                                L.e("$TAG#LicenseScreenLinkText $e")
                            }
                        } else {
                            onClickLink()
                        }
                    }
                withLink(l) { append(linkText.ifEmpty { link }) }
                append(suffixText)
            },
    )
}

// @Composable
// @Preview
// fun PreviewAngerLogLinkText() {
//    AngerLogLinkText(
//        modifier = Modifier.padding(10.dp),
// //        preText = "「利用を開始する」を押下すると",
// //        linkText = "利用規約",
//        link = "https://example.com",
// //        postText = "に同意することになります。"
//    )
// }
