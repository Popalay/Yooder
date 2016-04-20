package com.popalay.yooder.fragments


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popalay.yooder.R
import com.popalay.yooder.lists.FriendsAdapter
import com.popalay.yooder.models.User
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*

class NotificationsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_notifications, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = FriendsAdapter()
        recycler.adapter = adapter
        VKApi.friends().get(VKParameters.from("order", "name", VKApiConst.FIELDS,
                "id,first_name,last_name,photo_100")).executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val vkUsers = response?.parsedModel as VKList<VKApiUser>
                var users = ArrayList<User>()
                vkUsers.forEach { vkUser ->
                    val user = User(vkUser.id.toString(), vkUser.toString(), vkUser.photo_100)
                    users.add(user)
                    adapter.items = users
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}


