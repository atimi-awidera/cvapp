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
import com.atimi.cvapp.databinding.ExperienceHeaderViewBinding
import com.atimi.cvapp.databinding.ExperienceEntryViewBinding
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.model.PersonalDetails
import com.atimi.cvapp.model.PersonalStatement
import com.atimi.cvapp.model.ExperienceHeader
import com.atimi.cvapp.model.ExperienceEntry

class CvAdapter : RecyclerView.Adapter<CvAdapter.ViewHolder>() {

    private var entries: List<CvEntry> = emptyList()

    //This should always be private, I know it might be tempting to use it in the CvDocument and
    //it would "save" some lines but it'll also create strong linkage between the two making the
    //code less reusable
    private val personalDetails = 1
    private val personalStatement = 2
    private val experienceHeader = 3
    private val experienceEntry = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Consider NullHolder instead of falling back to ExperienceEntry if you wish to make it
        //more general and re-usable
        if (viewType == personalDetails) {
            return PersonalDetailsViewHolder(parent)
        } else if (viewType == personalStatement) {
            return PersonalStatementViewHolder(parent)
        } else if (viewType == experienceHeader) {
            return ExperienceHeaderViewHolder(parent)
        } else {
            return ExperienceEntryViewHolder(parent)
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
        } else
        if (holder is ExperienceHeaderViewHolder && getItemViewType(position) == experienceHeader) {
            val experienceHeader = entries[position] as ExperienceHeader
            holder.bind(experienceHeader)
        } else
        if (holder is ExperienceEntryViewHolder && getItemViewType(position) == experienceEntry) {
            val experienceEntry = entries[position] as ExperienceEntry
            holder.bind(experienceEntry)
        }
    }

    fun update(items: List<CvEntry>) {
        this.entries = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (entries[position].getType() == "Personal Details") {
            return personalDetails
        } else if (entries[position].getType() == "Personal Statement") {
            return personalStatement
        } else if (entries[position].getType() == "Experience Header") {
            return experienceHeader
        } else {
            return experienceEntry
        }
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    //
    // ViewHolders - Consider moving them to separate files when the number grows
    //

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

    class ExperienceHeaderViewHolder(
        private val parent: ViewGroup,
        private val binding: ExperienceHeaderViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.experience_header_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: ExperienceHeader) {
            binding.title = item.title
        }
    }

    class ExperienceEntryViewHolder(
        private val parent: ViewGroup,
        private val binding: ExperienceEntryViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.experience_entry_view,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {
        fun bind(item: ExperienceEntry) {
            binding.description = item.description
            binding.timeframe = item.timeframe
            binding.company = item.company
        }
    }
}

// Avoid the Gradle 3.3 Gradle Plugin Kotlin compiler warning Binding adapter AK(...) already exists for entries
@BindingAdapter("entries")
fun RecyclerView.bindItems(items: List<CvEntry>) {
    val adapter = adapter as CvAdapter
    adapter.update(items)
}