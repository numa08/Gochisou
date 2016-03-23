package android.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import net.numa08.gochisou.R

class CustomSymbolButton : Button {
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : super(context, attrs, defStyleAttr) {
        attrs?.let { attrs ->
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSymbolButton)
            try {
                val symbol: String? = typedArray.getString(R.styleable.CustomSymbolButton_symbol)
                symbol?.let { setSymbol(it) }
            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setSymbol(symbol: String) {
        val typeface: Typeface? = Typeface.createFromAsset(context.assets, symbol)
        typeface?.let { setTypeface(it) }
    }
}