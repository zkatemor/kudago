package com.zkatemor.kudago.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.zkatemor.kudago.R

class ViewPagerAdapter(private val context: Context, private val images: ArrayList<String>) : PagerAdapter()
{
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == (p1 as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.image_event_card)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        val pager = container as ViewPager
        pager.addView(imageView)

        Picasso.get().load(images[position])
            .error(R.drawable.image_event_card).into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }
}