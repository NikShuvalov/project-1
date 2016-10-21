package shuvalov.nikita.todoornottodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SubListActivity extends AppCompatActivity {
    private RecyclerView subListRecyView;
    private LinearLayoutManager linearLayoutManager;
    private Button addItemButt, backButt;
    private int mPosition;
    private TextView listNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);

        Intent superListIntent = getIntent();
        mPosition=superListIntent.getIntExtra("Position",-1);

        subListRecyView = (RecyclerView) findViewById(R.id.errandRecycler);
        linearLayoutManager = new LinearLayoutManager(SubListActivity.this, LinearLayoutManager.VERTICAL, false);
        subListRecyView.setLayoutManager(linearLayoutManager);
        listNameView = (TextView)findViewById(R.id.listnameview);
        listNameView.setText(superListIntent.getStringExtra("ListName"));

        addItemButt = (Button)findViewById(R.id.additem);
        backButt = (Button)findViewById(R.id.backButt);

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        addItemButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubListActivity.this, ErrandEntryActivity.class);
                startActivityForResult(intent, MainActivity.RETRIEVE_CONTENT);
            }
        });

        SubListRecyclerAdapter subListRecAdp = new SubListRecyclerAdapter(MasterLister.retriveToDoLists().get(mPosition).getErrandsList());
        subListRecyView.setAdapter(subListRecAdp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode == MainActivity.RETRIEVE_CONTENT){
                if(mPosition==-1){
                    Toast.makeText(this, "There was an out of bounds issue", Toast.LENGTH_SHORT).show();
                }else{
                    MasterLister.getErrEncapInPosition(mPosition).appendErrand(new Errands(data.getStringExtra(String.valueOf(R.string.user_note))));
                    subListRecyView.getAdapter().notifyItemChanged(mPosition);
                }
            }
            }
        else{
            Toast.makeText(this, "Add Note Cancelled", Toast.LENGTH_SHORT).show();
        }

    }
}
