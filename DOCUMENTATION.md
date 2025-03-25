# Documentação Técnica

## **Decisões Técnicas**
### **Spring Boot**
O Spring Boot foi escolhido por sua facilidade de configuração e integração com bibliotecas populares.

### **RestTemplate**
Utilizado para fazer requisições HTTP à API do HubSpot. É uma biblioteca robusta e amplamente utilizada.

### **OAuth 2.0**
Implementado para garantir segurança na autenticação com o HubSpot.

### **Rate Limit**
Mecanismo para evitar o bloqueio da API devido ao excesso de requisições.

### **Logs**
SLF4J com Logback para registro de informações e erros.

## **Melhorias Futuras**
1. Migração para WebClient.
2. Cache de tokens.
3. Monitoramento com Prometheus e Grafana.
4. Testes automatizados.

## **Motivação para Uso de Bibliotecas**
- **RestTemplate vs WebClient:** RestTemplate foi escolhido por sua simplicidade, mas WebClient pode ser uma alternativa futura.
- **Spring Security:** Utilizado para implementar autenticação OAuth 2.0.
- **Lombok:** Reduz boilerplate code, melhorando a legibilidade.

## **Conclusão**
Este projeto é uma solução robusta e segura para integrar com a API do HubSpot. Com as melhorias futuras, pode se tornar ainda mais escalável e eficiente.