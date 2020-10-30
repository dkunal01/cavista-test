package kunal.cavista_test.com.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_view.*
import kunal.cavista_test.com.R
import kunal.cavista_test.com.database.DatabaseHelper
import kunal.cavista_test.com.viewmodel.CommentListAdapter
import kunal.cavista_test.com.viewmodel.DatabaseAccess

class ImageViewActivity : AppCompatActivity() {

    internal var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

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
        val DA = DatabaseAccess(this@ImageViewActivity)
        val list = DA.getCommentsFromDatabase(link)

        val adapter = CommentListAdapter(this@ImageViewActivity, list)
        rvCommentList!!.adapter = adapter

    }
}
