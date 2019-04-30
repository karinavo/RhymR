package hci.atacuniviehcimya3app;

import android.os.AsyncTask;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void,Void,Void> {
    private String word = "";
    private static String  ergebnis = "";
    private URL url;
    private Spinner category;
    private static CharSequence charSequence;
    private int anzahl;
    private String selected ="";


    @Override
    protected Void doInBackground(Void... voids){
        try {
            category = (Spinner) MainActivity.getSpinner();
            charSequence = (CharSequence) MainActivity.getQuery();
            //check which category was selected
            if(category.getSelectedItemId()==0) {
                url = new URL("https://api.datamuse.com/words?rel_syn="+charSequence);
                selected+=category.getSelectedItem().toString();
            }else if(category.getSelectedItemId()==1) {
                url = new URL("https://api.datamuse.com/words?rel_jjb="+charSequence);
                selected+=category.getSelectedItem().toString();

            }
            else if(category.getSelectedItemId()==2) {
                url = new URL("https://api.datamuse.com/words?rel_nry=" + charSequence);
                selected+=category.getSelectedItem().toString();
            }
            //clear the string for results
            if(!ergebnis.isEmpty()) {
                ergebnis="";
            }
            //API
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //read the results from api
            String line = "";
            while(line!=null) {
                line=bufferedReader.readLine();
                word += line;
            }
            JSONArray jsonArray= new JSONArray(word);
            anzahl = jsonArray.length();
            ergebnis = ergebnis + "Number of words: " + anzahl + "\n";
            //check if the word have the results for this category
            if(jsonArray.length()==0){
                ergebnis+="Sorry, no " + selected +" for this word";
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String object = "     " + jsonObject.getString("word") + "\n";
                    ergebnis = ergebnis + object;
                }
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    //show results to the user
    @Override
    protected  void onPostExecute(Void aVoid){

        super.onPostExecute(aVoid);
        EndScreen.getTextView().setText(this.ergebnis);

    }
    public String getErgebnis(){
        return ergebnis;
    }
}
