package com.github.alexverdonck.raceschedule.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // get reference to binding object and inflate fragment view
        val binding: FragmentEventDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_event_detail, container, false)

        val arguments = EventDetailFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = EventDetailViewModelFactory(arguments.selectedEvent)

        val eventDetailViewModel = ViewModelProvider(this, viewModelFactory)[EventDetailViewModel::class.java]

        val adapter = SessionAdapter()

        binding.eventSessions.adapter = adapter

        binding.eventDetailViewModel = eventDetailViewModel

        binding.lifecycleOwner = this

        eventDetailViewModel.event.observe(viewLifecycleOwner) {
            it?.let {
                // convert map to a list
                adapter.submitList(it.sessions.toList())
            }
        }

        return binding.root

    }
}