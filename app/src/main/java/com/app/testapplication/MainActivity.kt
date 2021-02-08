package com.app.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.app.testapplication.Adapters.TestAdapter
import com.app.testapplication.Forms.RowForm
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var requestQueue:RequestQueue?=null
    val form=ArrayList<RowForm>()
    val adapter=TestAdapter(form)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestQueue=Volley.newRequestQueue(this)
        linearLayoutManager= LinearLayoutManager(this)
        textView.text="Изучайте актуальные темы"
        recyclerView.layoutManager=linearLayoutManager

        drawerFrame.visibility= View.GONE
        /*for(i in 0..4){
            form.add(RowForm("F",i))
        }*/
        recyclerView.adapter=adapter
        httpReq()
    }
    private fun httpReq(){
        form.clear()
        drawerFrame.visibility=View.VISIBLE
        val url="https://gitcdn.link/repo/netology-code/rn-task/master/netology.json"
        val request=JsonObjectRequest(Request.Method.GET,url, null, {
                response -> try {
                    val array=response.getJSONArray("data")
                    println(array.length().toString())
                    for(i in 0 until array.length()){
                        val jOb=array.getJSONObject(i)
                        val curs=jOb.getJSONArray("groups")
                        val name=jOb.getJSONObject("direction").getString("title")
                        val form=RowForm(name,curs.length())
                        this.form.add(form)
                    }
                    adapter.notifyDataSetChanged()
                    drawerFrame.visibility=View.GONE
                } catch (e:JSONException){
                    e.printStackTrace()
                    Toast.makeText(this,"Проблемы парсинга",Toast.LENGTH_SHORT)
                    drawerFrame.visibility=View.GONE
                }
        }, { error -> error.printStackTrace()
            Toast.makeText(this,"Проблемы подключения",Toast.LENGTH_SHORT)
            drawerFrame.visibility=View.GONE})
        requestQueue?.add(request)
    }
}