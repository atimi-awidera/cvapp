package com.atimi.cvapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.atimi.cvapp.R
import com.atimi.cvapp.databinding.PersonalDetailsViewBinding
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.model.PersonalDetails

class CvAdapter : RecyclerView.Adapter<CvAdapter.ViewHolder>() {

    private var entries: List<CvEntry> = emptyList()

    private val personalDetails = 0
    private val personalStatement = 1
    private val experienceHeader = 2
    private val experienceEntry = 3
    private val educationHeader = 4
    private val educationEntry = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PersonalDetailsViewHolder(parent)
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ( position >= entries.size ) {
            return
        }
        if (holder is PersonalDetailsViewHolder && getItemViewType(position) == personalDetails) {
            val personalDetails = entries[position] as PersonalDetails
            holder.bind(personalDetails)
        }
    }

    fun update(items: List<CvEntry>) {
        this.entries = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = personalDetails

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class PersonalDetailsViewHolder(
        private val parent: ViewGroup,
        private val binding: PersonalDetailsViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.personal_details_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: PersonalDetails) {
            binding.name = item.name
            binding.surname = item.surname
        }
    }
}

// Avoid the Gradle 3.3 Gradle Plugin
// Kotlin compiler warning Binding adapter AK(...) already exists for entries
@BindingAdapter("entries")
fun RecyclerView.bindItems(items: List<CvEntry>) {
    val adapter = adapter as CvAdapter
    adapter.update(items)
}