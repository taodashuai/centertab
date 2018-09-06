package com.crisw.centertab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //如果要让其强行居中，前后加空即可
    val datas = arrayListOf("", "1", "2", "3", "4", "5", "6", "7", "")
    var windowWidth = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取屏宽
        windowWidth = window.windowManager.defaultDisplay.width / 3
        initView()

    }

    private fun initView() {
        rv.adapter = RvAdapter()
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //使rv获得粘性效果
        LinearSnapHelper().attachToRecyclerView(rv)
    }

    inner class RvAdapter : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.rv_item, parent, false) as View
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTv.setText(datas[position])
            holder.mTv.width = windowWidth
            holder.mTv.setOnClickListener {
                //点击判断，目前只处理了出现一页3个情况，如有更多，可自行拓展
                if (position != 0 && position != datas.size - 1) {
                    val index = position - (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    when (index) {
                        0 -> {
                            rv.smoothScrollToPosition(position - 1)
                        }
                        1 -> {
                            rv.smoothScrollToPosition(position)
                        }
                        else -> {
                            rv.smoothScrollToPosition(position + 1)
                        }
                    }

                }
            }
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            internal var mTv: TextView

            init {
                mTv = itemView.findViewById(R.id.tv) as TextView

            }
        }

    }


}
