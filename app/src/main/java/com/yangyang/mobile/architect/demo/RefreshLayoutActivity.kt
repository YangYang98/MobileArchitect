package com.yangyang.mobile.architect.demo

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.yangyang.mobile.architect.R
import com.yangyang.widget.refresh.RefreshLayout
import com.yangyang.widget.refresh.header.TextHeaderView
import com.yangyang.widget.refresh.interfaces.IRefresh


/**
 * Create by Yang Yang on 2023/11/13
 */
class RefreshLayoutActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_layout)
        val refreshLayout = findViewById<RefreshLayout>(R.id.refresh_layout)
        val xOverView = TextHeaderView(this)
        /*val lottieOverView =
            HiLottieOverView(this)*/
        refreshLayout.setRefreshHeaderView(xOverView)
        refreshLayout.setRefreshListener(object :
            IRefresh.RefreshListener {
            override fun onRefresh() {
                Handler().postDelayed({ refreshLayout.refreshFinished() }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
        refreshLayout.setDisableRefreshScroll(true)
        //initRecycleView()
    }

    var myDataset =
        arrayOf(
            "HiRefresh",
            "HiRefresh",
            "HiRefresh",
            "HiRefresh",
            "HiRefresh",
            "HiRefresh",
            "HiRefresh"
        )

    /*private fun initRecycleView() {
        recyclerView = findViewById<View>(R.id.recycleview) as RecyclerView
        // use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
        recyclerView!!.setHasFixedSize(true)
        // use a linear layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        // specify an adapter (see also next example)
        val mAdapter =
            MyAdapter(
                myDataset
            )
        recyclerView!!.setAdapter(mAdapter)
    }

    class MyAdapter // Provide a suitable constructor (depends on the kind of dataset)
        (private val mDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
        class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            // each data item is just a string in this case
            var textView: TextView

            init {
                textView = v.findViewById(R.id.tv_title)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder { // create a new view
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            return MyViewHolder(
                v
            )
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(
            holder: MyViewHolder,
            position: Int
        ) { // - get element from your dataset at this position
// - replace the contents of the view with that element
            holder.textView.text = mDataset[position]
            holder.itemView.setOnClickListener { L.d("position:$position") }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount(): Int {
            return mDataset.size
        }

    }*/
}