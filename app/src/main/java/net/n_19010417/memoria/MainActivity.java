package net.n_19010417.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombre = editText1.getText().toString();
                        String contenido = editText2.getText().toString();

                        try {
                            OutputStreamWriter outputStreamWriter =
                                    new OutputStreamWriter(openFileOutput(nombre,
                                            Context.MODE_PRIVATE));
                            outputStreamWriter.write(contenido);
                            outputStreamWriter.close();
                        }catch (Exception ex){
                            Log.e("Archivo", "Error al escribir en la memoria Interna");
                        }
                        editText1.setText("");
                        editText2.setText("");
                        editText3.setText(getFilesDir().getPath().toString());
                    }
                }
        );

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombre = editText1.getText().toString();
                        String contenido = editText2.getText().toString();
                        String filePath = "Memoria";
                        if (isExternalStorageAvaliable()){
                            File file = new File(getExternalFilesDir(filePath),
                                    nombre);
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                fileOutputStream.write(contenido.getBytes());
                                fileOutputStream.close();
                            }catch (Exception e){
                                Log.e("Archivo", "Error al escribir en memoria Externa");
                            }
                            editText1.setText("");
                            editText2.setText("");
                            editText3.setText(file.getPath().toString());
                        }
                    }
                }
        );
        //editar
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombre = editText1.getText().toString();
                        String contenido = "";
                        try {
                            BufferedReader bufferedReader = new BufferedReader(
                                    new InputStreamReader(openFileInput(nombre)));
                            String tmp = bufferedReader.readLine();
                            while(tmp!=null){
                                contenido = contenido + tmp;
                                tmp = bufferedReader.readLine();
                            }
                        }catch (Exception exception){
                            Log.e("Archivos", "Error al leer en la memoria Interna");
                        }
                        editText1.setText("");
                        editText2.setText(contenido);
                        editText3.setText(getFilesDir().getPath().toString());
                    }
                }
        );

        button4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombre = editText1.getText().toString();
                        String contenido = editText2.getText().toString();
                        String filePath = "Memoria";
                        if (isExternalStorageAvaliable()){
                            File file = new File(getExternalFilesDir(filePath),
                                    nombre);
                            try {
                                FileInputStream fileInputStream = new FileInputStream(file);
                                DataInputStream dataInputStream =
                                        new DataInputStream(fileInputStream);
                                BufferedReader bufferedReader =
                                        new BufferedReader(new InputStreamReader(dataInputStream));
                                String tmp;
                                while((tmp=bufferedReader.readLine()) !=null){
                                    contenido = contenido + tmp;
                                }
                                bufferedReader.close();
                            }catch (Exception ex){
                                Log.e("Archivos", "Error al leer la memoria Externa");
                            }
                            editText1.setText("");
                            editText2.setText(contenido);
                            editText3.setText(file.getPath().toString());
                        }
                    }
                }
        );

        button5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.exit(0);
                    }
                }
        );//cierre button5-Salir


    }

    private boolean isExternalStorageAvaliable(){
        String estadoAlmacenamientoExterno = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(estadoAlmacenamientoExterno))
            return true;
        else
            return false;
    }
}