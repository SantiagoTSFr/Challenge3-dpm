package co.com.personacliente2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.PersonaServiceImpl;
import co.com.personacliente2021.service.tipodocumento.TipoDocumentoServiceImpl;
import co.com.personacliente2021.util.ActionBarUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegistroPersonaActivity extends AppCompatActivity {

    @BindView(R.id.txt_documento)
    EditText txtDocumento;

    @BindView(R.id.txt_nombre)
    EditText txtNombre;

    @BindView(R.id.txt_apellido)
    EditText txtApellido;

    @BindView(R.id.spinnerTipoDocumento)
    Spinner spinnerTipoDocumento;

    PersonaDTO persona;
    PersonaDTO personaActualizar;
    private Integer documentoSeleccionado;
    boolean esInsert = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        ButterKnife.bind(this);
        persona = new PersonaDTO();
        personaActualizar = (PersonaDTO) getIntent().getSerializableExtra("persona");
        validarTipoRequest();
        validarCargaACampos();
        ActionBarUtil.getInstance(this, true).setToolBar(getString(R.string.registro_persona), getString(R.string.insertar));
        listarTiposDocumentos();
        onSelectItemSpinner();
    }

    private void onSelectItemSpinner() {
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 documentoSeleccionado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listarTiposDocumentos() {
        TipoDocumentoServiceImpl tipoDocumentoService = new TipoDocumentoServiceImpl(this);
        tipoDocumentoService.getTipoDocumento(spinnerTipoDocumento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            cargarInformacion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validarCargaACampos() {
        if (!esInsert) {
            spinnerTipoDocumento.setSelection(personaActualizar.getIdTipoDocumento());
            txtDocumento.setText(personaActualizar.getNumeroDocumento());
            txtNombre.setText(personaActualizar.getNombre());
            txtApellido.setText(personaActualizar.getApellido());
        }
    }


    private void cargarInformacion() {
        validarInformacionACargar();
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.confirm);
        if (esInsert) {
            builder.setMessage(R.string.confirm_message_guardar_informacion);
        } else {
            builder.setMessage(R.string.confirm_message_actualizar_informacion);
        }
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  insertarInformacion() );
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void validarInformacionACargar() {
        if (esInsert) {
            persona.setIdTipoDocumento(documentoSeleccionado);
            persona.setNumeroDocumento(txtDocumento.getText().toString());
            persona.setNombre(txtNombre.getText().toString());
            persona.setApellido(txtApellido.getText().toString());
        } else {
            personaActualizar.setIdTipoDocumento(documentoSeleccionado);
            personaActualizar.setNumeroDocumento(txtDocumento.getText().toString());
            personaActualizar.setNombre(txtNombre.getText().toString());
            personaActualizar.setApellido(txtApellido.getText().toString());
        }
    }

    private void insertarInformacion() {
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        Observable.fromCallable(()-> {
            if (esInsert) {
                personaService.insertar(persona);
                finish();
                return persona;
            } else {
                personaService.actualizar(personaActualizar);
                finish();
                return personaActualizar;
            }
        }).subscribeOn(Schedulers.computation()).subscribe();
        enviarAListadoPersonas();
    }

    public void enviarAListadoPersonas(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    private void validarTipoRequest() {
        esInsert = personaActualizar != null ? false : true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}