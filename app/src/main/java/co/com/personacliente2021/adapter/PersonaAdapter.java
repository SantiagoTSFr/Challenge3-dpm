package co.com.personacliente2021.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.R;
import co.com.personacliente2021.RegistroPersonaActivity;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;

public class PersonaAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private final List<Persona> personas;
    private Persona currentPerson;

    private Context context;

    public PersonaAdapter(Context context, List<Persona> personas) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.personas = personas;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Persona getItem(int position) {
        return personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personas.get(position).getIdPersona().longValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        PersonaDTO personaDto = modelToDto(getItem(position));
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_persona, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.numeroDocumento.setText(personas.get(position).getNumeroDocumento());
        holder.nombre.setText(personas.get(position).getNombre());
        holder.apellido.setText(personas.get(position).getApellido());
        currentPerson = getItem(position);
        try {
            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RegistroPersonaActivity.class);
                intent.putExtra("persona", personaDto);
                context.startActivity(intent);
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }

    public PersonaDTO modelToDto(Persona persona){
        return new PersonaDTO(persona.getIdPersona(),persona.getTipoDocumento().getIdTipoDocumento(),persona.getNumeroDocumento(),persona.getNombre(),persona.getApellido(), persona.isActivo());

    }

    public Persona getCurrentPerson() {
        return currentPerson;
    }

    class ViewHolder {
        @BindView(R.id.txtNumeroDocumento)
        TextView numeroDocumento;
        @BindView(R.id.txNombre)
        TextView nombre;
        @BindView(R.id.txtApellido)
        TextView apellido;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
