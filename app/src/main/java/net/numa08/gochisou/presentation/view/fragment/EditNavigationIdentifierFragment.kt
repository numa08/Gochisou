package net.numa08.gochisou.presentation.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import kotlinx.android.synthetic.main.fragment_edit_navigation_identifier.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import org.jetbrains.anko.find
import javax.inject.Inject

class EditNavigationIdentifierFragment : Fragment() {

    val dragDropManager = RecyclerViewDragDropManager()
    lateinit var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_edit_navigation_identifier, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Adapter(navigationIdentifierRepository)
        recycler.adapter = dragDropManager.createWrappedAdapter(adapter)
        recycler.addItemDecoration(DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL_LIST))
        recycler.itemAnimator = RefactoredDefaultItemAnimator()
        dragDropManager.attachRecyclerView(recycler)
    }

    override fun onPause() {
        super.onPause()
        dragDropManager.cancelDrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dragDropManager.release()
    }
}

internal class Adapter(val repository: NavigationIdentifierRepository) : RecyclerView.Adapter<ViewHolder>(), DraggableItemAdapter<ViewHolder> {
    init {
        setHasStableIds(true)
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        repository.move(fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCheckCanStartDrag(holder: ViewHolder?, position: Int, x: Int, y: Int): Boolean = holder?.let {
        val rect = Rect(it.handle.left, it.handle.top, it.handle.right, it.handle.bottom)
        rect.intersect(x, y, x, y)
    } ?: false

    override fun onGetItemDraggableRange(holder: ViewHolder?, position: Int): ItemDraggableRange? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? = parent?.let {
        val view = LayoutInflater.from(it.context).inflate(R.layout.row_navigation_identifier, it, false)
        ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.text?.text = repository[position].name
    }

    override fun getItemCount(): Int = repository.size

    override fun getItemId(position: Int): Long = repository[position].hashCode().toLong()

}

internal class ViewHolder(val view: View) : AbstractDraggableItemViewHolder(view) {
    val text by lazy { view.find<TextView>(R.id.text) }
    val handle by lazy { view.find<View>(R.id.handle) }
}