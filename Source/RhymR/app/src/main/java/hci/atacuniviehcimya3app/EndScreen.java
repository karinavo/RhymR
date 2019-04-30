package hci.atacuniviehcimya3app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class EndScreen extends AppCompatActivity implements View.OnClickListener {
    //views for the results:
    private ScrollView scrollView;
    private static TextView tv;
    //images for add_to_favourites and my_favourites:
    private ImageView fav, myfav;
    //for the category:
    private Spinner spinner_cat;
    //get data from another class:
    private final FetchData process = new FetchData();
    //shows to user in "my favourites":
    private String toSave = "";
    //save data into the set:
    private static Set<String> list_of_fav = new HashSet<String>();
    private boolean isInFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        isInFavourite = false;
        fav = (ImageView) findViewById(R.id.fav);
        myfav = (ImageView) findViewById(R.id.myfav);
        // get the category that selected:
        spinner_cat = (Spinner) MainActivity.getSpinner();
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        tv = new TextView(this);
        scrollView.addView(tv);
        try {
            // get the results
            process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // clickable images
        fav.setOnClickListener(this);
        myfav.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //this line shows the results to user
        toSave += " Word: " + MainActivity.getQuery().toString() + "\n" + " Category: " + spinner_cat.getSelectedItem().toString() +  '\n'+ " " + process.getErgebnis();
        switch (v.getId()) {
            case R.id.fav:
                // user cannot click on "add to favourites" if he is in "my favourite" field
                if(!isInFavourite){
                    addToSet(toSave);
                    toSave="";
                    break;
                }

            case R.id.myfav:
                //clear the scrollview
                scrollView.removeAllViews();
                isInFavourite = true;
                //create layout
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));


                List<String> list = new ArrayList<String>(list_of_fav);
                final ListView listView= new ListView(this);// for each result i have a view
                //to display each result within view:
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

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

    //SAVE Results to Set
    public void addToSet(String data) {
            // check if results are in list
            if(list_of_fav.contains(data)){
                Toast.makeText(EndScreen.this, "Word already exists in Favorites List", Toast.LENGTH_SHORT).show();

            }else {
                System.out.println(data);
                list_of_fav.add(data);
                Toast.makeText(EndScreen.this, "Added to Favorites", Toast.LENGTH_SHORT).show();

            }

    }
// get methods
    public static TextView getTextView(){
        return tv;
    }
    public  static Set<String> getList_of_fav(){
        return list_of_fav;
    }
}