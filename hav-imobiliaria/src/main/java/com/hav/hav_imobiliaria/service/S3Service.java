package com.hav.hav_imobiliaria.service;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;  // Importa a classe que vai carregar as credenciais do perfil padrão
import com.amazonaws.services.s3.AmazonS3;  // Importa a interface do cliente do Amazon S3
import com.amazonaws.services.s3.AmazonS3ClientBuilder;  // Importa a classe para construir um cliente do S3
import com.amazonaws.services.s3.model.PutObjectRequest;  // Importa a classe para criar uma solicitação para enviar um arquivo para o S3
import java.io.File;  // Importa a classe File para manipular arquivos no Java

public class S3Service {

        private AmazonS3 s3Client;  // Declara uma variável para armazenar o cliente do S3

        public S3Service() {
            // Cria um cliente do Amazon S3 utilizando as credenciais do perfil padrão (procura no arquivo ~/.aws/credentials)
            this.s3Client = AmazonS3ClientBuilder.standard()
                    //busca as credenciais armazenadas no arquivo ~/.aws/credentials
                    .withCredentials(new ProfileCredentialsProvider())
                    // região em que o cliente S3 irá operar.
                    .withRegion("us-east-1")
                    .build();  // Constrói e inicializa o cliente do S3
        }

        // Metodo para fazer o upload de um arquivo para o S3
        public void uploadFile(String bucketName, String key, String filePath) {
            File file = new File(filePath);  // Cria um objeto File a partir do caminho do arquivo fornecido
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, file);
            // Cria uma solicitação para enviar o arquivo para o bucket do S3 com o nome do arquivo e caminho especificados

            s3Client.putObject(putRequest);  // Envia a solicitação para o cliente S3, fazendo o upload do arquivo
            System.out.println("Arquivo enviado para o S3: " + key);  // Imprime uma mensagem de sucesso com o nome do arquivo enviado
        }

}
