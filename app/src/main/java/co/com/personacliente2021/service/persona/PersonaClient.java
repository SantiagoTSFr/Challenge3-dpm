package co.com.personacliente2021.service.persona;

import java.util.List;

import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.respuesta.RespuestaPersona;
import co.com.personacliente2021.util.CustomResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface PersonaClient {
       @GET("v1/persona")
       Call<List<Persona>> getPersonas();

       @POST("v1/persona")
       Call<CustomResponse<RespuestaPersona>> insertar(@Header("Content-Type") String contentTypeApplicationJson, @Body PersonaDTO persona);

       @PUT("v1/persona/{idPersona}")
       Call<CustomResponse<RespuestaPersona>> actualizar(@Header("Content-Type") String contentTypeApplicationJson, @Body PersonaDTO persona, @Path(value = "idPersona") Integer idPersona);

       @DELETE("v1/persona/{idPersona}")
       Call<CustomResponse<RespuestaPersona>> eliminar(@Header("Content-Type") String contentTypeApplicationJson, @Path(value = "idPersona") Integer idPersona);


 }
