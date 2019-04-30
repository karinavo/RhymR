package hci.atacuniviehcimya3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  private Button search;
  private static Spinner spinner;//Select a category
  private SearchView sv1;
  private static CharSequence query;
  private ImageView myfav;
  private ScrollView scrollView;


    //for the second screen
    public void init(){
        search = (Button) findViewById(R.id.querybutton);
        search.setText("Search");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EndScreen.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv1 = (SearchView) findViewById(R.id.searchView1);
        query = sv1.getQuery();

        // For the dropdown menu
        spinner =(Spinner) findViewById(R.id.spinner);
        // View for the results
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        myfav = (ImageView) findViewById(R.id.imageView1);

        //shows which category is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myfav.setOnClickListener(this);
        //go to the end-screen
        init();

    }
// get methods
    public static CharSequence getQuery() {

        return query;
    }
    public  static Spinner getSpinner() {
        return spinner;
    }
//shows the my favourite list
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView1:
                //clear the previous scroll view
                scrollView.removeAllViews();
                //create the layout
                final LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                List<String> list = new ArrayList<String>(EndScreen.getList_of_fav());
                final ListView listView= new ListView(this);// for each result i have a view
                //to display each result within view:
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt = (TextView) view;
                        System.out.println(txt.getText().toString());
                    }
                });

                linearLayout.addView(listView);
                scrollView.addView(linearLayout);

                break;
       }
    }
}
