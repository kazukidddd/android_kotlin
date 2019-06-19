package com.example.rssreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity(),LoaderManager.LoaderCallbacks<Rss> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        loaderManager.initLoader(1,null,this)
    }

    //
    override fun onCreateLoader(id:Int,args:Bundle?) = RssLoader(this)

    //
    override fun onLoaderReset(loader: Loader<Rss>) {
        //特に何もしない
    }

    //
    override fun onLoadFinished(loader: Loader<Rss>, data: Rss?) {
        //
        if (data != null){

            //
            val recyclerView = findViewById<RecyclerView>(R.id.articles)

            //
            val adapter = ArticlesAdapter(this,data.articles){ article ->
                //記事をタップしたときの処理
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this, Uri.parse(article.link))
            }
            recyclerView.adapter = adapter

            //
            val layoutManager = GridLayoutManager(this,2)

            recyclerView.layoutManager = layoutManager
        }
    }

}
