package com.yangyang.library.log.printer

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yangyang.library.log.R
import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.bean.LogInfo
import com.yangyang.library.log.bean.LogType


/**
 * Create by Yang Yang on 2023/11/1
 */
class ViewPrinter(private val activity: Activity) : ILogPrinter {

    private val recyclerView: RecyclerView by lazy {
        RecyclerView(activity)
    }
    private var logAdapter: LogAdapter
    private var viewPrinterProvider: ViewPrinterProvider

    init {
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        with(recyclerView) {
            logAdapter = LogAdapter(LayoutInflater.from(context))
            val layoutManager = LinearLayoutManager(context)
            setLayoutManager(layoutManager)
            adapter = logAdapter
        }
        viewPrinterProvider = ViewPrinterProvider(rootView, recyclerView)
    }

    override fun print(config: LogConfig, level: Int, tag: String, printString: String) {
        logAdapter.addItem(LogInfo(System.currentTimeMillis(), level, tag, printString))
        recyclerView.smoothScrollToPosition(logAdapter.itemCount - 1)
    }

    fun getViewProvider(): ViewPrinterProvider {
        return viewPrinterProvider
    }

    inner class LogAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<LogViewHolder>() {

        private val logs = mutableListOf<LogInfo>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
            val itemView = inflater.inflate(R.layout.item_view_log, parent, false)
            return LogViewHolder(itemView)
        }

        fun addItem(logInfo: LogInfo) {
            logs.add(logInfo)
            notifyItemInserted(logs.size - 1)
        }

        override fun getItemCount(): Int {
            return logs.size
        }

        override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
            holder.bindData(logs[position])
        }

    }

    inner class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tagView = itemView.findViewById<TextView>(R.id.tag)
        private val messageView = itemView.findViewById<TextView>(R.id.message)

        fun bindData(logInfo: LogInfo) {
            val color = getHighlightColor(logInfo.level)
            tagView.apply {
                text = logInfo.getFlattened()
                setTextColor(color)
            }
            messageView.apply {
                text = logInfo.log
                setTextColor(color)
            }
        }

        private fun getHighlightColor(logLevel: Int): Int {
            return when (logLevel) {
                LogType.V -> {
                    Color.parseColor("#ffbbbbbb")
                }
                LogType.D -> {
                    Color.parseColor("#ffffffff")
                }
                LogType.I -> {
                    Color.parseColor("#ff6a8759")
                }
                LogType.W -> {
                    Color.parseColor("#ffbbb529")
                }
                LogType.E -> {
                    Color.parseColor("#ffff6b68")
                }
                else -> {
                    Color.parseColor("#ffffff00")
                }
            }
        }
    }
}