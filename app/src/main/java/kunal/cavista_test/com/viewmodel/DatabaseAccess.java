package kunal.cavista_test.com.viewmodel;

import android.content.Context;

import java.util.ArrayList;

import kunal.cavista_test.com.database.DatabaseHelper;

public class DatabaseAccess {

    Context context;
    ArrayList<String> ListComments = new ArrayList<>();

    public DatabaseAccess(Context context) {
        this.context = context;
    }

    public ArrayList<String> getCommentsFromDatabase(String link) {
        DatabaseHelper DH = new DatabaseHelper(context);

        ListComments = DH.getAllComments(link);

        return ListComments;
    }

}
