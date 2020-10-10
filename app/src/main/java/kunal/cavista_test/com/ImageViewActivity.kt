package kunal.cavista_test.com

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import kunal.cavista_test.com.adapter.CommentListAdapter
import kunal.cavista_test.com.database.DatabaseHelper

class ImageViewActivity : AppCompatActivity() {

    @BindView(R.id.mImage)
    internal var mImage: ImageView? = null

    @BindView(R.id.edtComment)
    internal var edtComment: EditText? = null

    @BindView(R.id.btnSubmit)
    internal var btnSubmit: Button? = null

    @BindView(R.id.rvCommentList)
    internal var rvCommentList: RecyclerView? = null

    internal var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        ButterKnife.bind(this)

        val layoutManager = LinearLayoutManager(this)
        rvCommentList!!.layoutManager = layoutManager
        rvCommentList!!.setHasFixedSize(true)


        link = intent.getStringExtra("LINK")

        loadComments(link)

        Picasso.get()
                .load(link)
                .into(mImage)

        btnSubmit!!.setOnClickListener {
            if (edtComment!!.text.toString().length > 0) {
                val DH = DatabaseHelper(this@ImageViewActivity)
                DH.saveComment(link, edtComment!!.text.toString())
                DH.close()
                edtComment!!.setText("")
                loadComments(link)
            } else {
                Toast.makeText(this@ImageViewActivity, "Please enter comment..", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun loadComments(link: String?) {
        val DH = DatabaseHelper(this@ImageViewActivity)
        val list = DH.getAllComments(link)

        val adapter = CommentListAdapter(this@ImageViewActivity, list)
        rvCommentList!!.adapter = adapter

    }
}
