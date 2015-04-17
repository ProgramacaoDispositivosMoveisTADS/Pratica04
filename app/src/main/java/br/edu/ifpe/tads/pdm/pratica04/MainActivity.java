package br.edu.ifpe.tads.pdm.pratica04;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        //Parte 2: Salvando dados no armazenamento interno do dispositivo
        /*
        *   Passo 2: Modifique o método buttonSaveClick() para conter o seguinte código:
        *   Esse código estende o código anterior para suportar gravação em um arquivo
        *   no armazenamento interno identificado pelo valor no campo PrefName. A API
        *   usada para acessar o arquivo é opeFileOutput(), que retorna um
        *   FileOutputStream, porém é possível fazer o mesmo através de um objeto File.
        */

    public void buttonSaveClick(View view) {

        //
        /*
        String prefName = ((EditText)findViewById(R.id.edit_prefname)).getText().toString();
         String prefValue =((EditText)findViewById(R.id.edit_prefvalue)).getText().toString();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefName, prefValue);
        editor.apply();*/


        boolean saveToFile =
                ((CheckBox) findViewById(R.id.checkbox_savetofile)).isChecked();
        String prefName =
                ((EditText) findViewById(R.id.edit_prefname)).getText().toString();
        String prefValue =
                ((EditText) findViewById(R.id.edit_prefvalue)).getText().toString();
        if (!saveToFile) {
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(prefName, prefValue);
            editor.apply();
        } else {
            DataOutputStream outputStream;
            try {
                outputStream =
                        new DataOutputStream(openFileOutput(prefName, Context.MODE_PRIVATE));
                outputStream.writeUTF(prefValue);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

  //Parte 2: Salvando dados no armazenamento interno do dispositivo
 /*
  *  Passo 3: Modifique o método buttonReadClick()
  *  Esse método trabalha de forma similar ao anterior, porém obtendo um
  *  FileInputStream para realizar a leitura da String do arquivo. Caso a tentativa de
  *  ler o arquivo seja mal sucedida, a mensagem “ERRO” deve aparecer no campo
  *  PrefValue.
  */

    public void buttonReadClick(View view) {
        /*
        String prefName = ((EditText)findViewById(R.id.edit_prefname)).getText().toString();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String prefValue = prefs.getString(prefName, "NOT_FOUND");
        ((EditText)findViewById(R.id.edit_prefvalue)).setText(prefValue);
        */

        boolean saveToFile =
                ((CheckBox) findViewById(R.id.checkbox_savetofile)).isChecked();
        String prefName =
                ((EditText) findViewById(R.id.edit_prefname)).getText().toString();
        String prefValue = "ERROR";
        if (!saveToFile) {
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            prefValue = prefs.getString(prefName, "NOT_FOUND");
        } else {
            DataInputStream inputStream;
            try {
                inputStream = new DataInputStream(openFileInput(prefName));
                prefValue = inputStream.readUTF();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ((EditText) findViewById(R.id.edit_prefvalue)).setText(prefValue);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
