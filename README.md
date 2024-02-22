# Vault Playground

## Rationale

Before diving into the gory details of the implementation it's important to understand the problem [Vault](https://www.vaultproject.io) is trying to address and the scope it's suitable for.

[HashiCorp](https://www.hashicorp.com) has been smart enough to notice the dynamic nature of the workloads with the rising of Cloud Architecture.

They figured automation was going to be mandatory to be able to cope with the exponential workload cardinality.

Human operators would never be able to handle that many workloads: a scale issue.

And the secret management was no exception to this transformation. [Vault](https://www.vaultproject.io) tries to address exactly that: offload the burden of secret management from the human operators.

We know that most standard application have 3 main responsibilities:
- capturing user input
- processing user input
- persisting state

The persistence of the state usually involves some sort of persistence mechanism.

Now since data is the most critical part of the application it's usually highly secured (at least should be) with secrets.

From the persistence point of view, a secret is shared with the application and comes with a limited scope.

In the traditional workflow this is what happens:
- a database administrator defines a role (which describes a scope), creates a user, its password and associates the user with the role.
- the DBA shares the secrets with another operator which can override, at system level, the ones defined in the application
- and this workflow is replicated for each environment.

A common alternative to this workflow is to embed an encrypted version of the secrets in the source code of the application.

But then the application would need a way to decrypt the secrets, therefore, would need the decryption key.

[Vault](https://www.vaultproject.io) provides 2 approaches to mitigate these limitations
- static secret engines
  - the operator manually stores the database secrets in vault
  - the application is granted a token which holds permissions to read/write the secrets
- dynamic secret engines:
  - each time the application starts, [Vault](https://www.vaultproject.io) generates the database role, the database user and the database password
  - the secrets are injected in the application and used to interact with the persistence mechanism as long as the application is up and running
  - the secrets are revoked/destroyed when the application stops 

The 2nd approach is more involved but remove the need for both operators to configure secrets for the application.

In addition, it's bounded to the application lifecycle, therefore reduces the risk of leakage.

The 1st approach allows application to remove any secret reference from their codebase.

In the scope of this playground, we'll explore the 2 approaches.
- the application is a [spring-boot](https://spring.io/projects/spring-boot) app.
- we'll use testcontainers to bootstrap and configure both postgres and vault
- we'll secure vault communications with TLS and a self-signed certificate
- we'll explore the specific relation between the application lifecycle and the secret lifecycle which, in my opinion, is the one of the most valuable feature. 

