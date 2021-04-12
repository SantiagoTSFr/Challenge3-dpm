package co.com.personacliente2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.adapter.PersonaAdapter;
import co.com.personacliente2021.service.persona.PersonaServiceImpl;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewPersonas)
    ListView listViewPersonas;
    private PersonaServiceImpl personaService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        personaService = new PersonaServiceImpl(this);
        personaService.getPersona(listViewPersonas);
    }

    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(MainActivity.this,RegistroPersonaActivity.class);
        startActivity(intent);
    }

    public void eliminarListItemPersona(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.eliminar);
        builder.setMessage(R.string.confirm_message_delete_person);
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  eliminarPersona());
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void eliminarPersona() {
        try {
            personaService.eliminar();
            Toast.makeText(this, R.string.persona_eliminada_exitosamente, Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            Toast.makeText(this, R.string.error_inesperado_eliminando_persona, Toast.LENGTH_LONG).show();
        }
    }
}