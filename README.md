<h1 align="center">
     Store String Parser
</h1>

<h4 align="center">
	🚧 Concluído 🚧
</h4>

Tabela de conteúdos
=================

* [Sobre o projeto](#-sobre-o-projeto)
* [Como executar o projeto](#-como-executar-o-projeto)
    * [Pré-requisitos](#pré-requisitos)
    * [Rodando o projeto](#user-content--rodando-o-projeto)
* [Tecnologias](#-tecnologias)
* [Autor](#-autor)
* [Licença](#user-content--licença)


## 💻 Sobre o projeto

Sistema para interpretação de strings para geração de relatório.

---

## 🚀 Como executar o projeto

### Pré-requisitos

- [Git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/)
- [JDK 16](https://adoptopenjdk.net/?variant=openjdk16&jvmVariant=openj9)

#### 🎲 Rodando o projeto

```bash

# Clone este repositório
$ git clone git@github.com:Ellian-aragao/StringsStoreParser.git

# Acesse a pasta do projeto no terminal/cmd
$ cd StringsStoreParser

# Instale as dependências
$ mvn install

# Execute a aplicação
$ mvn compile exec:java

```
---

## 🛠 Tecnologias

O projeto fora todo desenvolvido apenas com Java, sem utilização de bibliotecas externas e nem framworks com exceção
apenas a biblioteca de logs, [Logback](http://logback.qos.ch/) e [Lombok](https://projectlombok.org/) para redução de
boilerplate.

O sistema foi desenvolvido de modo a simular 3 processos, sendo o primeiro responsável por ler os arquivos de entrada,
o segundo fazer a interpretação dos dados lidos dos arquivos, e o terceiro responsável por gerar o relatório.

Para tal, foram simuladas 2 serviços que seriam externos a aplicação:
- **Banco de dados**: para armazenamento dos dados processados para utilização na geração do relatório.
- **Fila**: para delegar o processamento dos dados lidos dos arquivos a outro serviço.

---

## 🦸 Autor


 <img style="border-radius: 50%;" src="https://avatars1.githubusercontent.com/u/52057913?s=400&u=222dffcab5586f0eb4efcbff06caa868450f6b8a&v=4" width="100px;" alt=""/>
 <br />
 <a><sub><b>Ellian Aragão Dias</b></sub></a>

[![Linkedin Badge](https://img.shields.io/badge/-Ellian-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/ellian-arag%C3%A3o-dias-22192a159/)](https://www.linkedin.com/in/ellian-arag%C3%A3o-dias-22192a159/)
[![Gmail Badge](https://img.shields.io/badge/-ellian.aragao@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:ellian.aragao@gmail.com)](mailto:ellian.aragao@gmail.com)

---

## 📝 Licença

Este projeto esta sobe a licença [MIT](./LICENSE).

Feito com ❤️ por Ellian Aragão Dias 👋🏽 [Entre em contato!](https://www.linkedin.com/in/ellian-arag%C3%A3o-dias-22192a159/)

---
