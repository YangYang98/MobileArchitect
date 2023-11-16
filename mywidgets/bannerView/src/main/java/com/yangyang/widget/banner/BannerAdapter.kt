package com.yangyang.widget.banner

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yangyang.library.utils.ViewUtil
import com.yangyang.widget.banner.bean.IBannerEntity
import com.yangyang.widget.banner.interfaces.IBanner
import com.yangyang.widget.banner.interfaces.IBindAdapter


/**
 * Create by Yang Yang on 2023/11/15
 */
class BannerAdapter(private val context: Context) : PagerAdapter() {

    var bannerClickListener: IBanner.OnBannerClickListener? = null
    val cacheViews = SparseArray<BannerViewHolder>()

    var autoPlay = true
    var loop = false

    var layoutResId = -1

    private var datas = mutableListOf<IBannerEntity>()
    var bindAdapter: IBindAdapter? = null

    fun setBannerData(datas: List<IBannerEntity>) {
        this.datas.addAll(datas)
        initCacheView()
        notifyDataSetChanged()
    }

    fun getFirstItem(): Int {
        return Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % datas.size
    }

    private fun initCacheView() {
        cacheViews.clear()
        datas.forEachIndexed { index, _ ->
            val viewHolder = BannerViewHolder(ViewUtil.newInstance(context, null, layoutResId))
            cacheViews.put(index, viewHolder)
        }
    }

    override fun getCount(): Int {
        return if (autoPlay || loop) {
            Int.MAX_VALUE
        } else {
            datas.size
        }
    }

    fun getRealCount(): Int {
        return datas.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var realPosition = position
        if (datas.isNotEmpty()) {
            realPosition = position % datas.size
        }
        val viewHolder = cacheViews.get(realPosition)
        if (container == viewHolder.rootView.parent) {
            container.removeView(viewHolder.rootView)
        }

        onBind(viewHolder, datas[realPosition], realPosition)
        if (viewHolder.rootView.parent != null) {
            (viewHolder.rootView.parent as ViewGroup).removeView(viewHolder.rootView)
        }
        container.addView(viewHolder.rootView)

        return viewHolder.rootView
    }

    private fun onBind(
        viewHolder: BannerViewHolder,
        iBannerEntity: IBannerEntity,
        position: Int
    ) {
        viewHolder.rootView.setOnClickListener {
            bannerClickListener?.onBannerClick(viewHolder, iBannerEntity, position)
        }
        bindAdapter?.onBindAdapter(viewHolder, iBannerEntity, position)
    }

    override fun getItemPosition(`object`: Any): Int {
        //让item每次都会刷新
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //adapter的数据变化会被ViewPager观察到，然后调用destroyItem销毁View，cacheViews里的VIew就被销毁了，这里不希望View被销毁
        //super.destroyItem(container, position, `object`)
    }

    inner class BannerViewHolder(val rootView: View) {

        private val viewHolderSparseArr: SparseArray<View> by lazy {
            SparseArray(1)
        }

        fun <T : View> findViewById(id: Int): T {
            if (rootView !is ViewGroup) {
                return rootView as T
            }


            var child: T? = viewHolderSparseArr.get(id) as? T
            if (child == null) {
                child = rootView.findViewById(id)
                viewHolderSparseArr.put(id, child)
            }

            return child!!
        }
    }
}