/* 0 - Nome do pacote */ 

/* 1 - Bibliotecas */ 
//import io.restassured.response.Response; // Classe Resposta do REST-assured

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;     // Função given da classe REST-assured
import static org.hamcrest.Matchers.*;              // Classe de verificadores do Hamcrest


/* 2 - Classe */
public class TestPet {

    /* 2.1 - Atributos */ 
    static String ct = "application/json"; // CT = Content Type
    static String uriPet = "https://petstore.swagger.io/v2/pet";

    /* 2.2 - Funções e Métodos */ 
    /* 2.2.1 funções e métodos comuns / úteis */
    public static String lerArquivoJson(String arquivoJson) throws IOException{ // Função de leitura de Json. Retorna um texto
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    /* 2.2.2 métodos de teste */
    @Test
    public void testPostPet() throws IOException {
        /* CONFIGURA */
        /* Carregar os dados do arquivo json do pet */
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        int petId = 502670901; // Código esperado do pet

        /* Começa o teste via REST-assured */
        given()                                         // DADO que
            .contentType(ct)                            // o tipo do conteúdo é String ct
            .log().all()                                // mostre tudo na ida (terminal)
            .body(jsonBody)                             // envie o corpo da requisição

        /* EXECUTA */    
        .when()                                         // QUANDO
            .post(uriPet)                               // chamamos o endpoint fazendo post

        /* VALIDA */    
        .then()                                         // ENTÃO
            .log().all()                                // mostre tudo na volta
            .statusCode(200)                            // verifique se o status code é 200
            .body("name", is("Snoopy"))                 // verifique se o nome é Snoopy
            .body("id", is(petId))                      // verifique o código do pet
            .body("category.name", is("cachorro"))      // verifique se é cachorro
            .body("tags[0].name", is("vacinado"))       // verifique se está vacinado
        ; // fim do given
    }                                                                   

}