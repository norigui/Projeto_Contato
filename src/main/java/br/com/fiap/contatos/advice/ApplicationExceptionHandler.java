package br.com.fiap.contatos.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // #A PRIMEIRA STRING REPRESENTA O NOME DO OBJETO QUE ESTÁ COM ERRO, E O SEGUNDO REPRESENTA A MENSAGEM DE ERRO.
    public Map<String, String> manusearArgumentosInvalidos(MethodArgumentNotValidException erro) {

        Map<String, String> mapaDeErro = new HashMap<>();
        // # "List<FieldError>" CRIA UMA LISTA DO TIPO FIELD ERROR,
        // ".getBindingResult()" PEGA TODA A MENSAGEM DE ERRO
        // E "getFieldErrors()" RETORNA SÓ O CAMPO FIELD ERROR
        List<FieldError> campoErro = erro.getBindingResult().getFieldErrors();

        // #PEGA TODAS AS MENSAGENS DE FIELD E DEFAULTMESSAGE.
        for (FieldError fe : campoErro) {
            mapaDeErro.put(fe.getField(), fe.getDefaultMessage());
        }
        return mapaDeErro;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> manusearIntegridadeDados() {
        Map<String, String> mapaErro = new HashMap<>();
        mapaErro.put("erro", "Usuário já está cadastrado.");
        return mapaErro;
    }

}
