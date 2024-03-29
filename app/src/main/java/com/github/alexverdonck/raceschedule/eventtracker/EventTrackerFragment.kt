package com.github.alexverdonck.raceschedule.eventtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.data.Events
import com.github.alexverdonck.raceschedule.databinding.FragmentEventTrackerBinding


class EventTrackerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding: FragmentEventTrackerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = EventTrackerViewModelFactory(application)

        val eventTrackerViewModel = ViewModelProvider(this, viewModelFactory)[EventTrackerViewModel::class.java]

        binding.eventTrackerViewModel = eventTrackerViewModel

        binding.lifecycleOwner = this

        val adapter = EventAdapter(EventListener { event ->
            eventTrackerViewModel.onEventClicked(event)
        })

        binding.eventList.adapter = adapter

        eventTrackerViewModel.navigateToEventDetail.observe(viewLifecycleOwner) { event ->
            event?.let {
                this.findNavController().navigate(
                    EventTrackerFragmentDirections.actionEventTrackerFragmentToEventDetailFragment(
                        event
                    )
                )
                eventTrackerViewModel.onEventDetailNavigated()
            }
        }

        Events.events.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }
}