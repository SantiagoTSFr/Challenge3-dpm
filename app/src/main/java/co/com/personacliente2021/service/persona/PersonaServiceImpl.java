package co.com.personacliente2021.service.persona;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import co.com.personacliente2021.adapter.PersonaAdapter;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.respuesta.RespuestaPersona;
import co.com.personacliente2021.util.CustomResponse;
import co.com.personacliente2021.util.Parametro;
import co.com.personacliente2021.util.RetrofitFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class PersonaServiceImpl extends RetrofitFactory {



     public PersonaServiceImpl(Context context){
         super(context);
     }
     private List<Persona> listaPersonas;
     private PersonaAdapter personaAdapter;

     public void getPersona(ListView listViewPersonas){
        Retrofit retrofit =  getRetrofitInstance();
        PersonaClient client = retrofit.create(PersonaClient.class);
        Call<List<Persona>> response = client.getPersonas();
        response.enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(Call<List<Persona>> call, Response<List<Persona>> response) {
                listaPersonas  = response.body();
                if(listaPersonas != null){
                    personaAdapter = new PersonaAdapter(getContext(),listaPersonas);
                    listViewPersonas.setAdapter(personaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Persona>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

     public void insertar(PersonaDTO persona) {
         Retrofit retrofit =  getRetrofitInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         Call<CustomResponse<RespuestaPersona>> response =client.insertar(Parametro.CONTENT_TYPE_APPLICATION_JSON,persona);
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {

             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }

     public void actualizar(PersonaDTO personaActualizar) {
         Retrofit retrofit =  getRetrofitInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         Call<CustomResponse<RespuestaPersona>> response =client.actualizar(Parametro.CONTENT_TYPE_APPLICATION_JSON,personaActualizar,personaActualizar.getIdPersona());
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {

             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }
     public void eliminar(){
         Retrofit retrofit =  getRetrofitInstance();
         PersonaClient client = retrofit.create(PersonaClient.class);
         System.out.println(personaAdapter.getCurrentPerson().getIdPersona());
         Call<CustomResponse<RespuestaPersona>> response =client.eliminar(Parametro.CONTENT_TYPE_APPLICATION_JSON, personaAdapter.getCurrentPerson().getIdPersona());
         for (int i = 0; i < listaPersonas.size(); i++) {
             if (listaPersonas.get(i).getIdPersona() == personaAdapter.getCurrentPerson().getIdPersona()) {
                 listaPersonas.remove(i);
             }
         }
         personaAdapter.notifyDataSetChanged();
         response.enqueue(new Callback<CustomResponse<RespuestaPersona>>() {
             @Override
             public void onResponse(Call<CustomResponse<RespuestaPersona>> call, Response<CustomResponse<RespuestaPersona>> response) {

             }

             @Override
             public void onFailure(Call<CustomResponse<RespuestaPersona>> call, Throwable t) {
                 Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
     }
 }
