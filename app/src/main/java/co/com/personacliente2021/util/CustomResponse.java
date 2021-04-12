package co.com.personacliente2021.util;

public class CustomResponse<T> {

    private T valor;

    public CustomResponse(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }
}
