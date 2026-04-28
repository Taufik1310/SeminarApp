package com.example.seminarapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val list: List<MainItem>,
    private val onFeatureClick: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FEATURE = 1
        const val TYPE_UPCOMING = 2
        const val TYPE_SECTION = 3
        const val TYPE_SEMINAR = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is MainItem.Header -> TYPE_HEADER
            is MainItem.Feature -> TYPE_FEATURE
            is MainItem.Upcoming -> TYPE_UPCOMING
            is MainItem.SectionTitle -> TYPE_SECTION
            is MainItem.SeminarItem -> TYPE_SEMINAR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> HeaderVH(inflater.inflate(R.layout.item_header, parent, false))
            TYPE_FEATURE -> FeatureVH(
                inflater.inflate(R.layout.item_feature, parent, false),
                onFeatureClick
            )
            TYPE_UPCOMING -> UpcomingVH(inflater.inflate(R.layout.item_upcoming, parent, false))
            TYPE_SECTION -> SectionVH(inflater.inflate(R.layout.item_section, parent, false))
            else -> SeminarVH(inflater.inflate(R.layout.item_seminar, parent, false))
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = list[position]) {
            is MainItem.Header -> (holder as HeaderVH).bind(item)

            is MainItem.Feature -> {}

            is MainItem.Upcoming -> (holder as UpcomingVH).bind()

            is MainItem.SectionTitle -> (holder as SectionVH).bind(item)

            is MainItem.SeminarItem -> (holder as SeminarVH).bind(item.seminar)
        }
    }

    // ===== VIEW HOLDERS =====

    class HeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        private val txtGreeting: TextView = view.findViewById(R.id.txtGreeting)
        fun bind(item: MainItem.Header) {
            txtGreeting.text = "Hi, ${item.name} 👋"
        }
    }

    class FeatureVH(view: View, private val onClick: () -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val btn = view.findViewById<Button>(R.id.btnSeminar)

        init {
            btn.setOnClickListener {
                onClick()
            }
        }
    }

    class UpcomingVH(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {

        }
    }

    class SectionVH(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.txtSection)

        fun bind(item: MainItem.SectionTitle) {
            txtTitle.text = item.title
        }
    }

    class SeminarVH(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.txtTitle)
        private val subtitle: TextView = view.findViewById(R.id.txtSubtitle)
        private val time: TextView = view.findViewById(R.id.txtTime)
        private val status: TextView = view.findViewById(R.id.txtStatus)

        fun bind(data: Seminar) {
            title.text = data.title
            subtitle.text = data.subtitle
            time.text = data.time
            status.text = data.status
        }
    }
}