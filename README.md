# ☕ Studios Veneto — Backend

**API REST em Spring Boot** responsável por servir o conteúdo dinâmico da landing page do empreendimento Studios Veneto (MK2 Incorporadora) e capturar leads, com notificação automática por e-mail para o time de corretores.

> 🔗 Repositório do frontend: [`estudio-veneto-frontend`](https://github.com/caiomilanic/estudio-veneto-frontend)
> 🌐 Produção: [`estudio-veneto-backend.onrender.com`](https://estudio-veneto-backend.onrender.com) · domínio próprio (`api.studiosveneto.com.br`) em configuração

---

## 📖 Sobre

Este backend serve como fonte única de dados para a landing page — textos institucionais, fotos, preços, diferenciais e links sociais são todos consultados via API, sem hardcode no frontend. Também é responsável por capturar cada lead preenchido no formulário e notificar o corretor automaticamente por e-mail.

---

## 📑 Índice

- [Tecnologias](#️-tecnologias)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Como rodar localmente](#-como-rodar-localmente)
- [Deploy em produção](#-deploy-em-produção)
- [Variáveis de ambiente](#-variáveis-de-ambiente)
- [Roadmap](#-roadmap)
- [Aprendizados técnicos](#-aprendizados-técnicos)

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| ☕ **Java 25** (LTS) | Linguagem principal |
| 🍃 **Spring Boot 4.1** | Framework da API REST |
| 🐘 **PostgreSQL** (via [Neon](https://neon.tech)) | Banco de dados serverless, região `sa-east-1` |
| 📧 **[Brevo](https://brevo.com)** | Envio de e-mails transacionais — **via API HTTP** (não SMTP, veja aprendizados) |
| ✅ **Bean Validation** | Validação de DTOs de entrada |
| 🔀 **Spring `@Async`** | Envio de e-mail em thread separada, sem travar a resposta do lead |
| 🐳 **Docker** | Build multi-stage para deploy no Render |
| 📦 **Maven** | Gerenciador de dependências e build |
| ☁️ **Render** | Hospedagem (free tier) |

---

## 📂 Estrutura do projeto

Organização **por feature** — cada módulo carrega seus próprios `dto/`, `entity/`, `Controller`, `Service` e `Repository`:

```
com.estudioveneto.landingapi
│
├── 📁 content/                  → conteúdo institucional da landing
│   ├── 📁 dto/                   ContentDTO, PhotoDTO, SocialLinkDTO, HighlightDTO
│   ├── 📁 entity/                 SiteContent, Photo, SocialLink, Highlight
│   ├── ContentController.java     GET /api/content, /photos, /social-links, /highlights
│   ├── ContentService.java
│   └── *Repository.java
│
├── 📁 pricing/                  → tipologias e valores
│   ├── 📁 dto/                    UnitDTO (tipo, areaPrivativa, areaTotal, areaJardim, precoAPartirDe)
│   ├── 📁 entity/                 Unit
│   ├── UnitController.java        GET /api/units
│   ├── UnitService.java
│   └── UnitRepository.java
│
├── 📁 lead/                     → captação de leads
│   ├── 📁 dto/                    LeadRequestDTO (com Bean Validation)
│   ├── 📁 entity/                 Lead (inclui preferenciaContato: email | whatsapp | ligacao)
│   ├── LeadController.java        POST /api/leads
│   ├── LeadService.java           salva no banco e delega notificação
│   ├── BrevoEmailService.java     ✉️ envia e-mail via API HTTP do Brevo (@Async)
│   └── LeadRepository.java
│
├── 📁 config/
│   └── CorsConfig.java            origens permitidas (frontend Vercel)
│
├── 📁 exception/
│   └── GlobalExceptionHandler.java
│
└── LandingApiApplication.java     @EnableAsync
```

**Arquivos de infraestrutura (raiz do projeto):**
```
├── Dockerfile          → build multi-stage (JDK para build, JRE para runtime)
└── .dockerignore
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/content` | Textos institucionais (hero, sobre, localização, para-morar, para-investir, sobre-incorporadora) |
| `GET` | `/api/photos` | Fotos do empreendimento (URLs Cloudinary), ordenadas por `displayOrder` |
| `GET` | `/api/social-links` | Instagram e WhatsApp (número + mensagem padrão) |
| `GET` | `/api/highlights?category=` | Lista de destaques — `category=localizacao` ou `category=diferenciais` |
| `GET` | `/api/units` | Tipologias disponíveis, com área privativa, área total, área de jardim (quando aplicável) e preço |
| `POST` | `/api/leads` | 📨 Captura um novo lead e dispara notificação por e-mail para o corretor (assíncrono) |

<details>
<summary>📦 Exemplo de payload — <code>POST /api/leads</code></summary>

```json
{
  "nome": "Maria Silva",
  "telefone": "(41) 99999-9999",
  "email": "maria@exemplo.com",
  "preferenciaContato": "whatsapp"
}
```

**Validações aplicadas:**
- `nome` — obrigatório
- `telefone` — obrigatório
- `email` — obrigatório e formato válido
- `preferenciaContato` — obrigatório, aceita apenas `email`, `whatsapp` ou `ligacao`
</details>

<details>
<summary>📦 Exemplo de resposta — <code>GET /api/units</code></summary>

```json
[
  {
    "tipo": "Studio",
    "areaPrivativa": "18,33m² a 19,39m²",
    "areaTotal": "24,39m² a 25,80m²",
    "areaJardim": null,
    "precoAPartirDe": 199000.00
  },
  {
    "tipo": "Studio Garden",
    "areaPrivativa": "18,33m² a 19,39m²",
    "areaTotal": "24,39m² a 25,80m²",
    "areaJardim": "14,52m² a 25,98m²",
    "precoAPartirDe": 219900.00
  }
]
```
</details>

---

## 🚀 Como rodar localmente

### Pré-requisitos
- ☕ Java 25
- 📦 Maven (ou use o wrapper `./mvnw` incluído)
- 🐘 Conta no [Neon](https://neon.tech) — Postgres serverless
- 📧 Conta no [Brevo](https://brevo.com) — API Key transacional (não SMTP), com remetente verificado

### Passos

```bash
git clone https://github.com/caiomilanic/estudio-veneto-backend.git
cd estudio-veneto-backend

# configure as variáveis de ambiente (ver seção abaixo)
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. Teste com:
```
http://localhost:8080/api/content
```

---

## ☁️ Deploy em produção

Hospedado no **Render** (free tier), via **Docker**.

- O `Dockerfile` usa build multi-stage: um estágio compila com Maven + JDK completo, o outro roda só com JRE (imagem final mais enxuta)
- **Root Directory** configurado como `landing-api` no painel do Render (o repositório tem essa subpasta na raiz)
- `server.port=${PORT:8080}` no `application.properties` — obrigatório, já que o Render define a porta dinamicamente via variável `PORT`
- **Keep-alive:** o free tier do Render "dorme" após ~15 min de inatividade. Um monitor no [UptimeRobot](https://uptimerobot.com) pinga `/actuator/health` a cada 5 minutos para manter o serviço ativo

---

## 🔐 Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `DB_HOST` | Endpoint **pooled** do Neon (ex: `ep-xxx-pooler.sa-east-1.aws.neon.tech`) |
| `DB_NAME` | Nome do banco (`neondb`) |
| `DB_USER` | Usuário do Postgres |
| `DB_PASSWORD` | Senha do Postgres |
| `BREVO_API_KEY` | Chave de **API** do Brevo (aba *API Keys*, **não** a chave SMTP) |
| `LEAD_SENDER_EMAIL` | E-mail remetente, **verificado** no Brevo (Settings → Senders & IP) |
| `CORRETOR_EMAIL` | E-mail que recebe a notificação de cada novo lead |

> ⚠️ **Render free tier bloqueia portas SMTP de saída (25, 465, 587).** Por isso o envio de e-mail migrou de SMTP para a **API HTTP do Brevo** (porta 443, sempre liberada). As variáveis `BREVO_SMTP_USER`/`BREVO_SMTP_KEY` (antigas) não são mais usadas.

> ⚠️ Configure essas variáveis diretamente na *Run Configuration* da sua IDE em desenvolvimento local — definir só via `export` no terminal não é suficiente se você rodar pela IDE.

> 🔒 Nunca commite `application-local.properties` nem qualquer arquivo com credenciais reais — já estão no `.gitignore`.

---

## 🗺️ Roadmap

### ✅ Concluído
- [x] Endpoints de conteúdo (`content`, `photos`, `social-links`, `highlights`, `units`)
- [x] Captura de leads com validação, incluindo preferência de contato
- [x] Notificação por e-mail via API HTTP do Brevo, assíncrona
- [x] Dockerfile e deploy em produção no Render
- [x] Keep-alive configurado via UptimeRobot
- [x] Domínio `studiosveneto.com.br` registrado (Registro.br)

### 🚧 Pendente
- [ ] 🌐 Finalizar propagação DNS e apontar `api.studiosveneto.com.br` para o Render
- [ ] 🔒 Travar CORS para a origem final do domínio próprio (hoje usa `allowedOriginPatterns` com wildcard `*.vercel.app`)
- [ ] ✉️ Autenticar domínio próprio no Brevo (SPF/DKIM), migrando o remetente do Gmail para `contato@studiosveneto.com.br`

---

## 💡 Aprendizados técnicos

- 🌍 Variáveis de ambiente devem ser configuradas **na IDE**, não apenas no terminal — senão o Spring resolve `${DB_HOST}` literalmente e lança `UnknownHostException`
- 🔌 O endpoint **pooled** do Neon (com sufixo `-pooler`) é o correto para uma aplicação web que abre/fecha conexões constantemente
- 🔧 CORS precisa liberar explicitamente a origem do frontend — em produção, usar `allowedOriginPatterns` (com wildcard) quando a URL do deploy ainda não é fixa/definitiva
- 🐳 Ao fazer deploy de um repositório com subpasta (`landing-api/` dentro do repo), é preciso configurar o **Root Directory** no Render, senão o build não encontra o `Dockerfile`
- 🚫 **Render bloqueia outbound SMTP (portas 25, 465, 587) no free tier desde set/2025.** Isso causa dois sintomas ao mesmo tempo: e-mails que nunca chegam *e* requisições lentas (a conexão SMTP trava até estourar timeout, dentro da mesma requisição). A solução foi migrar para a **API HTTP do Brevo** (porta 443, nunca bloqueada) e tornar o envio **assíncrono** (`@Async`), para que a resposta do lead nunca dependa do envio de e-mail
- 😴 O free tier do Render "dorme" após ~15 min de inatividade, com cold start de até ~1 minuto — resolvido com ping periódico via UptimeRobot no endpoint `/actuator/health`
- 📧 No Brevo, **login SMTP ≠ e-mail remetente ≠ API Key** — são três credenciais distintas, cada uma com seu propósito

---

## 🏢 Sobre a incorporadora

**MK2 Incorporadora** — atuação desde 2010, desenvolvendo empreendimentos residenciais pautados em planejamento, responsabilidade e qualidade construtiva.

---

<p align="center">Feito com ☕ e 🍃 Spring Boot</p>