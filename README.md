# quarkus-backend-t4 (Trabalho T4)

## **Ponto importante**

Esse projeto, graças à GraalVM, compila para código nativo. Dessa forma, mesmo sendo em Java, tem praticamente a mesma performance que um aplicação em C

* Setar para os hooks de pre-commit

``` shell script
git config core.hooksPath hooks/
```

## Depts

* Docker
* GraalVM
* Heroku CLI

## Docker e Deployments

As instruções estão em nosso Dockerfile. Para rodar tudo de forma mais "açucarada", **execute**:

``` shell script
./deploy_docker
```

## Rodar localmente

Como o projeto tem o Vert.x, grosseiramente falando, funciona como Node.js: quando você add algo, rodando em dev, não precisa fazer o rebuild. **Comando para rodar em dev**:

``` shell script
./gradlew quarkusDev
```

## Spotless

Lib para deixar a nossa codebase padronizada e organizada. Vou deixar um hook para rodar a cada commit, mas caso queira rodar, **o comando é esse**:

``` shell script
./gradlew spotlessApply
```

## Deploy para o container de PRD no Heroku

``` shell script
heroku login
heroku container:login

# caso não haja pub
docker tag quarkus/quarkus-backend-t4 registry.heroku.com/quarkus-back-t4-native/web
# caso não haja pub
docker push registry.heroku.com/quarkus-back-t4-native/web

heroku container:release web -a quarkus-back-t4-native
```
