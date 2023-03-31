package com.The12sMB.fundamentalsubmission1letsgo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.The12sMB.fundamentalsubmission1letsgo.databinding.FragmentFollowBinding

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)
        val followingViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        val login = arguments?.getString(ARG_LOGIN)
        followingViewModel.getDetailFollowing(login.toString())
        followingViewModel.githubuserfollowing.observe(viewLifecycleOwner) { items ->
            binding.rvFollow.adapter = showRecyclerView(items)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showRecyclerView(list: List<GithubUserResponse>?): MainMenuAdapter {
        val userList = ArrayList<GithubUserResponse>()

        list?.let {
            userList.clear()
            userList.addAll(it)
        }

        return MainMenuAdapter(userList)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_LOGIN =""
    }
}