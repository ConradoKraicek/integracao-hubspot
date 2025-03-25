# Integração com HubSpot - API REST em Java

## **Descrição do Projeto**
Este projeto é uma API REST desenvolvida em Java utilizando o Spring Boot para integrar com a API do HubSpot.

## **Requisitos**
- Java JDK 11 ou superior
- Maven
- Conta de desenvolvedor no HubSpot

## **Configuração**
1. Clone o repositório.
2. Configure as variáveis de ambiente.
3. Execute o projeto.

## **Endpoints**
- `/auth/authorize`: Gera a URL de autorização.
- `/auth/callback`: Processa o callback OAuth.
- `/contacts`: Cria um contato no HubSpot.
- `/webhook/contact-creation`: Recebe notificações de criação de contatos.

## **Testando com Postman**
1. **Gere o Token de Acesso**
    - Acesse /auth/authorize para obter a URL de autorização e o código que será redirecionado para /auth/callback.
    - Use o código retornado para obter o token em /auth/callback.
2. **Crie um Contato**
    - Envie uma requisição POST para /contacts com o token no header Authorization e os dados do contato no body (JSON). 
3. **Simule o Webhook**
    - Envie uma requisição POST para /webhook/contact-creation com um payload de exemplo.

## **Documentação Técnica**
Para detalhes sobre as decisões técnicas, motivação para uso de bibliotecas e melhorias futuras, consulte o arquivo [DOCUMENTATION.md](DOCUMENTATION.md).