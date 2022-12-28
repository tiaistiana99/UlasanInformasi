package com.kotlin.tiaistiana.ui.informasi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.tiaistiana.databinding.RowNewsArticleBinding
import com.kotlin.tiaistiana.repository.model.informasi.News
import com.kotlin.tiaistiana.utils.load



/**
 * Adaptor Berita untuk menampilkan berita dalam daftar.
 */

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    /**
     * Daftar artikel berita
     */

    private var newsArticles: List<News> = emptyList()


    var onNewsClicked: ((News) -> Unit)? = null

    /**
     * Mengembang tampilan
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemBinding =
            RowNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(itemBinding)
    }


    /**
     * Mengikat tampilan dengan data
     */

    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) =
        newsHolder.bind(newsArticles[position])

    /**
     * Jumlah item dalam daftar untuk ditampilkan
     */
    override fun getItemCount() = newsArticles.size

    /**
     * Lihat Pola Pemegang
     */

    inner class NewsHolder(private val itemBinding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(newsArticle: News) = with(itemView) {
            itemBinding.ivNewsImage.load(itemBinding.root.context, newsArticle.urlToImage ?: "")
            itemBinding.root.setOnClickListener {
                onNewsClicked?.invoke(newsArticle)
            }

        }
    }

    /**
     * Tukar fungsi untuk mengatur data baru saat memperbarui
     */
    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(items: List<News>) {
        newsArticles = items
        notifyDataSetChanged()
    }
}