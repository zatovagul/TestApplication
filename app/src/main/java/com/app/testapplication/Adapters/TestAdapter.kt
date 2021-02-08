package com.app.testapplication.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.app.testapplication.Forms.RowForm
import com.app.testapplication.R
import kotlinx.android.synthetic.main.test_row.view.*

class TestAdapter(private val texts:ArrayList<RowForm>): RecyclerView.Adapter<TestAdapter.myHolder>() {
    class myHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view:View=itemView
        init {
            itemView.setOnClickListener(this)

        }
        override fun onClick(v:View){
            Log.d("myHolder","Clicked")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.myHolder {
        val inflatedView=parent.inflate(R.layout.test_row,false)
        return myHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TestAdapter.myHolder, position: Int) {
        val form= texts.get(position)
        holder.itemView.name.text=form.name
        val te=form.num.toString()+" курсов"
        holder.itemView.num.text=te
    }

    override fun getItemCount()=texts.size

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int,attachToRoot:Boolean=false):View{
        return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
    }
}