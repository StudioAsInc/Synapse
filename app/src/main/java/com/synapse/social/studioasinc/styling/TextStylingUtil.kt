package com.synapse.social.studioasinc.styling

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri // Required for Uri.parse
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ReplacementSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synapse.social.studioasinc.ProfileActivity
import java.util.regex.Pattern

// You might need to add this dependency in your Sketchware Pro project's build.gradle if not already present:
// implementation 'androidx.browser:browser:1.7.0' (or the latest stable version)
import androidx.browser.customtabs.CustomTabsIntent

class TextStylingUtil(private val activityContext: Context) {

    private val pattern: Pattern = Pattern.compile(
        "(?<![^\\s])(([@]{1}|[#]{1})([A-Za-z0-9_-]\\.?)+)|" + // Group 3: Mentions/Hashtags
                "\\*\\*(.*?)\\*\\*|" +    // Group 4: Bold (**)
                "__(.*?)__|" +            // Group 5: Italic (__)
                "~~(.*?)~~|" +            // Group 6: Strikethrough (~~)
                "_(.*?)_|" +              // Group 7: Underline (_)
                "\\*(.*?)\\*|" +          // Group 8: Italic (*)
                "///(.*?)///|" +          // Group 9: Bold-Italic (///)
                "`(.*?)`|" +              // Group 10: Highlighted text (``)
                "\\[([^\\]]+)\\]\\(([^)]+)\\)|" + // Group 11: Link Text, Group 12: Link URL
                "##(.*?)##|" +            // Group 13: Larger Text (##text##)
                "#(.*?)#"                 // Group 14: Medium Text (#text#)
    )

    fun applyStyling(text: String, textView: TextView) {
        textView.movementMethod = LinkMovementMethod.getInstance()

        val ssb = SpannableStringBuilder(text)
        val matcher = pattern.matcher(text)
        var offset = 0

        while (matcher.find()) {
            val start = matcher.start() + offset
            val end = matcher.end() + offset

            when {
                matcher.group(3) != null -> {
                    val span = ProfileSpan(activityContext)
                    ssb.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                matcher.group(4) != null -> {
                    val boldText = matcher.group(4)!!
                    ssb.replace(start, end, boldText)
                    ssb.setSpan(
                        StyleSpan(Typeface.BOLD),
                        start,
                        start + boldText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 4
                }
                matcher.group(5) != null -> {
                    val italicText = matcher.group(5)!!
                    ssb.replace(start, end, italicText)
                    ssb.setSpan(
                        StyleSpan(Typeface.ITALIC),
                        start,
                        start + italicText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 4
                }
                matcher.group(6) != null -> {
                    val strikethroughText = matcher.group(6)!!
                    ssb.replace(start, end, strikethroughText)
                    ssb.setSpan(
                        StrikethroughSpan(),
                        start,
                        start + strikethroughText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 4
                }
                matcher.group(7) != null -> {
                    val underlineText = matcher.group(7)!!
                    ssb.replace(start, end, underlineText)
                    ssb.setSpan(
                        UnderlineSpan(),
                        start,
                        start + underlineText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 2
                }
                matcher.group(8) != null -> {
                    val italicText = matcher.group(8)!!
                    ssb.replace(start, end, italicText)
                    ssb.setSpan(
                        StyleSpan(Typeface.ITALIC),
                        start,
                        start + italicText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 2
                }
                matcher.group(9) != null -> {
                    val boldItalicText = matcher.group(9)!!
                    ssb.replace(start, end, boldItalicText)
                    ssb.setSpan(
                        StyleSpan(Typeface.BOLD_ITALIC),
                        start,
                        start + boldItalicText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 6
                }
                matcher.group(10) != null -> {
                    val highlightedText = matcher.group(10)!!
                    ssb.replace(start, end, highlightedText)
                    val span = RoundedBackgroundSpan(
                        backgroundColor = Color.parseColor("#E0E0E0"),
                        textColor = Color.BLACK,
                        cornerRadius = 8f,
                        paddingHorizontal = 8f,
                        paddingVertical = 2f
                    )
                    ssb.setSpan(span, start, start + highlightedText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    offset -= 2
                }
                matcher.group(11) != null -> { // Link: [link text](url)
                    val linkDisplayText = matcher.group(11)!!
                    val linkUrl = matcher.group(12)!! // Get the URL from the new group
                    val fullMatchLength = matcher.end() - matcher.start()

                    ssb.replace(start, end, linkDisplayText)

                    val roundedBgSpan = RoundedBackgroundSpan(
                        backgroundColor = Color.parseColor("#CCE0FF"),
                        textColor = Color.BLUE,
                        cornerRadius = 8f,
                        paddingHorizontal = 8f,
                        paddingVertical = 2f
                    )
                    ssb.setSpan(roundedBgSpan, start, start + linkDisplayText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                    val linkClickSpan = LinkClickableSpan(linkUrl, activityContext) // Pass URL and context
                    ssb.setSpan(linkClickSpan, start, start + linkDisplayText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                    offset -= (fullMatchLength - linkDisplayText.length)
                }
                matcher.group(13) != null -> { // Larger Text (##text##)
                    val largeText = matcher.group(13)!!
                    ssb.replace(start, end, largeText)
                    ssb.setSpan(
                        RelativeSizeSpan(1.5f),
                        start,
                        start + largeText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 4
                }
                matcher.group(14) != null -> { // Medium Text (#text#)
                    val mediumText = matcher.group(14)!!
                    ssb.replace(start, end, mediumText)
                    ssb.setSpan(
                        RelativeSizeSpan(1.2f),
                        start,
                        start + mediumText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    offset -= 2
                }
            }
        }
        textView.text = ssb
    }

    private inner class ProfileSpan(private val activityContext: Context) : ClickableSpan() {

        override fun onClick(view: View) {
            if (view is TextView) {
                val tv = view
                if (tv.text is Spannable) {
                    val sp = tv.text as Spannable
                    val start = sp.getSpanStart(this)
                    val end = sp.getSpanEnd(this)
                    val objectClicked = sp.subSequence(start, end).toString()
                    val handle = objectClicked.replace("@", "")

                    val getReference = FirebaseDatabase.getInstance().getReference()
                        .child("synapse/username")
                        .child(handle)

                    getReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val uid = dataSnapshot.child("uid").getValue(String::class.java)
                                if (uid != null && uid != "null") {
                                    val intent = Intent(activityContext, ProfileActivity::class.java)
                                    intent.putExtra("uid", uid)
                                    activityContext.startActivity(intent)
                                } else {

                                }
                            } else {

                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                }
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
            ds.color = Color.parseColor("#445E91")
            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
    }

    private inner class LinkClickableSpan(private val url: String, private val activityContext: Context) : ClickableSpan() {
        override fun onClick(view: View) {
            val uri = Uri.parse(url)
            try {
                // Attempt to open using Custom Tabs
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(activityContext, uri)
            } catch (e: Exception) {
                // Fallback to standard browser intent if Custom Tabs fails or is not available
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activityContext.startActivity(intent)
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
            ds.color = Color.BLUE
        }
    }

    class RoundedBackgroundSpan(
        private val backgroundColor: Int,
        private val textColor: Int,
        private val cornerRadius: Float,
        private val paddingHorizontal: Float,
        private val paddingVertical: Float
    ) : ReplacementSpan() {

        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            val width = paint.measureText(text, start, end)
            return (width + 2 * paddingHorizontal).toInt()
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val originalColor = paint.color
            val originalStyle = paint.style

            paint.color = backgroundColor
            paint.style = Paint.Style.FILL
            val width = paint.measureText(text, start, end)
            val rect = RectF(
                x,
                top.toFloat() + paddingVertical,
                x + width + 2 * paddingHorizontal,
                bottom.toFloat() - paddingVertical
            )
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

            paint.color = textColor
            paint.style = originalStyle
            canvas.drawText(text!!, start, end, x + paddingHorizontal, y.toFloat(), paint)

            paint.color = originalColor
        }
    }
}