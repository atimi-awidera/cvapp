package com.atimi.cvapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.atimi.cvapp.R
import com.atimi.cvapp.databinding.PersonalDetailsViewBinding
import com.atimi.cvapp.databinding.PersonalStatementViewBinding
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.model.PersonalDetails
import com.atimi.cvapp.model.PersonalStatement

class CvAdapter : RecyclerView.Adapter<CvAdapter.ViewHolder>() {

    private var entries: List<CvEntry> = emptyList()

    private val personalDetails = 1
    private val personalStatement = 2
    private val experienceHeader = 3
    private val experienceEntry = 4
    private val educationHeader = 5
    private val educationEntry = 6

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == personalDetails) {
            return PersonalDetailsViewHolder(parent)
        } else {
            return PersonalStatementViewHolder(parent)
        }
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ( position >= entries.size ) {
            return
        }
        if (holder is PersonalDetailsViewHolder && getItemViewType(position) == personalDetails) {
            val personalDetails = entries[position] as PersonalDetails
            holder.bind(personalDetails)
        } else
        if (holder is PersonalStatementViewHolder && getItemViewType(position) == personalStatement) {
            val personalStatement = entries[position] as PersonalStatement
            holder.bind(personalStatement)
        }
    }

    fun update(items: List<CvEntry>) {
        this.entries = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (entries[position].getType() == "Personal Details") {
            return personalDetails
        } else {
            return personalStatement
        }
    }

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

    class PersonalStatementViewHolder(
        private val parent: ViewGroup,
        private val binding: PersonalStatementViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.personal_statement_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: PersonalStatement) {
            binding.statement = item.statement
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