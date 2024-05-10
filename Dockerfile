# Estágio de construção
FROM maven:3.8.4-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml primeiro para permitir que o Docker crie a camada de dependências isoladamente
COPY pom.xml .

# Baixa as dependências Maven (separadamente para aproveitar o cache do Docker)
RUN mvn -B -DskipTests=true dependency:go-offline

# Copia o restante do código-fonte
COPY src ./src

# Constrói o aplicativo sem executar os testes
RUN mvn -B -DskipTests=true package

# Etapa de construção da imagem final
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta 8080
EXPOSE 8080

# Copia o jar construído do estágio anterior
COPY --from=build /app/target/app-calcis-0.0.1-SNAPSHOT.jar .

# Comando para iniciar o aplicativo quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app-calcis-0.0.1-SNAPSHOT.jar"]
