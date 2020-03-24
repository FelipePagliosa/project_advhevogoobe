package br.project_advhevogoober_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var offererId = intent.getStringExtra("id")

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        adapter.add(ChatItem())
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter=adapter
    }

    class ChatItem:Item<ViewHolder>(){

        override fun bind(viewHolder: ViewHolder, position: Int) {
        }
        override fun getLayout(): Int {
            return R.layout.layout_message_from
        }
    }
}
